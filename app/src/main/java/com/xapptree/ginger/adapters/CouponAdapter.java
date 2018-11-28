package com.xapptree.ginger.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xapptree.ginger.R;
import com.xapptree.ginger.model.Coupons;
import com.xapptree.ginger.utilities.AppConstants;

import java.util.Vector;

/**
 * Created by Akbar on 1/30/2018.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {
    private Vector<Coupons> arrCoupons = null;
    private Context mContext;
    private Typeface customFont;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tValue, tDesc;

        public MyViewHolder(View view) {
            super(view);
            tValue = view.findViewById(R.id.couponvalue);
            tDesc = view.findViewById(R.id.coupondesc);

        }
    }

    public CouponAdapter(Context mContext, Vector<Coupons> arrCoupons) {
        this.arrCoupons = arrCoupons;
        this.mContext = mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_listitem, parent, false);
        AppConstants.overrideFonts(mContext, itemView.findViewById(R.id.rootlay), "Raleway-Regular.ttf");
        AssetManager am = mContext.getAssets();
        customFont = Typeface.createFromAsset(am, "Raleway-Bold.ttf");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Coupons obj = arrCoupons.get(position);

        holder.tValue.setText("\u20B9" + String.valueOf(obj.Value));
        holder.tDesc.setText(obj.Description);
        holder.tValue.setTypeface(customFont);
    }

    @Override
    public int getItemCount() {
        return this.arrCoupons.size();
    }

}

