package com.xapptree.ginger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xapptree.ginger.interfaces.GingerInterface;
import com.xapptree.ginger.model.Customers;

public class ProfileScreen extends GingerBaseActivity implements GingerInterface {
    private Customers mCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_screen);

        ImageButton mMenu = findViewById(R.id.menu);
        TextView tHeader = findViewById(R.id.header);
        TextView tsubhead = findViewById(R.id.subheader);
        Button bInvite = findViewById(R.id.invite);

        mCat = new Customers();

         /*Setting Fonts*/
        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");
        tHeader.setTypeface(customFont);
        tsubhead.setTypeface(customFont);

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileScreen.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });
        bInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Ginger");
                    String sAux = "Install Ginger App and Register with " + mCat.Name + " referral code " + mCat.RefferalId + " and earn Rs.50.\n";
                    sAux = sAux + "https://play.google.com/store/apps/ \n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Invite to GINGER APP"));
                } catch (Exception e) {
                    //e.toString();
                }

            }
        });
    }

    private void bindData() {
        TextView tname = findViewById(R.id.name);
        TextView temail = findViewById(R.id.email);
        TextView tstatus = findViewById(R.id.status);
        ImageView iv = findViewById(R.id.pic);

        tname.setText(mCat.Name);
        temail.setText(mCat.Email);
        tstatus.setText(mCat.Status);

        GlideApp.with(ProfileScreen.this).load(mCat.PhotoUrl).error(R.drawable.imagepreviewbig).into(iv);

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
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        String sid = sp.getString("Uid", "");
        //FireStore REf
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        CollectionReference customerRef = mDatabase.collection("Customers");
        DocumentReference docRef = customerRef.document(sid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //user exists, do something
                        mCat = document.toObject(Customers.class);
                        bindData();
                    } else {

                    }
                } else {

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCat = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(ProfileScreen.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
