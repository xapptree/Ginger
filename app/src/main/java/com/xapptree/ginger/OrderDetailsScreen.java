package com.xapptree.ginger;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.xapptree.ginger.adapters.OrderDetailItemsAdpater;
import com.xapptree.ginger.interfaces.GingerInterface;
import com.xapptree.ginger.model.OnlineOrders;
import com.xapptree.ginger.model.OrderItems;
import com.xapptree.ginger.utilities.AppConstants;

import java.util.Vector;

public class OrderDetailsScreen extends GingerBaseActivity implements GingerInterface {
    private RecyclerView lineItemRecycler;
    private OrderDetailItemsAdpater lineItemAdapter;
    private CollectionReference onlineRef;
    private String orderId;
    private TextView tOrderId, tOverview, tStatus, tOrderType, tPaymentStatus;
    private TextView tSubtotal, tGst, tTotal, tDeliveryCharge, tDiscount, tCouponDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_details_screen);

        /*Widget Ref*/
        lineItemRecycler = findViewById(R.id.lineitemlist);
        tOrderId = findViewById(R.id.orderid);
        tOverview = findViewById(R.id.overview);
        tStatus = findViewById(R.id.status);
        tOrderType = findViewById(R.id.ordertype);
        tPaymentStatus = findViewById(R.id.paymentstatus);
        tSubtotal = findViewById(R.id.subtotal);
        tGst = findViewById(R.id.gst);
        tTotal = findViewById(R.id.total);
        tDiscount = findViewById(R.id.discount);
        tDeliveryCharge = findViewById(R.id.delivery);
        tCouponDiscount = findViewById(R.id.coupondiscount);
        ImageButton mMenu = findViewById(R.id.menu);

        /*Db Ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        onlineRef = mDatabase.collection("OnlineOrders");

        /*Setting Fonts*/
        AppConstants.overrideFonts(getApplicationContext(), findViewById(R.id.rootLayout), "Raleway-Light.ttf");
        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");

        tOrderId.setTypeface(customFont);
        tTotal.setTypeface(customFont);
        tPaymentStatus.setTypeface(customFont);
        tOrderType.setTypeface(customFont);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString("ORDERID", "0");
        }

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderDetailsScreen.this, MyOrders.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });
        tStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OrderDetailsScreen.this, TrackScreen.class);
//                startActivity(i);
//                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void bindItems() {
        tOrderId.setText("ORDER ID #" + AppConstants.cOrder.Order_Id);
        tOverview.setText(AppConstants.cOrder.Order_Status + " | " + "Items " + AppConstants.cOrder.Order_ItemsQuantity + " | " + AppConstants.uniString(AppConstants.cOrder.ExtendedTotal));
        if (AppConstants.cOrder.Order_Type.equalsIgnoreCase("1")) {
            tOrderType.setText("PICK-UP");
        } else {
            tOrderType.setText("HOME DELIVERY");
        }
        tPaymentStatus.setText(AppConstants.cOrder.OrderPaymentState);
        tSubtotal.setText(AppConstants.uniString(AppConstants.cOrder.Order_SubTotal));
        tDiscount.setText(AppConstants.uniString(AppConstants.cOrder.DiscountAmount));
        tGst.setText(AppConstants.uniString(AppConstants.cOrder.Order_GST));
        tDeliveryCharge.setText(AppConstants.uniString(AppConstants.cOrder.Order_DeliveryCharge));
        tCouponDiscount.setText(AppConstants.uniString(AppConstants.cOrder.CouponDiscountAmount));
        tTotal.setText(AppConstants.uniString(AppConstants.cOrder.ExtendedTotal));

        if (AppConstants.cOrder.Order_StatusCode.equalsIgnoreCase("300")) {
            tStatus.setVisibility(View.VISIBLE);
        } else {
            tStatus.setVisibility(View.GONE);
        }

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
        DocumentReference docRef = onlineRef.document(orderId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        AppConstants.cOrder = document.toObject(OnlineOrders.class);
                        bindItems();
                    } else {
                        Log.d("OrderDetailsScreen", "No Data found related to given OrderId. ", task.getException());
                    }
                } else {
                    Log.d("OrderDetailsScreen", "Error getting documents: ", task.getException());

                }
            }
        });

        CollectionReference orderItemRef = docRef.collection("OrderItems");
        orderItemRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AppConstants.arrOnlineItems = new Vector<OrderItems>();
                    if (task.getResult() != null) {
                        //user exists, do something
                        for (DocumentSnapshot categorySnapshot : task.getResult()) {
                            OrderItems mitem = categorySnapshot.toObject(OrderItems.class);
                            AppConstants.arrOnlineItems.add(mitem);
                        }

                        lineItemAdapter = new OrderDetailItemsAdpater(OrderDetailsScreen.this, AppConstants.arrOnlineItems);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        lineItemRecycler.setLayoutManager(mLayoutManager);
                        lineItemRecycler.setAdapter(lineItemAdapter);
                        lineItemAdapter.notifyDataSetChanged();

                    } else {
                        // No data

                    }
                } else {
                    Log.d("OrderDetailScreen", "Error getting documents: ", task.getException());

                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(OrderDetailsScreen.this, MyOrders.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
