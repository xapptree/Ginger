package com.xapptree.ginger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xapptree.ginger.adapters.CouponAdapter;
import com.xapptree.ginger.interfaces.GingerInterface;
import com.xapptree.ginger.model.Coupons;
import com.xapptree.ginger.utilities.AppConstants;

import java.util.Vector;

public class OfferScreen extends GingerBaseActivity implements GingerInterface {
    private String cUID;
    private RecyclerView couponRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_offer_screen);
        ImageButton mMenu = findViewById(R.id.menu);
        TextView tHeader = findViewById(R.id.header);
        couponRecycler = findViewById(R.id.coupon_recycler);

         /*Setting Fonts*/
        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");
        tHeader.setTypeface(customFont);

          /*Customer UID from sp*/
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        cUID = sp.getString("Uid", "");

         /*RecyclerView options*/
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        couponRecycler.setLayoutManager(mLayoutManager);
        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.START);
        snapHelperTop.attachToRecyclerView(couponRecycler);

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OfferScreen.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
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
    public void getDataFireStore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference couponRef = db.collection("Coupons");
        AppConstants.arrCoupons = new Vector<>();
        Query query = couponRef.whereEqualTo("Type", 2).whereEqualTo("Active", true);
        Query querySelf = couponRef.whereEqualTo("ReferBy", cUID).whereEqualTo("Active", true);
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
                setupData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Print coupons", "Failed");
                setupData();
            }
        });

    }

    private void setupData() {
        CouponAdapter couponAdapter = new CouponAdapter(OfferScreen.this,AppConstants.arrCoupons);
        couponRecycler.setAdapter(couponAdapter);
        couponAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(OfferScreen.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
