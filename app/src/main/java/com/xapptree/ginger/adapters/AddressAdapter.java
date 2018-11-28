package com.xapptree.ginger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xapptree.ginger.GlideApp;
import com.xapptree.ginger.R;
import com.xapptree.ginger.model.CustomerAddress;

import java.util.Vector;

/**
 * Created by Akbar on 9/14/2017.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private Context mContext;
    private Vector<CustomerAddress> arrCustomerAddress = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tName, tDesc;

        public MyViewHolder(View view) {
            super(view);
            tName = view.findViewById(R.id.name);
            tDesc = view.findViewById(R.id.desc);

        }
    }

    public AddressAdapter(Context mContext, Vector<CustomerAddress> arrCustomerAddress) {
        this.mContext = mContext;
        this.arrCustomerAddress = arrCustomerAddress;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CustomerAddress obj = arrCustomerAddress.get(position);

        holder.tName.setText(obj.AddressName);
        holder.tDesc.setText(obj.AddressLoc);
    }

    @Override
    public int getItemCount() {
        return this.arrCustomerAddress.size();
    }

}

