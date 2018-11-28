package com.xapptree.ginger;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ryanharter.android.tooltips.ToolTip;
import com.ryanharter.android.tooltips.ToolTipLayout;
import com.xapptree.ginger.adapters.OrderItemAdapter;
import com.xapptree.ginger.interfaces.GingerInterface;
import com.xapptree.ginger.interfaces.TooltipInterface;
import com.xapptree.ginger.model.LineItemRealm;
import com.xapptree.ginger.model.OnlineOrders;
import com.xapptree.ginger.model.OrderItems;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RealmController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderItemScreen extends GingerBaseActivity implements GingerInterface, TooltipInterface {
    private RecyclerView cartItems;
    private IntentFilter mIntentFilter;
    private CoordinatorLayout coordinatorLayout;
    private ImageView mlogo;
    private TextView tStoreName, tSpecialize, tRating, tCusName;
    private TextView tSubtotal, tDiscount, tGst, tDeliveryCharge, tTotal;
    private double oDeliveryCharge;
    private double oProcessingCharge;
    private double oAdditionalCharge = 0.00;
    private String cName, cMobile, cEmail, cUid, sCouponcode, sCouponValue;
    private EditText mMobile, mInstructions;
    private CollectionReference onlineRef;
    private CollectionReference TimeStampRef;
    private CollectionReference configRef;
    private String mOrderTS, mOrderType;
    private Double subtotal, EXTD_subtotal, discountAmount, mGST, totalamount, EXTD_total;
    private Boolean IsDeliverSelect = false;
    private Typeface customFont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_item_screen);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(AppConstants.mBroadcastAction);

         /*Db ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        onlineRef = mDatabase.collection("OnlineOrders");
        TimeStampRef = mDatabase.collection("TimeStampValue");
        configRef = mDatabase.collection("Configurations");

        cartItems = findViewById(R.id.cart_recycler);
        TextView tHeader = findViewById(R.id.header);
        tStoreName = findViewById(R.id.restro_name);
        tSpecialize = findViewById(R.id.specialize);
        tRating = findViewById(R.id.rating);
        tCusName = findViewById(R.id.customername);
        tSubtotal = findViewById(R.id.subtotal);
        tDiscount = findViewById(R.id.discount);
        tGst = findViewById(R.id.gst);
        tDeliveryCharge = findViewById(R.id.delivery);
        tTotal = findViewById(R.id.total);
        mlogo = findViewById(R.id.banner);
        Button mCheckout = findViewById(R.id.checkout);
        mMobile = findViewById(R.id.mobile);
        mInstructions = findViewById(R.id.instructions);
        final TextView adChargeInfo = findViewById(R.id.ad_charges);
        Button mVoid = findViewById(R.id.btnvoid);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        ImageButton mMenu = findViewById(R.id.menu);
        LinearLayout pickview = findViewById(R.id.pickuplay);
        LinearLayout deliverview = findViewById(R.id.delverylay);
        final TextView helpTxt = findViewById(R.id.helptxt);
        final LottieAnimationView pv = findViewById(R.id.ptick);
        final LottieAnimationView hdv = findViewById(R.id.hdtick);

        cartItems.setNestedScrollingEnabled(false);

        //Get Cart
        AppConstants.arrLineItemRealm = RealmController.with(OrderItemScreen.this).getCart();

        /*Setting Fonts*/
        AppConstants.overrideFonts(getApplicationContext(), findViewById(R.id.coordinatorLayout), "Raleway-Regular.ttf");
        final AssetManager am = this.getApplicationContext().getAssets();
        customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");
        tHeader.setTypeface(customFont);

           /*Data from shared prefs*/
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        cName = sp.getString("UserName", "");
        cMobile = sp.getString("Phone", "");
        cEmail = sp.getString("Email", "");
        cUid = sp.getString("Uid", "");
        mOrderTS = sp.getString("OrderTs", "");
        boolean isTimeStampCreated = sp.getBoolean("IsTimeStampCreated", false);


        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderItemScreen.this, MenuScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });

        mVoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voidBasket();
            }
        });

        pickview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsDeliverSelect = true;
                pv.setVisibility(View.VISIBLE);
                hdv.setVisibility(View.GONE);
                helpTxt.setText(R.string.pickup);
                mOrderType = "1";
                oAdditionalCharge = oProcessingCharge;
                //oDeliveryCharge = 0.00;
                //oProcessingCharge = 10.00;
                bindData();
            }
        });
        deliverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsDeliverSelect = true;
                pv.setVisibility(View.GONE);
                hdv.setVisibility(View.VISIBLE);
                helpTxt.setText(R.string.homedeliver);
                mOrderType = "2";
                oAdditionalCharge = oDeliveryCharge;
                //oDeliveryCharge = 30.00;
                //oProcessingCharge = 0.00;
                bindData();
            }
        });

        adChargeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTooltip("Additional Charges", "* Includes Delivery charges or Processing charges", adChargeInfo);

            }
        });

        mCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstants.isValidMobile(mMobile.getText().toString())) {
                    if (IsDeliverSelect) {
                        // getBasketData();
                        EXTD_total = EXTD_total - Double.parseDouble(sCouponValue);
                        makeOrder();
                    } else {
                        showSnacbarMsg("Select delivery option.");
                    }

                } else {
                    showSnacbarMsg("Enter Mobile Number.");
                }
            }
        });

        Map<String, Object> doc = new HashMap<>();
        doc.put("timestamp", FieldValue.serverTimestamp());

        if (!isTimeStampCreated) {
            sCouponValue = "0.00";
            sCouponcode = "";

            TimeStampRef.document("TS").set(doc).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    getServerTimeStamp();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mOrderTS = AppConstants.timeStamp();
                    saveTimestamp(true);
                }
            });
        } else {
            getBasketData();
        }

        setUpData();
    }

    private void setUpData() {
        tStoreName.setText(AppConstants.CurStore.StoreName);
        tSpecialize.setText(AppConstants.CurStore.Specialize);
        tRating.setText(AppConstants.CurStore.StoreRating);
        tCusName.setText(cName);

        GlideApp.with(getApplicationContext()).load(AppConstants.CurStore.StoreBanner).error(R.drawable.imagepreviewbig).into(mlogo);

    }

    @Override
    protected void onStart() {
        super.onStart();
        showTooltip("Mobile Number", "Provide valid mobile number for further contact regarding order!", mMobile);

        bindData();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void bindData() {
        subtotal = RealmController.with(OrderItemScreen.this).getExtCartSubtotal();
        tDeliveryCharge.setText(AppConstants.uniText(oAdditionalCharge));
        tSubtotal.setText(AppConstants.uniText(subtotal));
        //Discount
        if (AppConstants.CurStore.IsOfferAvailable) {
            if (subtotal >= AppConstants.CurStore.MinBasketValue) {
                double oDP = AppConstants.CurStore.DiscountPercent;
                discountAmount = subtotal * oDP / 100;
                EXTD_subtotal = subtotal - discountAmount;
                tDiscount.setText(AppConstants.uniText(discountAmount));
            } else {
                EXTD_subtotal = subtotal;
                discountAmount = 0.00;
                tDiscount.setText(AppConstants.uniText(0.00));
            }
        } else {
            EXTD_subtotal = subtotal;
            discountAmount = 0.00;
            tDiscount.setText(AppConstants.uniText(0.00));
        }
        //Gst
        double oGst = AppConstants.CurStore.GST;
        mGST = EXTD_subtotal * oGst / 100;
        tGst.setText(AppConstants.uniText(mGST));

        //Total
        totalamount = EXTD_subtotal + mGST;
        EXTD_total = EXTD_subtotal + mGST + oAdditionalCharge;
        tTotal.setText(AppConstants.uniText(EXTD_total));


        // setup cart
        if (AppConstants.arrLineItemRealm.size() > 0) {
            OrderItemAdapter orderItemAdapter = new OrderItemAdapter(OrderItemScreen.this, AppConstants.arrLineItemRealm);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderItemScreen.this);
            cartItems.setLayoutManager(mLayoutManager);
            cartItems.setAdapter(orderItemAdapter);
            orderItemAdapter.notifyDataSetChanged();
        } else {
            showEmptycartPopup();
        }

    }

    private void showEmptycartPopup() {
        final Dialog dialog = new Dialog(OrderItemScreen.this, R.style.mydialogstyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.emptycartpopup);
        dialog.setCancelable(true);
        Button mcancel = (Button) dialog.findViewById(R.id.dialog_Done);

        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(OrderItemScreen.this, MenuScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();

            }
        });
        dialog.show();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(AppConstants.mBroadcastAction)) {
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run() {
                        //some work
                        bindData();
                    }
                });
            }
        }
    };

    private void makeOrder() {
        showProgress("Calculating Basket..");

        int itemsQuantity = RealmController.with(OrderItemScreen.this).getItemsQuantity();
        OnlineOrders user = new OnlineOrders();
        user.Order_Id = mOrderTS;
        user.Order_Date = AppConstants.currentDate();
        user.Order_Time = AppConstants.toDate();

        user.PrefferedStore = AppConstants.CurStore.StoreId;
        user.StoreBannerUrl = AppConstants.CurStore.StoreBanner;
        user.StoreName = AppConstants.CurStore.StoreName;

        user.CustomerUid = cUid;
        user.CustomerMobile = mMobile.getText().toString();
        user.CustomerEmail = cEmail;
        user.CustomerName = cName;

        user.DeliveryAddress = AppConstants.OrderAddress;
        user.Latitude = AppConstants.latitude;
        user.Longitude = AppConstants.longitude;

        user.Order_SubTotal = AppConstants.bigDecimalString(subtotal);
        user.DiscountAmount = AppConstants.bigDecimalString(discountAmount);
        user.Extended_Subtotal = AppConstants.bigDecimalString(EXTD_subtotal);
        user.Order_GST = AppConstants.bigDecimalString(mGST);
        user.Order_Total = AppConstants.bigDecimalString(totalamount);
        user.ExtendedTotal = AppConstants.bigDecimalString(EXTD_total);

        user.CouponCode = sCouponcode;
        user.CouponDiscountAmount = sCouponValue;

        user.Order_PaymentMode = "Not Intiated";
        user.OrderPaid_Amount = "0.00";
        user.OrderPaymentState = "Not Paid";
        user.Payment_ReferId = "";

        user.Order_Status = "Basket Created";
        user.Order_StatusCode = "50";
        user.Order_Type = mOrderType;// 1 Pickup or 2 HomDelivery

        user.Order_ItemsQuantity = String.valueOf(itemsQuantity);
        user.Order_DeliveryCharge = AppConstants.bigDecimalString(oAdditionalCharge);
        user.Order_ProcessingCharge = "0.00";
        user.Order_Instructions = mInstructions.getText().toString();
        user.Order_Modified = "0";

        user.AgentId = "";
        user.AgentName = "";

        user.Order_CGST = "0.00";
        user.Order_SGST = "0.00";


        for (int i = 0; i < AppConstants.arrLineItemRealm.size(); i++) {
            LineItemRealm itemRealm = new LineItemRealm();
            itemRealm = AppConstants.arrLineItemRealm.get(i);
            OrderItems orderItems = new OrderItems();
            orderItems.ItemId = itemRealm.getItemId();
            orderItems.ItemName = itemRealm.getItemName();
            orderItems.ItemPrice = itemRealm.getItemPrice();
            orderItems.Item_Quantity = itemRealm.getItemQuantity();
            orderItems.Item_Total = itemRealm.getItemTotal();
            orderItems.Item_Available = true;
            onlineRef.document(mOrderTS).collection("OrderItems").document(orderItems.ItemId).set(orderItems);
        }
        // pushing user to 'users' node using the userId
        onlineRef.document(mOrderTS).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                hideProgress();

                Intent i = new Intent(OrderItemScreen.this, CheckoutScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("OnlineOrders", "Error writing document", e);
                hideProgress();
                showSnacbarMsg("Error occured, Please try again.\n");
            }
        });
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

    private void saveTimestamp(boolean b) {
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("IsTimeStampCreated", b);
        editor.putString("OrderTs", mOrderTS);
        editor.commit();
    }

    private void getServerTimeStamp() {
        TimeStampRef.document("TS").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        Map<String, Object> map = snapshot.getData();
                        Date date = (Date) map.get("timestamp");
                        Log.d("tag", "date=" + date);
                        mOrderTS = String.valueOf(date.getTime());
                        saveTimestamp(true);
                    } else {
                        Log.d("tag", "No such document");
                    }
                } else {
                    Log.d("tag", "get() failed with ", task.getException());
                }
            }
        });
    }

    private void showSnacbarMsg(String msg) {
        final Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        snackbar.show();
    }

    private void voidBasket() {
        View bottoms = getLayoutInflater().inflate(R.layout.void_lay, null);
        Button mvoidBtn = bottoms.findViewById(R.id.submitbtn);
        final BottomSheetDialog dialog = new BottomSheetDialog(OrderItemScreen.this);
        dialog.setContentView(bottoms);
        mvoidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOrderTS = "";
                saveTimestamp(false);
                dialog.dismiss();
                RealmController.with(OrderItemScreen.this).EmptyCart(OrderItemScreen.this);
            }
        });
        dialog.show();
    }

    @Override
    public void getDataFireStore() {
        configRef.document("CHARGES").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        oDeliveryCharge = snapshot.getDouble("DeliveryCharge");
                        oProcessingCharge = snapshot.getDouble("ProcessingCharge");
                    } else {
                        oDeliveryCharge = 30.00;
                        oProcessingCharge = 10.00;
                    }
                } else {
                    Log.d("tag", "get() failed with ", task.getException());
                    oDeliveryCharge = 30.00;
                    oProcessingCharge = 10.00;
                }
            }
        });
    }

    private void getBasketData() {
        DocumentReference od = onlineRef.document(mOrderTS);
        od.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    OnlineOrders oOrder = documentSnapshot.toObject(OnlineOrders.class);
                    sCouponcode = oOrder.CouponCode;
                    sCouponValue = oOrder.CouponDiscountAmount;
                    mMobile.setText(oOrder.CustomerMobile);
                    mInstructions.setText(oOrder.Order_Instructions);

                } else {
                    sCouponValue = "0.00";
                    sCouponcode = "";
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sCouponValue = "0.00";
                sCouponcode = "";
            }
        });
    }

    @Override
    public void showTooltip(String header, String message, View tView) {
        // Get the tooltip layout
        ToolTipLayout tipContainer = findViewById(R.id.tooltip_container);
        View contentView = getLayoutInflater().inflate(R.layout.tooltip_view, null);
        TextView theader = contentView.findViewById(R.id.tooltip_txt);
        TextView tmsg = contentView.findViewById(R.id.tooltip_msg);
        theader.setText(header);
        tmsg.setText(message);

        // Create a ToolTip using the Builder class
        ToolTip t = new ToolTip.Builder(getApplicationContext())
                .anchor(tView)      // The view to which the ToolTip should be anchored
                .gravity(Gravity.TOP)      // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                .color(R.color.colorBlue)          // The color of the pointer arrow
                .pointerSize(15) // The size of the pointer
                .contentView(contentView)  // The actual contents of the ToolTip
                .build();
        tipContainer.addTooltip(t);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(OrderItemScreen.this, MenuScreen.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
