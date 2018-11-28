package com.xapptree.ginger;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;

import com.moe.pushlibrary.MoEHelper;
import com.xapptree.ginger.interfaces.ProgressLoaderInterface;
import com.xapptree.ginger.receivers.ConnectivityChangeReceiver;

public class GingerBaseActivity extends AppCompatActivity implements ConnectivityChangeReceiver.OnConnectivityChangedListener, ProgressLoaderInterface {
    private MoEHelper mHelper;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    public ProgressDialog mProgressDialog;
    public AppCompatDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = MoEHelper.getInstance(this);

        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityChangeReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.onStop(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper.onResume(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mHelper.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        // TODO handle connectivity change
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityChangeReceiver);
    }

    @Override
    public void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }

    @Override
    public void showNetworkPopUp() {
        if (pDialog == null) {
            pDialog = new AppCompatDialog(this);
            pDialog.setContentView(R.layout.progress);
            pDialog.setCancelable(false);
        }

        pDialog.show();
    }

    @Override
    public void hideNetorkPopUp() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
