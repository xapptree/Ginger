package com.xapptree.ginger.utilities;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.xapptree.ginger.R;

/**
 * Created by Akbar on 4/25/2017.
 */
public class MyProgressLoader {
    Context mContext;
    AppCompatDialog pDialog;

    public MyProgressLoader(Context context) {
        this.mContext = context;

    }

    public void show() {
        pDialog = new AppCompatDialog(mContext);
        pDialog.setContentView(R.layout.progress);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void dismiss() {
        pDialog.hide();
    }
}
