package com.xapptree.ginger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private TextView etSource;
    private Address address;
    private boolean mylocation = false;
    private double mLAt, mLON;
    private String strAddress = null;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageButton backicon = findViewById(R.id.back);
        Button bSearch = findViewById(R.id.searchbtn);
        etSource = findViewById(R.id.etEnterSouce);
        Button pickuup = findViewById(R.id.pick);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPlaceAutocompleteActivityIntent();
            }
        });
        pickuup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etSource.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Address", message);
                resultIntent.putExtra("City", address.getLocality());
                resultIntent.putExtra("Latitude", mLAt);
                resultIntent.putExtra("Longitude", mLON);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });
    }

    private void callPlaceAutocompleteActivityIntent() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, 12);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                // Showing the current location in Google Map
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18));

                getAddressFromLocation(place.getLatLng(), etSource);

            }
        }
    }

    /**
     * Manipulates the map once available.
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mapsettings();
        buildGoogleApiClient();
        mGoogleApiClient.connect();

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.custommap);
        googleMap.setMapStyle(style);

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
            }
        });

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition camPos = googleMap.getCameraPosition();
                LatLng latlong = camPos.target;
                mLAt = latlong.latitude;
                mLON = latlong.longitude;
                getAddressFromLocation(camPos.target, etSource);
            }
        });
    }


    private void mapsettings() {
        // TODO Auto-generated method stub
        // Enabling MyLocation Layer of Google Map
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.getUiSettings().setCompassEnabled(true);

        mMap.getUiSettings().setRotateGesturesEnabled(true);

        mMap.getUiSettings().setTiltGesturesEnabled(true);

        mMap.getUiSettings().setZoomControlsEnabled(false);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setIndoorEnabled(true);

        mMap.setBuildingsEnabled(true);

    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider callin
            return;
        }
        mMap.setMyLocationEnabled(true);

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    mMap.clear();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

                    getAddressFromLocation(latLng, etSource);
                }
            }
        });
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (!mylocation) {
            // Showing the current location in Google Map
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            mylocation = true;
        }
    }

    private void getAddressFromLocation(final LatLng latlng, final TextView et) {
        et.setText("Waiting for Address");
        et.setTextColor(Color.GRAY);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Geocoder gCoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> list = gCoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
                    if (list != null && list.size() > 0) {
                        address = list.get(0);

                        StringBuilder sb = new StringBuilder();
                        if (address.getAddressLine(0) != null) {
                            if (address.getMaxAddressLineIndex() > 0) {
                                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                                    sb.append(address.getAddressLine(i)).append("\n");
                                }
                                sb.append(",");
                                sb.append(address.getCountryName());
                            } else {
                                sb.append(address.getAddressLine(0));
                            }
                        }

                        strAddress = sb.toString();
                        strAddress = strAddress.replace(",null", "");
                        strAddress = strAddress.replace("null", "");
                        strAddress = strAddress.replace("Unnamed", "");

                    }

                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(strAddress)) {
                                et.setText(strAddress);
                                et.setTextColor(Color.BLACK);

                            } else {
                                et.setText("");
                            }
                        }
                    });

                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 212: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, resultIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
