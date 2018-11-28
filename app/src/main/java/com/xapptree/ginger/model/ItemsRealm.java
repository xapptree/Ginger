package com.xapptree.ginger.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Akbar on 1/23/2018.
 */

public class ItemsRealm extends RealmObject {
    @PrimaryKey
    private String ItemId;
    private String ItemName;
    private String ItemUrl;
    private double ItemPrice;
    private String CategoryId;
    private String StoreId;
    private boolean Availability;
    private boolean IsVeg;
    private boolean IsDiscount;
    public boolean IsRecommended;
    public int DiscountPercent;

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

    public String getItemUrl() {
        return ItemUrl;
    }

    public void setItemUrl(String itemUrl) {
        ItemUrl = itemUrl;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public boolean isAvailability() {
        return Availability;
    }

    public void setAvailability(boolean availability) {
        Availability = availability;
    }

    public boolean isVeg() {
        return IsVeg;
    }

    public void setVeg(boolean veg) {
        IsVeg = veg;
    }

    public boolean isDiscount() {
        return IsDiscount;
    }

    public void setDiscount(boolean discount) {
        IsDiscount = discount;
    }

    public boolean isRecommended() {
        return IsRecommended;
    }

    public void setRecommended(boolean recommended) {
        IsRecommended = recommended;
    }

    public int getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        DiscountPercent = discountPercent;
    }

}
