package com.xapptree.ginger.pagers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xapptree.ginger.R;
import com.xapptree.ginger.adapters.ItemsAdapter;
import com.xapptree.ginger.model.Categories;
import com.xapptree.ginger.model.Items;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RealmController;

import java.util.Comparator;
import java.util.Vector;

/**
 * Created by Akbar on 1/9/2017.
 */
public class MenuPager extends PagerAdapter {
    private Context mainActivity;
    private Vector<Categories> arrCCategory = null;
    private CollectionReference itemRef;

    public MenuPager(Context mainActivity, Vector<Categories> arrCCategory) {
        // TODO Auto-generated constructor stub
        this.mainActivity = mainActivity;
        this.arrCCategory = arrCCategory;
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        itemRef = mDatabase.collection("Items");

    }

    @Override
    public int getCount() {
        return this.arrCCategory.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrCCategory.get(position).CategoryName;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        // TODO Auto-generated method stub
        final Categories objCategories = this.arrCCategory.get(position);
        LayoutInflater mLayoutInflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.menupager_listitem, container, false);
        final RecyclerView mRecyclerView = itemView.findViewById(R.id.items_listview);

        if (objCategories.SequenceId == 1) {
//            final Query query = itemRef.whereEqualTo("IsRecommended", true).whereEqualTo("Availability", true);
//            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        AppConstants.arrItems = new Vector<Items>();
//                        if (task.getResult() != null) {
//                            //user exists, do something
//                            for (DocumentSnapshot itemSnapshot : task.getResult()) {
//                                Items mItem = itemSnapshot.toObject(Items.class);
//                                AppConstants.arrItems.add(mItem);
//                            }
//                            ItemsAdapter adapter = new ItemsAdapter(mainActivity, AppConstants.arrItems, true);
//                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mainActivity, 2);
//                            mRecyclerView.setLayoutManager(mLayoutManager);
//                            SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
//                            snapHelperTop.attachToRecyclerView(mRecyclerView);
//                            mRecyclerView.setAdapter(adapter);
//
//
//                        } else {
//                            // No categories
//                        }
//                    } else {
//                        //Log.d(TAG, "Error getting documents: ", task.getException());
//
//                    }
//                }
//            });
            AppConstants.arrItems = new Vector<Items>();
           // AppConstants.arrItems = RealmController.with((Activity) mainActivity).getRecommended();

            for (Items mCat : AppConstants.arrStoreItems) {
               if(mCat.IsRecommended){
                   AppConstants.arrItems.add(mCat);
               }
            }

            ItemsAdapter adapter = new ItemsAdapter(mainActivity, AppConstants.arrItems, true);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mainActivity, 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
            snapHelperTop.attachToRecyclerView(mRecyclerView);
            mRecyclerView.setAdapter(adapter);
//
        } else {
//            final Query query = itemRef.whereEqualTo("CategoryId", objCategories.CategoryId).whereEqualTo("Availability", true);
//            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        AppConstants.arrItems = new Vector<Items>();
//                        if (task.getResult() != null) {
//                            //user exists, do something
//                            for (DocumentSnapshot itemSnapshot : task.getResult()) {
//                                Items mItem = itemSnapshot.toObject(Items.class);
//                                AppConstants.arrItems.add(mItem);
//                            }
//
//                            ItemsAdapter adapter = new ItemsAdapter(mainActivity, AppConstants.arrItems, false);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mainActivity);
//                            mRecyclerView.setLayoutManager(mLayoutManager);
//                            SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
//                            snapHelperTop.attachToRecyclerView(mRecyclerView);
//                            mRecyclerView.setAdapter(adapter);
//
//                        } else {
//                            // No categories
//                        }
//                    } else {
//                        //Log.d(TAG, "Error getting documents: ", task.getException());
//
//                    }
//                }
//            });
            AppConstants.arrItems = new Vector<Items>();
            //AppConstants.arrItems = RealmController.with((Activity) mainActivity).getItemsByCategory(objCategories.CategoryId);
            for (Items mCat : AppConstants.arrStoreItems) {
                if(mCat.CategoryId.equalsIgnoreCase(objCategories.CategoryId)){
                    AppConstants.arrItems.add(mCat);
                }
            }
            ItemsAdapter adapter = new ItemsAdapter(mainActivity, AppConstants.arrItems, false);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mainActivity);
            mRecyclerView.setLayoutManager(mLayoutManager);
            SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
            snapHelperTop.attachToRecyclerView(mRecyclerView);
            mRecyclerView.setAdapter(adapter);

        }

        container.addView(itemView);
        return itemView;
    }

    public class ItemComparator implements Comparator<Items> {

        @Override
        public int compare(Items m1, Items m2) {
            String val1 = m1.CategoryId;
            String val2 = m2.CategoryId;
            return val2.compareTo(val1);
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
}
