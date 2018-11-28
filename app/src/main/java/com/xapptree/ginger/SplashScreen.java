package com.xapptree.ginger;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.xapptree.ginger.interfaces.GingerInterface;

public class SplashScreen extends GingerBaseActivity implements GingerInterface {
    private static Handler handler = new Handler();
    private FirebaseAuth mAuth;
    private String pushToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        TextView mHeaderName = findViewById(R.id.appname);
        TextView mH2 = findViewById(R.id.appname2);
        TextView mH3 = findViewById(R.id.appname3);
        TextView mSubtitle = findViewById(R.id.info);

        final AssetManager am = getApplicationContext().getAssets();
        Typeface customFont = Typeface.createFromAsset(am, "Raleway-Regular.ttf");
        Typeface customFont2 = Typeface.createFromAsset(am, "FEASFBRG.TTF");

        mHeaderName.setTypeface(customFont2);
        mH2.setTypeface(customFont2);
        mH3.setTypeface(customFont2);
        mSubtitle.setTypeface(customFont);

        //Firebase Auth start
        mAuth = FirebaseAuth.getInstance();
           /*Firebase Token */
        FirebaseMessaging.getInstance().subscribeToTopic("Ginger");// This Topic Name can be change for Topic Notification Purpose .
        pushToken = FirebaseInstanceId.getInstance().getToken();

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

               /*FireStore Database ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        final CollectionReference customerRef = mDatabase.collection("Customers");

          /* New Handler to start the Menu-Activity
//         * and close this Splash-Screen after some seconds.*/
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    customerRef.document(currentUser.getUid()).update("PushId", pushToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                           /* Create an Intent that will start the Menu-Activity. */
                            Intent i = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(i);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            finish();
                        }
                    });

                } else {
                    Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                }

            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
