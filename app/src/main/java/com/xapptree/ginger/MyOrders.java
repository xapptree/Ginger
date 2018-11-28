package com.xapptree.ginger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xapptree.ginger.adapters.OrderAdapter;
import com.xapptree.ginger.interfaces.GingerInterface;
import com.xapptree.ginger.model.OnlineOrders;
import com.xapptree.ginger.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyOrders extends GingerBaseActivity implements GingerInterface {
    private CollectionReference onlineRef;
    private List<OnlineOrders> mOrders;
    private RecyclerView orderRecycler;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_orders);

        /*Widget REf*/
        progressBar = findViewById(R.id.progressBar);
        ImageButton mMenu = findViewById(R.id.menu);
        orderRecycler = findViewById(R.id.order_recycler);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        TextView tHeader = findViewById(R.id.header);

        progressBar.setVisibility(View.VISIBLE);

        /*DB Ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        onlineRef = mDatabase.collection("OnlineOrders");

        /*Setting Fonts*/
        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");
        tHeader.setTypeface(customFont);


        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyOrders.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });

        orderRecycler.addOnItemTouchListener(new RecyclerTouchListener(MyOrders.this, orderRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(MyOrders.this, OrderDetailsScreen.class);
                i.putExtra("ORDERID", mOrders.get(position).Order_Id);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void bindOrders() {
        Collections.reverse(mOrders);
        OrderAdapter lineItemAdapter = new OrderAdapter(MyOrders.this, mOrders);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        orderRecycler.setLayoutManager(mLayoutManager);
        orderRecycler.setAdapter(lineItemAdapter);
        lineItemAdapter.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);
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
    public void getDataFireStore() {
          /*Customer UID from sp*/
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        String cUid = sp.getString("Uid", "");

        mOrders = new ArrayList<>();
        Query query = onlineRef.whereEqualTo("CustomerUid", cUid);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        //user exists, do something
                        for (DocumentSnapshot categorySnapshot : task.getResult()) {
                            OnlineOrders mitem = categorySnapshot.toObject(OnlineOrders.class);
                            mOrders.add(mitem);
                        }
                        bindOrders();
                    } else {
                        // No stores available
                        showSnacbarInd("OhNo!  you did'nt made any orders.");

                    }
                } else {
                    Log.d("Myorders", "Error getting documents: ", task.getException());
                    showSnacbarInd("Error getting Orders.");
                }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(MyOrders.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
