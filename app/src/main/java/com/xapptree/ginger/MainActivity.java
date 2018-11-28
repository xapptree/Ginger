package com.xapptree.ginger;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;
import com.freshchat.consumer.sdk.ConversationOptions;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.moe.pushlibrary.MoEHelper;
import com.ryanharter.android.tooltips.ToolTip;
import com.ryanharter.android.tooltips.ToolTipLayout;
import com.xapptree.ginger.adapters.AddressAdapter;
import com.xapptree.ginger.interfaces.TooltipInterface;
import com.xapptree.ginger.model.CustomerAddress;
import com.xapptree.ginger.model.Stores;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RecyclerTouchListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class MainActivity extends GingerBaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, TooltipInterface {
    private CollectionReference storeRef;
    private CollectionReference addressRef;

    private static final int REQUEST_WRITE_STORAGE = 112;
    private static final String TAG = "GoogleActivity";
    private ImageButton homeLoc;
    private CoordinatorLayout coordinatorLayout;
    private String cUID = "";
    private RecyclerView recyclerView;
    private boolean pickerLoc = false;
    private Boolean exit = false;

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    final static int REQUEST_LOCATION = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

          /*Firechat Init*/
        FreshchatConfig freshchatConfig = new FreshchatConfig("4c11d8d4-2e01-4633-af33-4f8524253232", "8ca12c45-5854-46ce-a2fe-1082fe3f3e5c");
        Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);

          /*Widget ref*/
        TextView mH1 = findViewById(R.id.appname1);
        TextView mH2 = findViewById(R.id.appname2);
        TextView mH3 = findViewById(R.id.appname3);

         /*FireStore Database ref*/
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        storeRef = mDatabase.collection("Stores");
        addressRef = mDatabase.collection("CustomerAddress");

           /*Customer UID from sp*/
        SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);
        cUID = sp.getString("Uid", "");

        /*Setting Fonts*/
        AppConstants.overrideFonts(getApplicationContext(), findViewById(R.id.coordinatorLayout), "Raleway-Regular.ttf");

        final AssetManager am = this.getApplicationContext().getAssets();
        Typeface customFont2 = Typeface.createFromAsset(am, "FEASFBRG.TTF");
        mH1.setTypeface(customFont2);
        mH2.setTypeface(customFont2);
        mH3.setTypeface(customFont2);

        //Bottom Navigation
        SpaceNavigationView spaceNavigationView = findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.restro_96));
        spaceNavigationView.addSpaceItem(new SpaceItem("Orders", R.drawable.order100));
        spaceNavigationView.addSpaceItem(new SpaceItem("Offers", R.drawable.offer100));
        spaceNavigationView.addSpaceItem(new SpaceItem("Account", R.drawable.user100));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setCentreButtonRippleColor(ContextCompat.getColor(this, R.color.colorBluegray));
        spaceNavigationView.changeCurrentItem(0);

        /*Widget ref*/
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        recyclerView = findViewById(R.id.addres_recycler);
        TextView tLogout = findViewById(R.id.logout);
        homeLoc = findViewById(R.id.pickloc);

        /*RecyclerView options*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.START);
        snapHelperTop.attachToRecyclerView(recyclerView);

        /*Onclick listeners*/
        homeLoc.setOnClickListener(this);
        tLogout.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //Firebase Auth start
        mAuth = FirebaseAuth.getInstance();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CustomerAddress objaddress = AppConstants.arrAddress.get(position);
                // LatLng latLngc = new LatLng(Double.valueOf(objaddress.Latitude), Double.valueOf(objaddress.Longitude));
                /*Check Distance of address*/
                checkAddressDistance(objaddress.AddressLoc, Double.valueOf(objaddress.Latitude), Double.valueOf(objaddress.Longitude));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                SharedPreferences sp = getSharedPreferences("UserCreds", MODE_PRIVATE);

                //Get the user object for the current installation
                FreshchatUser freshUser = Freshchat.getInstance(getApplicationContext()).getUser();
                freshUser.setFirstName(sp.getString("UserName", ""));
                freshUser.setEmail(sp.getString("Email", ""));
                freshUser.setPhone("+91", sp.getString("Phone", ""));
                //Call setUser so that the user information is synced with Freshchat's servers
                Freshchat.getInstance(getApplicationContext()).setUser(freshUser);
                List<String> tags = new ArrayList<>();
                tags.add("Conversation");

                ConversationOptions convOptions = new ConversationOptions()
                        .filterByTags(tags, "Conversations");

                Freshchat.showConversations(MainActivity.this, convOptions);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 1:
                        Intent i = new Intent(MainActivity.this, MyOrders.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                        break;
                    case 2:
                        Intent o = new Intent(MainActivity.this, OfferScreen.class);
                        o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(o);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                        break;
                    case 3:
                        Intent p = new Intent(MainActivity.this, ProfileScreen.class);
                        p.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(p);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });

        /*Check percmissions*/
        onCheckPermissionStart();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!AppConstants.MapTooltip) {
            AppConstants.MapTooltip = true;
            showTooltip("Delivery Location", "Pick location from map, based on we show restaurents near you!", homeLoc);
        }
        getAllAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onCheckPermissionStart() {
        /*Below code is for Permission requesting in run time on above Marshmelow devices*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] PERMISSIONS = {android.Manifest.permission.VIBRATE, android.Manifest.permission.ACCESS_FINE_LOCATION};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, REQUEST_WRITE_STORAGE);
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    //reload my activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(MainActivity.this, "The app was not allowed to start up, please Re-start App. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    // [START on activity result]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (resultCode == Activity.RESULT_OK && requestCode == 300) {

            String selectedAddress = data.getExtras().getString("Address");
            Double selectedLat = data.getExtras().getDouble("Latitude");
            Double selectedLon = data.getExtras().getDouble("Longitude");
            pickerLoc = true;
            //Check Distance
            checkAddressDistance(selectedAddress, selectedLat, selectedLon);

        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_LOCATION) {
        } else {
            // Toast.makeText(this, "Error getting results", Toast.LENGTH_LONG).show();
        }
    }

    private void signOut() {
        //Logout user
        MoEHelper.getInstance(getApplicationContext()).logoutUser();
        AppConstants.arrAddress = new Vector<>();
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //Update Ui
                        Intent i = new Intent(MainActivity.this, LoginScreen.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        finish();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        //   result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        MainActivity.this,
                                        REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.logout) {
            signOut();
        } else if (i == R.id.pickloc) {
            launchPickerFragment();
        }
    }

    private void launchPickerFragment() {
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(i, 300);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    private void getAllAddress() {
        Query query = addressRef.whereEqualTo("CustomerUid", cUID);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                AppConstants.arrAddress = new Vector<>();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        CustomerAddress mCat = document.toObject(CustomerAddress.class);
                        AppConstants.arrAddress.add(mCat);
                    }
                    bindAddressData();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    showSnacbarMessage("Oops  server error.\n");
                }
            }
        });
    }

    private void checkAddressDistance(final String placeAddress, final Double lat, final Double lng) {
        showProgress("Please wait..");
        // Get City Name from Address
        String mcity = "";
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            mcity = addresses.get(0).getLocality();
        }
        // End
        Log.i("City", mcity);

        Query query = storeRef.whereEqualTo("City", mcity).whereEqualTo("IsActive", true);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AppConstants.arrStores = new Vector<>();
                    if (task.getResult() != null) {
                        //user exists, do something
                        for (DocumentSnapshot categorySnapshot : task.getResult()) {
                            Stores mCat = categorySnapshot.toObject(Stores.class);
                            if (mCat.IsActive) {
                                AppConstants.arrStores.add(mCat);
                            }
                        }
                        if (AppConstants.arrStores.size() > 0) {
                            validateDistance(placeAddress, lat, lng);
                        } else {
                            hideProgress();
                            // No stores available
                            pickerLoc = false;
                            showSnacbarMessage("Uhh No.. stores are closed.\n");
                        }

                    } else {
                        hideProgress();
                        // No stores available
                        pickerLoc = false;
                        showSnacbarMessage("Oops  stores unavailable.\n");
                    }
                } else {
                    hideProgress();
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    pickerLoc = false;
                    showSnacbarMessage("Oops  server error.\n");
                }
            }
        });
    }

    private void validateDistance(String placeAddress, Double Lat, Double Lng) {
        Vector<Stores> attStores = new Vector<Stores>();
        for (int i = 0; i < AppConstants.arrStores.size(); i++) {
            Stores mstrore = AppConstants.arrStores.get(i);
            double lat = Double.parseDouble(mstrore.StoreLat);
            double lan = Double.parseDouble(mstrore.StoreLong);
            double km = distance(lat, lan, Lat, Lng);
            if (km < 5) {
                attStores.add(mstrore);
            }
        }
        if (attStores.size() > 0) {
            hideProgress();
            AppConstants.arrStores = new Vector<>();
            AppConstants.arrStores = attStores;
            AppConstants.OrderAddress = placeAddress;
            AppConstants.latitude = String.valueOf(Lat);
            AppConstants.longitude = String.valueOf(Lng);
            if (pickerLoc == true) {
                LatLng latLng = new LatLng(Lat, Lng);
                saveAddressPopup(placeAddress, latLng);
            } else {
                Intent i = new Intent(MainActivity.this, RestaurentScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }

        } else {
            hideProgress();
            pickerLoc = false;
            showSnacbarMessage("Delivery address is too far, unable to fetch restaurents.\n");
        }
    }

    private void saveAddressPopup(String placeAddress, final LatLng latLng) {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.mydialogstyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.saveaddress);
        dialog.setCancelable(true);
        Button mcancel = dialog.findViewById(R.id.dialog_skip);
        Button msave = dialog.findViewById(R.id.dialog_Done);
        final EditText addName = dialog.findViewById(R.id.addname);
        final EditText addDesc = dialog.findViewById(R.id.adddesc);

        addDesc.setText(placeAddress);

        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(MainActivity.this, RestaurentScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();

            }
        });
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstants.isValidString(addName.getText().toString())) {
                    CustomerAddress cadd = new CustomerAddress();
                    cadd.AddressName = addName.getText().toString();
                    cadd.AddressLoc = addDesc.getText().toString();
                    cadd.Latitude = String.valueOf(latLng.latitude);
                    cadd.Longitude = String.valueOf(latLng.longitude);
                    cadd.CustomerUid = cUID;
                    addressRef.add(cadd);

                    dialog.dismiss();

                    Intent i = new Intent(MainActivity.this, RestaurentScreen.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                } else {
                    showSnacbarMessage("Fields not to be empty.");
                }

            }
        });
        dialog.show();
    }

    private void bindAddressData() {
        AddressAdapter addressAdapter = new AddressAdapter(MainActivity.this, AppConstants.arrAddress);
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
    }

    private void showSnacbarMessage(String messages) {
        final Snackbar snackbar = Snackbar
                .make(coordinatorLayout, messages, Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.show();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return (dist * 1.609344);

    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void doBounceAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(homeLoc, "translationY", 0, -180, 0);
        animator.setInterpolator(new EasingInterpolator(Ease.BOUNCE_IN_OUT));
        animator.setStartDelay(200);
        animator.setDuration(2200);
        animator.setRepeatCount(20);
        animator.start();
    }

    @Override
    public void onConnectivityChanged(final boolean isConnected) {
        super.onConnectivityChanged(isConnected);

        if (!isConnected) {
            showNetworkPopUp();
        } else {
            hideNetorkPopUp();
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (exit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity(); // finish activity
                } else {
                    finish();
                }
            } else {
                showSnacbarMessage("Press Back again to Exit.\n");

                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
