package com.xapptree.ginger;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.moengage.push.PushManager;

/**
 * Created by Akbar on 9/20/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        PushManager.getInstance().refreshToken(getApplicationContext(), refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}