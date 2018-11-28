package com.xapptree.ginger;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ryanharter.android.tooltips.ToolTip;
import com.ryanharter.android.tooltips.ToolTipLayout;
import com.xapptree.ginger.interfaces.TooltipInterface;
import com.xapptree.ginger.model.Categories;
import com.xapptree.ginger.model.LineItemRealm;
import com.xapptree.ginger.pagers.MenuPager;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RealmController;

import java.util.Collections;
import java.util.Comparator;

import io.realm.RealmResults;

public class MenuScreen extends GingerBaseActivity implements TooltipInterface {
    private ViewPager mViewPager;
    private SmartTabLayout tabLayout;
    private IntentFilter mIntentFilter;
    private TextView tCart;
    private RealmResults<LineItemRealm> arrdata;
    private ProgressBar progressBar;
    private RelativeLayout goCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_screen);

        /*Initializing broadcast action*/
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(AppConstants.mBroadcastAction);

        /*Widgets ref*/
        mViewPager = findViewById(R.id.menupager);
        progressBar = findViewById(R.id.progressBar);
        ImageButton mMenu = findViewById(R.id.menu);
        tabLayout = findViewById(R.id.tabs);
        tCart = findViewById(R.id.carttxt);
        TextView tHeader = findViewById(R.id.header);
        goCart = findViewById(R.id.gocart);

        /*Get cart from Realm DB*/
        arrdata = RealmController.with(MenuScreen.this).getCart();

        /*Setting Fonts*/
        AppConstants.overrideFonts(getApplicationContext(), findViewById(R.id.coordinatorLayout), "Raleway-Regular.ttf");
        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");
        tHeader.setTypeface(customFont);

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuScreen.this, RestaurentScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });
        goCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrdata.size() > 0) {
                    Intent i = new Intent(MenuScreen.this, OrderItemScreen.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                } else {
                    showEmptycartPopup();
                }
            }
        });
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!AppConstants.Cartooltip) {
            AppConstants.Cartooltip = true;
            showTooltip("Basket View", "Click to view items in your basket!", goCart);
        }
        setupData();
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        super.onConnectivityChanged(isConnected);
        if (!isConnected) {
            showNetworkPopUp();
        } else {
            hideNetorkPopUp();
        }
    }

    private void setupData() {
        Collections.sort(AppConstants.arrCategories, new IntegerComparator());
        MenuPager menuPagerAdapter = new MenuPager(MenuScreen.this, AppConstants.arrCategories);
        mViewPager.setAdapter(menuPagerAdapter);
        tabLayout.setViewPager(mViewPager);
        menuPagerAdapter.notifyDataSetChanged();

        if (arrdata.size() > 0) {
            bindData();
        }
        progressBar.setVisibility(View.GONE);
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

    private void bindData() {
        int totalQuan = RealmController.with(MenuScreen.this).getItemsQuantity();

        if (totalQuan > 0) {
            tCart.setVisibility(View.VISIBLE);
            tCart.setText(String.valueOf(totalQuan));
        } else {
            tCart.setVisibility(View.GONE);
            tCart.setText("0");
        }
    }

    private void showEmptycartPopup() {
        final Dialog dialog = new Dialog(MenuScreen.this, R.style.mydialogstyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.emptycartpopup);
        dialog.setCancelable(true);
        Button mcancel = dialog.findViewById(R.id.dialog_Done);

        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
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
                .gravity(Gravity.BOTTOM)      // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                .color(R.color.colorBlue)          // The color of the pointer arrow
                .pointerSize(15) // The size of the pointer
                .contentView(contentView)  // The actual contents of the ToolTip
                .build();
        tipContainer.addTooltip(t);
    }

    public class IntegerComparator implements Comparator<Categories> {

        @Override
        public int compare(Categories c1, Categories c2) {
            return c1.SequenceId - c2.SequenceId;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(MenuScreen.this, RestaurentScreen.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
