package com.xapptree.ginger.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xapptree.ginger.GlideApp;
import com.xapptree.ginger.R;
import com.xapptree.ginger.model.OnlineOrders;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Akbar on 8/27/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<OnlineOrders> arrCLineItems = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tStoreName, tOrderId, tDate, tTotal, tOrder_status;
        private ImageView iStoreImage;


        public MyViewHolder(View view) {
            super(view);
            tStoreName = view.findViewById(R.id.restro_name);
            tOrderId = view.findViewById(R.id.orderid);
            tDate = view.findViewById(R.id.orderdate);
            tTotal = view.findViewById(R.id.orderamount);
            tOrder_status = view.findViewById(R.id.orderstatus);
            iStoreImage = view.findViewById(R.id.logo);
        }
    }


    public OrderAdapter(Context mContext, List<OnlineOrders> arrCLineItems) {
        this.mContext = mContext;
        this.arrCLineItems = arrCLineItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        OnlineOrders cLineItem = this.arrCLineItems.get(position);

        GlideApp.with(mContext)
                .load(cLineItem.StoreBannerUrl)
                .centerCrop()
                .error(R.drawable.imagepreviewbig)
                .into(holder.iStoreImage);

        holder.tOrderId.setText("Order Id : #" + cLineItem.Order_Id);
        holder.tOrder_status.setText(cLineItem.Order_Status);
        holder.tDate.setText(cLineItem.Order_Time);
        holder.tStoreName.setText(cLineItem.StoreName);
        holder.tTotal.setTextColor(Color.parseColor("#4ca87e"));
        holder.tTotal.setText(" \u20B9 " + String.format("%.2f", new BigDecimal(cLineItem.ExtendedTotal)));

    }

    @Override
    public int getItemCount() {
        return arrCLineItems.size();
    }


}