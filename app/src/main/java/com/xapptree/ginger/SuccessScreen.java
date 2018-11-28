package com.xapptree.ginger;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xapptree.ginger.utilities.AppConstants;

public class SuccessScreen extends GingerBaseActivity {
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_success_screen);
        TextView mHeader = findViewById(R.id.header);
        Button bStatus = findViewById(R.id.orderstate);

        bStatus.setLongClickable(false);

        /*Setting Fonts*/
        AppConstants.overrideFonts(getApplicationContext(), findViewById(R.id.rootLayout), "Raleway-Light.ttf");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Raleway-Bold.ttf");
        mHeader.setTypeface(typeface);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString("ORDERID", "0");

        }

        bStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuccessScreen.this, OrderDetailsScreen.class);
                i.putExtra("ORDERID", orderId);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(SuccessScreen.this, OrderDetailsScreen.class);
            i.putExtra("ORDERID", orderId);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
