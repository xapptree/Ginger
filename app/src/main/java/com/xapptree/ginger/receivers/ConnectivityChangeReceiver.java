package com.xapptree.ginger.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Akbar on 1/3/2018.
 */

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    private OnConnectivityChangedListener listener;

    public ConnectivityChangeReceiver(OnConnectivityChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnected();
        listener.onConnectivityChanged(isConnected);
    }

    public interface OnConnectivityChangedListener {
        void onConnectivityChanged(boolean isConnected);
    }
}
