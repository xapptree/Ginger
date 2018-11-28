package com.xapptree.ginger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xapptree.ginger.R;
import com.xapptree.ginger.model.OrderItems;
import com.xapptree.ginger.utilities.AppConstants;

import java.util.Vector;

/**
 * Created by Akbar on 9/7/2017.
 */

public class OrderDetailItemsAdpater extends RecyclerView.Adapter<OrderDetailItemsAdpater.MyViewHolder> {

    private Vector<OrderItems> arrCLineItems = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tQuantity, tTotal, tDesc;

        public MyViewHolder(View view) {
            super(view);
            tQuantity = view.findViewById(R.id.quantity);
            tTotal = view.findViewById(R.id.itemtotal);
            tDesc = view.findViewById(R.id.item_desc);
        }
    }


    public OrderDetailItemsAdpater(Context mContext, Vector<OrderItems> arrCLineItems) {
        this.arrCLineItems = arrCLineItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lineitem_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        OrderItems cLineItem = this.arrCLineItems.get(position);
        holder.tDesc.setText(cLineItem.ItemName);
        holder.tQuantity.setText(String.valueOf(cLineItem.Item_Quantity));
        holder.tTotal.setText(AppConstants.uniText(cLineItem.Item_Total));

    }

    @Override
    public int getItemCount() {
        return arrCLineItems.size();
    }


}