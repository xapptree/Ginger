package com.xapptree.ginger.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xapptree.ginger.R;
import com.xapptree.ginger.model.CLineItem;
import com.xapptree.ginger.model.LineItemRealm;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RealmController;

import io.realm.RealmResults;

/**
 * Created by Akbar on 9/13/2017.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {
    private Context mContext;
    private RealmResults<LineItemRealm> arrCCategoryDetails = null;
    Typeface customFont;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, mprice, tminus, tplus, tquantity;
        ImageView mlogo, mIndicator;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            mprice = view.findViewById(R.id.price);
            tminus = view.findViewById(R.id.minus);
            tplus = view.findViewById(R.id.plus);
            tquantity = view.findViewById(R.id.quantity);
            mlogo = view.findViewById(R.id.logo);
            mIndicator = view.findViewById(R.id.typeindicator);
        }
    }

    public OrderItemAdapter(Context mContext, RealmResults<LineItemRealm> arrCCategoryDetails) {
        this.mContext = mContext;
        this.arrCCategoryDetails = arrCCategoryDetails;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menuitem_listitem, parent, false);

        AppConstants.overrideFonts(mContext, itemView.findViewById(R.id.rootlay), "Raleway-SemiBold.ttf");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final LineItemRealm obj = this.arrCCategoryDetails.get(position);
        Log.i(" line item", obj.toString());
        holder.title.setText(obj.getItemName());
        holder.mprice.setText(AppConstants.uniText(obj.getItemPrice()));
        holder.tquantity.setText(String.valueOf(obj.getItemQuantity()));
        if (obj.isVeg()) {
            holder.mIndicator.setImageResource(R.drawable.veg_indicator);
        } else {
            holder.mIndicator.setImageResource(R.drawable.non_vegindicator);
        }

        holder.tminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
//                toneGen1.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 150);
                if (Integer.parseInt(holder.tquantity.getText().toString()) == 0) {
                    holder.tquantity.setText("0");
                } else {

                    int totalQuan = Integer.parseInt(holder.tquantity.getText().toString()) - 1;
                    holder.tquantity.setText(String.valueOf(totalQuan));

                    if (totalQuan == 0) {
                        RealmController.with((Activity) mContext).removeCartItem(obj.getId(), mContext);

                    } else {
                        double itemP = obj.getItemPrice() * totalQuan;
                        double tTotal = AppConstants.getdouble(itemP);

                        CLineItem newItem = new CLineItem();
                        newItem.Item_Id = obj.getItemId();
                        newItem.Item_Name = obj.getItemName();
                        newItem.Item_price = obj.getItemPrice();
                        newItem.Item_Total = tTotal;
                        newItem.Item_Quantity = totalQuan;
                        newItem.IsDiscount = obj.isDiscount();
                        newItem.IsVeg = obj.isVeg();
                        newItem.DiscountPercent = obj.getDiscountPercent();

                        if (obj.IsDiscount) {
                            int mDisper = obj.getDiscountPercent();
                            double mdis = tTotal * mDisper / 100;
                            newItem.Extended_Total = tTotal - mdis;
                        } else {
                            newItem.Extended_Total = tTotal;
                        }


                        RealmController.with((Activity) mContext).updateCartItemToRealm(newItem, obj.getId(), mContext);
                    }
                }
            }
        });

        holder.tplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalQuan = Integer.parseInt(holder.tquantity.getText().toString()) + 1;
                holder.tquantity.setText(String.valueOf(totalQuan));
                double itemP = obj.getItemPrice() * totalQuan;
                double tTotal = AppConstants.getdouble(itemP);

                LineItemRealm mCartiteme = RealmController.with((Activity) mContext).getCartitem(obj.getItemId());
                if (mCartiteme != null) {
                    CLineItem newItem = new CLineItem();
                    newItem.Item_Id = obj.getItemId();
                    newItem.Item_Name = obj.getItemName();
                    newItem.Item_price = obj.getItemPrice();
                    newItem.Item_Total = tTotal;
                    newItem.Item_Quantity = totalQuan;
                    newItem.IsDiscount = obj.isDiscount();
                    newItem.IsVeg = obj.isVeg();
                    newItem.DiscountPercent = obj.getDiscountPercent();

                    if (obj.IsDiscount) {
                        int mDisper = obj.getDiscountPercent();
                        double mdis = tTotal * mDisper / 100;
                        newItem.Extended_Total = tTotal - mdis;
                    } else {
                        newItem.Extended_Total = tTotal;
                    }

                    RealmController.with((Activity) mContext).updateCartItemToRealm(newItem, mCartiteme.getId(), mContext);
                } else {
                    CLineItem newItem = new CLineItem();
                    newItem.Item_Id = obj.getItemId();
                    newItem.Item_Name = obj.getItemName();
                    newItem.Item_price = obj.getItemPrice();
                    newItem.Item_Total = tTotal;
                    newItem.Item_Quantity = totalQuan;
                    newItem.IsDiscount = obj.isDiscount();
                    newItem.IsVeg = obj.isVeg();
                    newItem.DiscountPercent = obj.getDiscountPercent();

                    if (obj.IsDiscount) {
                        int mDisper = obj.getDiscountPercent();
                        double mdis = tTotal * mDisper / 100;
                        newItem.Extended_Total = tTotal - mdis;
                    } else {
                        newItem.Extended_Total = tTotal;
                    }

                    RealmController.with((Activity) mContext).addLineItemToRealm(newItem, mContext);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.arrCCategoryDetails.size();
    }

}
