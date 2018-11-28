package com.xapptree.ginger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.xapptree.ginger.adapters.CouponAdapter;
import com.xapptree.ginger.interfaces.CouponInterface;
import com.xapptree.ginger.interfaces.GingerInterface;
import com.xapptree.ginger.model.Coupons;
import com.xapptree.ginger.model.NotificationsManage;
import com.xapptree.ginger.model.OnlineOrders;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RealmController;
import com.xapptree.ginger.utilities.RecyclerTouchListener;

import java.util.Date;
import java.util.Vector;

public class CheckoutScreen extends GingerBaseActivity implements View.OnClickListener, GingerInterface, CouponInterface {
    private Button bCOD, bRazorpay;
    private Button mApply;
    private CoordinatorLayout coordinatorLayout;
    private CollectionReference onlineRef;
    private CollectionReference notifRef;
    private CollectionReference couponRef;
    private TextView tAmount;
    private String pushToken;
    private String mOrderTS;
    private String cUid;
    private OnlineOrders curOrder;
    private Boolean isInitialized = false;
    private Typeface customFont;
    private View bottoms;
    private RecyclerView couponRecycler;
    private BottomSheetDialog dialog;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_checkout_screen);

        bottoms = getLayoutInflater().inflate(R.layout.coupons_lay, null);
        dialog = new BottomSheetDialog(CheckoutScreen.this);
        dialog.setContentView(bottoms);

        /*Db ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        onlineRef = mDatabase.collection("OnlineOrders");
        notifRef = mDatabase.collection("NotificationsManage");
        couponRef = mDatabase.collection("Coupons");

        /*PushToken fron firebase*/
        pushToken = FirebaseInstanceId.getInstance().getToken();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        /*Widget REf*/
        couponRecycler = bottoms.findViewById(R.id.coupon_recycler);
        bCOD = findViewById(R.id.codpayment);
        bRazorpay = findViewById(R.id.onlinepayment);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        tAmount = findViewById(R.id.amount);
        ImageButton mMenu = findViewById(R.id.menu);
        TextView tHeader = findViewById(R.id.header);
        mApply = findViewById(R.id.appplycoupon);
        RelativeLayout RCod = findViewById(R.id.codlay);
        RelativeLayout rrazorpay = findViewById(R.id.cardlay);

         /*RecyclerView options*/
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        couponRecycler.setLayoutManager(mLayoutManager);

        bCOD.setOnClickListener(this);
        bRazorpay.setOnClickListener(this);
        RCod.setOnClickListener(this);
        rrazorpay.setOnClickListener(this);
        mApply.setOnClickListener(this);
        findViewById(R.id.removecoupon).setOnClickListener(this);

        // Shared pref data
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        mOrderTS = sp.getString("OrderTs", "");
        cUid = sp.getString("Uid", "");

        /*Setting Fonts*/
        AppConstants.overrideFonts(getApplicationContext(), findViewById(R.id.coordinatorLayout), "Raleway-Regular.ttf");
        final AssetManager am = this.getApplicationContext().getAssets();
        customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");
        tHeader.setTypeface(customFont);

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckoutScreen.this, OrderItemScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getCoupons();
    }

    private void bindData() {
        tAmount.setText(AppConstants.uniString(curOrder.ExtendedTotal));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.codlay:
                if (isInitialized) {
                    bCOD.setVisibility(View.VISIBLE);
                    bRazorpay.setVisibility(View.GONE);
                }
                break;
            case R.id.cardlay:
                if (isInitialized) {
                    bCOD.setVisibility(View.GONE);
                    bRazorpay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.codpayment:

                curOrder.Order_PaymentMode = "CASH";
                curOrder.Order_Status = "Order Placed";
                curOrder.Order_StatusCode = "100";
                completeOrder();
                break;
            case R.id.onlinepayment:
                break;
            case R.id.appplycoupon:
                showcouponSheet();
                break;
            case R.id.removecoupon:
                removeCoupon();
                break;
            default:
                break;
        }

    }

    private void completeOrder() {
        showProgress("Confirming order..");
        // Track Event
        Bundle params = new Bundle();
        params.putString("CustomerId", cUid);
        params.putString("RestaurentId", curOrder.PrefferedStore);
        params.putString("OrderId", curOrder.Order_Id);
        params.putString("PurchaseDate", curOrder.Order_Date);
        params.putDouble("OrderTotal", Double.parseDouble(curOrder.ExtendedTotal));
        params.putString("PayMode", curOrder.Order_PaymentMode);
        params.putBoolean("IsSuccess", true);
        mFirebaseAnalytics.logEvent("Purchase", params);

        PayloadBuilder builder = new PayloadBuilder();
        builder.putAttrString("RestaurentId", curOrder.PrefferedStore)
                .putAttrString("CustomerId", cUid)
                .putAttrString("OrderId", curOrder.Order_Id)
                .putAttrDate("PurchaseDate", new Date())
                .putAttrDouble("OrderTotal", Double.parseDouble(curOrder.ExtendedTotal))
                .putAttrString("Currency", "rupee")
                .putAttrString("PayMode", curOrder.Order_PaymentMode);
        MoEHelper.getInstance(CheckoutScreen.this).trackEvent("Purchase", builder.build());
        // End

        onlineRef.document(mOrderTS).set(curOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                NotificationsManage nm = new NotificationsManage();
                nm.CustomerName = curOrder.CustomerName;
                nm.OrderId = mOrderTS;
                nm.StatusMessage = "Hi " + curOrder.CustomerName + " your order #" + mOrderTS + " has been placed successfully.";
                nm.Pushid = pushToken;
                nm.StatusCode = 100;
                notifRef.document(mOrderTS).set(nm);

                RealmController.with(CheckoutScreen.this).deleteCart();
                mOrderTS = "";
                saveTimestamp(false);
                hideProgress();
                Intent i = new Intent(CheckoutScreen.this, SuccessScreen.class);
                i.putExtra("ORDERID", curOrder.Order_Id);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("OnlineOrders", "Error writing document", e);
                hideProgress();
                showSnacbarInd("Error occured, Please try again.\n");
            }
        });

    }

    private void saveTimestamp(boolean b) {
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("IsTimeStampCreated", b);
        editor.putString("OrderTs", mOrderTS);
        editor.commit();
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        super.onConnectivityChanged(isConnected);
        if (!isConnected) {
            showNetworkPopUp();
        } else {
            hideNetorkPopUp();
            getDataFireStore();
        }
    }

    @Override
    public void getDataFireStore() {
        showProgress("Initializing Payment modes..");
        DocumentReference documentReference = onlineRef.document(mOrderTS);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshots) {
                hideProgress();
                isInitialized = true;
                curOrder = documentSnapshots.toObject(OnlineOrders.class);
                if (AppConstants.isValidString(curOrder.CouponCode)) {
                    mApply.setVisibility(View.GONE);
                    findViewById(R.id.removecoupon).setVisibility(View.VISIBLE);
                    findViewById(R.id.applytxt).setVisibility(View.VISIBLE);

                }
                bindData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgress();
                isInitialized = false;
                showSnacbarInd("Something went wrong refresh or go back and try again");

            }
        });
    }

    private void showSnacbarInd(String msg) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.show();
    }

    @Override
    public void applyCoupon(String couponid) {
        showProgress("Applying Coupon");
        DocumentReference cd = couponRef.document(couponid);
        cd.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Coupons couponObj = documentSnapshot.toObject(Coupons.class);
                if (couponObj.RedeemCount < couponObj.MaxRedemptions) {
                    double offerValue = Double.parseDouble(curOrder.ExtendedTotal) - couponObj.Value;

                    curOrder.ExtendedTotal = AppConstants.bigDecimalString(offerValue);
                    curOrder.CouponCode = couponObj.Id;
                    curOrder.CouponDiscountAmount = AppConstants.bigDecimalString(couponObj.Value);
                    onlineRef.document(mOrderTS).set(curOrder, SetOptions.merge());

                    couponObj.RedeemCount = couponObj.RedeemCount + 1;
                    couponRef.document(couponObj.Id).set(couponObj, SetOptions.merge());

                    mApply.setVisibility(View.GONE);
                    findViewById(R.id.removecoupon).setVisibility(View.VISIBLE);
                    findViewById(R.id.applytxt).setVisibility(View.VISIBLE);

                    hideProgress();
                    bindData();
                } else {
                    hideProgress();
                    showSnacbarInd("Maximum redeemptions availed for this coupon.");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    @Override
    public void getCoupons() {

        AppConstants.arrCoupons = new Vector<>();
        Query query = couponRef.whereEqualTo("Type", 2).whereEqualTo("Active", true);
        Query querySelf = couponRef.whereEqualTo("ReferBy", cUid).whereEqualTo("Active", true);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                    Coupons mCoupon = document.toObject(Coupons.class);
                    AppConstants.arrCoupons.add(mCoupon);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Print coupons", "Failed");
            }
        });
        querySelf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                    Coupons mCoupon = document.toObject(Coupons.class);
                    AppConstants.arrCoupons.add(mCoupon);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Print coupons", "Failed");
            }
        });
    }

    @Override
    public void showcouponSheet() {
        TextView couponTxt = bottoms.findViewById(R.id.coupontxt);
        couponTxt.setTypeface(customFont);

        if (AppConstants.arrCoupons.size() > 0) {
            bottoms.findViewById(R.id.elogo).setVisibility(View.GONE);
        }

        CouponAdapter couponAdapter = new CouponAdapter(CheckoutScreen.this, AppConstants.arrCoupons);
        couponRecycler.setAdapter(couponAdapter);
        couponAdapter.notifyDataSetChanged();

        couponRecycler.addOnItemTouchListener(new RecyclerTouchListener(CheckoutScreen.this, couponRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                if (Double.parseDouble(curOrder.Order_SubTotal) >= AppConstants.arrCoupons.get(position).MinBasketValue) {
                    applyCoupon(AppConstants.arrCoupons.get(position).Id);
                } else {
                    double needvalue = AppConstants.arrCoupons.get(position).MinBasketValue - Double.parseDouble(curOrder.Order_SubTotal);
                    String msg = "Need to add Rs." + String.valueOf(needvalue) + " or more value items to apply this coupon";
                    showSnacbarInd(msg);
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        dialog.show();
    }

    @Override
    public void removeCoupon() {
        showProgress("Removing Coupon");
        DocumentReference cd = couponRef.document(curOrder.CouponCode);
        cd.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Coupons couponObj = documentSnapshot.toObject(Coupons.class);
                double offerValue = Double.parseDouble(curOrder.ExtendedTotal) + couponObj.Value;

                curOrder.ExtendedTotal = AppConstants.bigDecimalString(offerValue);
                curOrder.CouponCode = "";
                curOrder.CouponDiscountAmount = "0.00";
                onlineRef.document(mOrderTS).set(curOrder, SetOptions.merge());

                couponObj.RedeemCount = couponObj.RedeemCount - 1;
                couponRef.document(couponObj.Id).set(couponObj, SetOptions.merge());

                mApply.setVisibility(View.VISIBLE);
                findViewById(R.id.removecoupon).setVisibility(View.GONE);
                findViewById(R.id.applytxt).setVisibility(View.GONE);

                hideProgress();
                bindData();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showSnacbarInd("Failed to remove. please try again or contact support team.");
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(CheckoutScreen.this, OrderItemScreen.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
