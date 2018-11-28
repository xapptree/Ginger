package com.xapptree.ginger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xapptree.ginger.adapters.RestaurentAdapter;
import com.xapptree.ginger.interfaces.GingerInterface;
import com.xapptree.ginger.model.Categories;
import com.xapptree.ginger.model.Items;
import com.xapptree.ginger.model.Stores;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RealmController;
import com.xapptree.ginger.utilities.RecyclerTouchListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class RestaurentScreen extends GingerBaseActivity implements GingerInterface {
    private RecyclerView restro_recycler;
    private CollectionReference categoryRef;
    private CollectionReference itemsRef;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_restaurent_screen);

        TextView mHeaderName = findViewById(R.id.header);
        ImageButton mMenu = findViewById(R.id.menu);
        ImageButton mFilter = findViewById(R.id.filter);
        restro_recycler = findViewById(R.id.restaurent_recycler);

        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont2 = Typeface.createFromAsset(am, "FEASFBRG.TTF");
        mHeaderName.setTypeface(customFont2);

        /*DB ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        categoryRef = mDatabase.collection("Categories");
        itemsRef = mDatabase.collection("Items");

        /*RecyclerView options*/
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        restro_recycler.setLayoutManager(mLayoutManager);
        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.START);
        snapHelperTop.attachToRecyclerView(restro_recycler);

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurentScreen.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });

        restro_recycler.addOnItemTouchListener(new RecyclerTouchListener(RestaurentScreen.this, restro_recycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
                String sid = sp.getString("STOREID", "");

                if (AppConstants.arrStores.get(position).IsStoreOpen) {
                    AppConstants.StoreName = AppConstants.arrStores.get(position).StoreName;
                    AppConstants.StoreBanner = AppConstants.arrStores.get(position).StoreBanner;
                    AppConstants.CurStore = AppConstants.arrStores.get(position);
                    showProgress("Fetching Menu..");
                    //Check cart
                    if (sid.equalsIgnoreCase(AppConstants.CurStore.StoreId)) {
                        RealmController.with(RestaurentScreen.this).deleteStoreItems();
                        getCategoryData(AppConstants.arrStores.get(position).StoreId);
                    } else {
                        RealmController.with(RestaurentScreen.this).deleteStoreItems();
                        RealmController.with(RestaurentScreen.this).deleteCart();

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("STOREID", AppConstants.CurStore.StoreId);
                        editor.commit();

                        getCategoryData(AppConstants.arrStores.get(position).StoreId);
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override

    protected void onStart() {
        super.onStart();
        mShimmerViewContainer = findViewById(R.id.emptyview);
        mShimmerViewContainer.setAngle(ShimmerFrameLayout.MaskAngle.CW_0);
        mShimmerViewContainer.setBaseAlpha(0.5f);
        mShimmerViewContainer.setDuration(1000);
        mShimmerViewContainer.setDropoff(0.3f);
        mShimmerViewContainer.setIntensity(0.0f);
        mShimmerViewContainer.setRelativeWidth(1);
        mShimmerViewContainer.setRelativeHeight(1);
        mShimmerViewContainer.setMaskShape(ShimmerFrameLayout.MaskShape.LINEAR);
        mShimmerViewContainer.startShimmerAnimation();
    }

    private void delaysplash() {
            /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupData();

            }
        }, 1500);

    }


    private void setupData() {
        Collections.sort(AppConstants.arrStores, new StoreComparator());
        RestaurentAdapter restaurentAdapter = new RestaurentAdapter(RestaurentScreen.this, AppConstants.arrStores);
        restro_recycler.setAdapter(restaurentAdapter);
        restaurentAdapter.notifyDataSetChanged();

        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        restro_recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void getDataFireStore() {
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        String sid = sp.getString("StoreId", "");

        Query query = itemsRef.whereEqualTo("StoreId", sid).whereEqualTo("Availability", true);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot taskSnapshots) {
                hideProgress();
                AppConstants.arrStoreItems = new Vector<>();
                for (DocumentSnapshot itemSnapshot : taskSnapshots.getDocuments()) {
                    Items mCat = itemSnapshot.toObject(Items.class);
                    AppConstants.arrStoreItems.add(mCat);
                }
//                Gson gson = new GsonBuilder().create();
//                JsonArray jsArray = gson.toJsonTree(AppConstants.arrStoreItems).getAsJsonArray();
//                RealmController.with(RestaurentScreen.this).saveStoreItems(jsArray.toString());

                // Move to MenuScreen
                Intent i = new Intent(RestaurentScreen.this, MenuScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgress();

            }
        });

    }

    public class StoreComparator implements Comparator<Stores> {

        @Override
        public int compare(Stores m1, Stores m2) {
            Boolean val1 = m1.IsStoreOpen;
            Boolean val2 = m2.IsStoreOpen;
            return val2.compareTo(val1);
        }
    }

    private void getCategoryData(final String storeId) {
        Query query = categoryRef.whereEqualTo("StoreId", storeId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    AppConstants.arrCategories = new Vector<>();
                    if (task.getResult() != null) {
                        //data exists, do something
                        for (DocumentSnapshot categorySnapshot : task.getResult()) {
                            Categories mCat = categorySnapshot.toObject(Categories.class);
                            AppConstants.arrCategories.add(mCat);
                        }
                        if (AppConstants.arrCategories.size() > 0) {
                            AppConstants.StoreId = storeId;
                               /*Save user details to local sp*/
                            SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("StoreId", storeId);
                            editor.commit();

                            getDataFireStore();

                        } else {
                            hideProgress();
                        }

                    } else {
                        // No categories
                        hideProgress();
                    }
                } else {
                    hideProgress();

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
            delaysplash();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(RestaurentScreen.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
