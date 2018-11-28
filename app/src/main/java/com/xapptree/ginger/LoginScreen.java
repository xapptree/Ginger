package com.xapptree.ginger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moe.pushlibrary.MoEHelper;
import com.xapptree.ginger.model.Customers;
import com.xapptree.ginger.utilities.AppConstants;

import java.security.SecureRandom;
import java.util.Random;

public class LoginScreen extends GingerBaseActivity implements View.OnClickListener {

    private CollectionReference customerRef;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private String pushToken;
    protected MoEHelper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        helper = MoEHelper.getInstance(this);

          /*FireStore Database ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        customerRef = mDatabase.collection("Customers");

         /*Widget ref*/
        TextView mHeaderName = findViewById(R.id.appname);

        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont2 = Typeface.createFromAsset(am, "FEASFBRG.TTF");
        mHeaderName.setTypeface(customFont2);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Firebase Auth start
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        /*Firebase Token */
        FirebaseMessaging.getInstance().subscribeToTopic("Ginger");// This Topic Name can be change for Topic Notification Purpose .
        pushToken = FirebaseInstanceId.getInstance().getToken();

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LoginScreen.this, "Authentication failed google.", Toast.LENGTH_SHORT).show();

                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // show progress
        showProgress("Please wait..");

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Update Ui
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //Update Ui
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(final FirebaseUser user) {
        if (user != null) {
            // Update unique Id
            MoEHelper.getInstance(LoginScreen.this).setAlias(user.getUid());
            //User Attibutes to Engage
            // Helper method to set User uniqueId. Can be String,int,long,float,double
            helper.setUniqueId(user.getUid());
            helper.setFirstName(user.getDisplayName());
            helper.setFullName(user.getDisplayName());
            helper.setEmail(user.getEmail());
            helper.setNumber(user.getPhoneNumber());

            /*Save user details to local sp*/
            SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("UserName", user.getDisplayName());
            editor.putString("Phone", user.getPhoneNumber());
            editor.putString("Email", user.getEmail());
            editor.putString("Uid", user.getUid());
            editor.putBoolean("IsFirstInstall", false);
            editor.putString("PhotoUrl", user.getPhotoUrl().toString());
            editor.commit();

            DocumentReference docRef = customerRef.document(user.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(final DocumentSnapshot document) {
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        //user exists, do something
                        customerRef.document(user.getUid()).update("PushId", pushToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Hide progress
                                hideProgress();

                                Intent i = new Intent(LoginScreen.this, MainActivity.class);
                                startActivity(i);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                finish();
                            }
                        });
                    } else {
                        // Hide progress
                        hideProgress();

                        final AppCompatDialog refDialog = new AppCompatDialog(LoginScreen.this);
                        refDialog.setContentView(R.layout.refferal_lay);
                        refDialog.setCancelable(false);
                        Button mcancel = refDialog.findViewById(R.id.dialog_skip);
                        Button msave = refDialog.findViewById(R.id.dialog_Done);
                        final EditText refferalCode = refDialog.findViewById(R.id.refcode);

                        mcancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                refDialog.dismiss();
                                requestRefferalCode(user, "");

                            }
                        });
                        msave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (AppConstants.isValidString(refferalCode.getText().toString())) {
                                    Query query = customerRef.whereEqualTo("RefferalId", refferalCode.getText().toString());
                                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot documentSnapshots) {
                                            Log.i("Login screen ", "Success");
                                            refDialog.dismiss();
                                            for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                                                Customers mCat = document.toObject(Customers.class);
                                                requestRefferalCode(user, mCat.Uid);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Login screen failed", e.toString());
                                            refDialog.dismiss();
                                            requestRefferalCode(user, "");
                                        }
                                    });

                                } else {
                                    refferalCode.setError("Enter Refferal Code");
                                }

                            }
                        });
                        refDialog.show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Hide progress
                    hideProgress();
                }
            });

        } else {
            //Error Message Login request
            // Hide progress
            hideProgress();
        }
    }

    private void requestRefferalCode(final FirebaseUser user, String refferalid) {
        showProgress("Applying refferal code");

        String refid = randomString(8);

        /*Register as customer*/
        /*Create new customer in DB*/
        Customers cobj = new Customers();
        cobj.Name = user.getDisplayName();
        cobj.Email = user.getEmail();
        cobj.Mobile = user.getPhoneNumber();
        cobj.Uid = user.getUid();
        cobj.PhotoUrl = user.getPhotoUrl().toString();
        cobj.Status = "Active";
        cobj.PushId = pushToken;
        cobj.RefferalId = refid;
        cobj.ReferedBy = refferalid;

        customerRef.document(user.getUid()).set(cobj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                hideProgress();
                Intent i = new Intent(LoginScreen.this, MainActivity.class);
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

    static final private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    final private Random rng = new SecureRandom();

    char randomChar() {
        return ALPHABET.charAt(rng.nextInt(ALPHABET.length()));
    }

    public String randomString(int length) {

        StringBuilder sb = new StringBuilder();
        int spacer = 0;
        while (length > 0) {
//            if(spacer == spacing){
//                sb.append(spacerChar);
//                spacer = 0;
//            }
            length--;
            spacer++;
            sb.append(randomChar());
        }
        return sb.toString();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity(); // finish activity
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
