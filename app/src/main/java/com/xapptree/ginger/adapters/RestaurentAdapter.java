package com.xapptree.ginger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xapptree.ginger.GlideApp;
import com.xapptree.ginger.R;
import com.xapptree.ginger.model.Stores;

import java.util.Vector;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by Akbar on 11/6/2017.
 */

public class RestaurentAdapter extends RecyclerView.Adapter<RestaurentAdapter.MyViewHolder> {

    private Context mContext;
    private Vector<Stores> arrStores = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tName, tSpecialize, tRating, tCloseTag;
        private ImageView roundedImageView;
        private ImageView tPricing;

        public MyViewHolder(View view) {
            super(view);
            tName = view.findViewById(R.id.restro_name);
            tSpecialize = view.findViewById(R.id.specialize);
            tRating = view.findViewById(R.id.rating);
            tCloseTag = view.findViewById(R.id.closetag);
            tPricing = view.findViewById(R.id.pricing);
            roundedImageView = view.findViewById(R.id.logo);
        }
    }


    public RestaurentAdapter(Context mContext, Vector<Stores> arrStores) {
        this.mContext = mContext;
        this.arrStores = arrStores;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_listitem, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Stores cLineItem = this.arrStores.get(position);
        holder.tName.setText(cLineItem.StoreName);
        holder.tSpecialize.setText(cLineItem.Specialize);
        holder.tRating.setText(cLineItem.StoreRating);

        switch (cLineItem.Pricing) {
            case 1:
                holder.tPricing.setImageResource(R.drawable.rupee_rating);
                break;
            case 2:
                holder.tPricing.setImageResource(R.drawable.rupee_rating2);
                break;
            case 3:
                holder.tPricing.setImageResource(R.drawable.rupee_rating3);
                break;
            case 4:
                holder.tPricing.setImageResource(R.drawable.rupee_rating4);
                break;
            default:
                break;
        }
        if (cLineItem.IsStoreOpen) {
            holder.tCloseTag.setVisibility(View.GONE);

            GlideApp.with(mContext)
                    .load(cLineItem.StoreBanner)
                    .centerCrop()
                    .error(R.drawable.imagepreviewbig)
                    .into(holder.roundedImageView);
        } else {
            holder.tCloseTag.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(cLineItem.StoreBanner)
                    .apply(bitmapTransform(new GrayscaleTransformation()))
                    .into(holder.roundedImageView);
        }

    }

    @Override
    public int getItemCount() {
        return arrStores.size();
    }


}