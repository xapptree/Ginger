package com.xapptree.ginger.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Akbar on 8/21/2017.
 */

public class LineItemRealm extends RealmObject {
    @PrimaryKey
    private int Id;
    private String ItemId;
    private String ItemName;
    private double ItemPrice;
    private int ItemQuantity;
    public double ItemTotal;
    public boolean IsDiscount;
    public boolean IsVeg;
    public int DiscountPercent;
    public double Extended_Total;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    public double getItemTotal() {
        return ItemTotal;
    }

    public void setItemTotal(double itemTotal) {
        ItemTotal = itemTotal;
    }

    public boolean isDiscount() {
        return IsDiscount;
    }

    public void setDiscount(boolean discount) {
        IsDiscount = discount;
    }

    public int getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        DiscountPercent = discountPercent;
    }

    public double getExtended_Total() {
        return Extended_Total;
    }

    public void setExtended_Total(double extended_Total) {
        Extended_Total = extended_Total;
    }

    public boolean isVeg() {
        return IsVeg;
    }

    public void setVeg(boolean veg) {
        IsVeg = veg;
    }

}
