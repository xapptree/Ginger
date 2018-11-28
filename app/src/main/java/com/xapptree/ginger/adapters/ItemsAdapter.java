package com.xapptree.ginger.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xapptree.ginger.GlideApp;
import com.xapptree.ginger.R;
import com.xapptree.ginger.model.CLineItem;
import com.xapptree.ginger.model.Items;
import com.xapptree.ginger.model.LineItemRealm;
import com.xapptree.ginger.utilities.AppConstants;
import com.xapptree.ginger.utilities.RealmController;

import java.util.Vector;

/**
 * Created by Akbar on 1/9/2017.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    private Context mContext;
    private Vector<Items> arrCCategoryDetails = null;
    private boolean IsRecommended;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, mprice, tminus, tplus, tquantity;
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

    public ItemsAdapter(Context mContext, Vector<Items> arrCCategoryDetails, boolean IsRecommended) {
        this.mContext = mContext;
        this.arrCCategoryDetails = arrCCategoryDetails;
        this.IsRecommended = IsRecommended;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (IsRecommended) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recommended_listitem, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menuitem_listitem, parent, false);
        }
        AppConstants.overrideFonts(mContext, itemView.findViewById(R.id.rootlay), "Raleway-SemiBold.ttf");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Items obj = arrCCategoryDetails.get(position);
        if (RealmController.with((Activity) mContext).CartitemExists(obj.ItemId)) {
            LineItemRealm objcart = RealmController.with((Activity) mContext).getCartitem(obj.ItemId);
            holder.tquantity.setText(String.valueOf(objcart.getItemQuantity()));
        }

        holder.title.setText(obj.ItemName);
        holder.mprice.setText(AppConstants.uniText(obj.ItemPrice));

        if (obj.IsVeg) {
            holder.mIndicator.setImageResource(R.drawable.veg_indicator);
        } else {
            holder.mIndicator.setImageResource(R.drawable.non_vegindicator);
        }
        if (IsRecommended) {
            GlideApp.with(mContext)
                    .load(obj.ItemUrl)
                    .centerCrop()
                    .error(R.drawable.imagepreviewbig)
                    .into(holder.mlogo);
        }

        holder.tminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
//                toneGen1.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 150);
                LineItemRealm objcart = RealmController.with((Activity) mContext).getCartitem(obj.ItemId);
                if (Integer.parseInt(holder.tquantity.getText().toString()) == 0) {
                    holder.tquantity.setText("0");
                } else {

                    int totalQuan = Integer.parseInt(holder.tquantity.getText().toString()) - 1;
                    holder.tquantity.setText(String.valueOf(totalQuan));

                    if (totalQuan == 0) {
                        RealmController.with((Activity) mContext).removeCartItem(objcart.getId(), mContext);

                    } else {
                        double itemP = obj.ItemPrice * totalQuan;
                        double tTotal = AppConstants.getdouble(itemP);

                        CLineItem newItem = new CLineItem();
                        newItem.Item_Id = obj.ItemId;
                        newItem.Item_Name = obj.ItemName;
                        newItem.Item_price = obj.ItemPrice;
                        newItem.Item_Total = tTotal;
                        newItem.Item_Quantity = totalQuan;
                        newItem.IsDiscount = obj.IsDiscount;
                        newItem.IsVeg = obj.IsVeg;
                        newItem.DiscountPercent = obj.DiscountPercent;

                        if (obj.IsDiscount) {
                            int mDisper = obj.DiscountPercent;
                            double mdis = tTotal * mDisper / 100;
                            newItem.Extended_Total = tTotal - mdis;
                        } else {
                            newItem.Extended_Total = tTotal;
                        }


                        RealmController.with((Activity) mContext).updateCartItemToRealm(newItem, objcart.getId(), mContext);
                    }
                }
            }
        });

        holder.tplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalQuan = Integer.parseInt(holder.tquantity.getText().toString()) + 1;
                holder.tquantity.setText(String.valueOf(totalQuan));
                double itemP = obj.ItemPrice * totalQuan;
                double tTotal = AppConstants.getdouble(itemP);

                LineItemRealm mCartiteme = RealmController.with((Activity) mContext).getCartitem(obj.ItemId);
                if (mCartiteme != null) {

                    CLineItem newItem = new CLineItem();
                    newItem.Item_Id = obj.ItemId;
                    newItem.Item_Name = obj.ItemName;
                    newItem.Item_price = obj.ItemPrice;
                    newItem.Item_Total = tTotal;
                    newItem.Item_Quantity = totalQuan;
                    newItem.IsDiscount = obj.IsDiscount;
                    newItem.IsVeg = obj.IsVeg;
                    newItem.DiscountPercent = obj.DiscountPercent;

                    if (obj.IsDiscount) {
                        int mDisper = obj.DiscountPercent;
                        double mdis = tTotal * mDisper / 100;
                        newItem.Extended_Total = tTotal - mdis;
                    } else {
                        newItem.Extended_Total = tTotal;
                    }

                    RealmController.with((Activity) mContext).updateCartItemToRealm(newItem, mCartiteme.getId(), mContext);
                } else {

                    CLineItem newItem = new CLineItem();
                    newItem.Item_Id = obj.ItemId;
                    newItem.Item_Name = obj.ItemName;
                    newItem.Item_price = obj.ItemPrice;
                    newItem.Item_Total = tTotal;
                    newItem.Item_Quantity = totalQuan;
                    newItem.IsDiscount = obj.IsDiscount;
                    newItem.IsVeg = obj.IsVeg;
                    newItem.DiscountPercent = obj.DiscountPercent;

                    if (obj.IsDiscount) {
                        int mDisper = obj.DiscountPercent;
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
