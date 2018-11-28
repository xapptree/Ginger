package com.xapptree.ginger.utilities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.gson.JsonArray;
import com.xapptree.ginger.MenuScreen;
import com.xapptree.ginger.OrderItemScreen;
import com.xapptree.ginger.model.CLineItem;
import com.xapptree.ginger.model.Items;
import com.xapptree.ginger.model.ItemsRealm;
import com.xapptree.ginger.model.LineItemRealm;

import org.json.JSONArray;

import java.util.Vector;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by Akbar on 3/8/2017.
 */
public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public void saveStoreItems(final String arrListt) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(ItemsRealm.class, arrListt);
            }
        });
    }

    /*Save category in Realm*/
    public void addLineItemToRealm(final CLineItem newItem, final Context mContext) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LineItemRealm citem = new LineItemRealm();
                int id;

                Number productPrimaryKey = realm.where(LineItemRealm.class).max("Id");
                if (productPrimaryKey == null) {
                    id = 1;
                } else {
                    id = productPrimaryKey.intValue() + 1;
                }
                citem.setId(id);
                citem.setItemId(newItem.Item_Id);
                citem.setItemName(newItem.Item_Name);
                citem.setItemPrice(newItem.Item_price);
                citem.setItemQuantity(newItem.Item_Quantity);
                citem.setItemTotal(newItem.Item_Total);
                citem.setDiscount(newItem.IsDiscount);
                citem.setVeg(newItem.IsVeg);
                citem.setDiscountPercent(newItem.DiscountPercent);
                citem.setExtended_Total(newItem.Extended_Total);
                realm.insertOrUpdate(citem);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                AppConstants.arrLineItemRealm = RealmController.with((Activity) mContext).getCart().sort("Id", Sort.ASCENDING);
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(AppConstants.mBroadcastAction);
                mContext.sendBroadcast(broadcastIntent);
            }
        });

    }

    /*Update cart item in Realm*/
    public void updateCartItemToRealm(final CLineItem newItem, final int id, final Context mContext) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LineItemRealm citem = new LineItemRealm();

                citem.setId(id);
                citem.setItemId(newItem.Item_Id);
                citem.setItemName(newItem.Item_Name);
                citem.setItemPrice(newItem.Item_price);
                citem.setItemQuantity(newItem.Item_Quantity);
                citem.setItemTotal(newItem.Item_Total);
                citem.setDiscount(newItem.IsDiscount);
                citem.setVeg(newItem.IsVeg);
                citem.setDiscountPercent(newItem.DiscountPercent);
                citem.setExtended_Total(newItem.Extended_Total);
                realm.insertOrUpdate(citem);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                AppConstants.arrLineItemRealm = RealmController.with((Activity) mContext).getCart().sort("Id", Sort.ASCENDING);
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(AppConstants.mBroadcastAction);
                mContext.sendBroadcast(broadcastIntent);
            }
        });

    }


    public RealmResults<LineItemRealm> getCart() {

        return realm.where(LineItemRealm.class).findAll();
    }

    /*Get All cart subtotal Amount*/
    public double getCartSubtotal() {
        return realm.where(LineItemRealm.class).findAll().sum("ItemTotal").doubleValue();
    }

    public double getExtCartSubtotal() {
        return realm.where(LineItemRealm.class).findAll().sum("Extended_Total").doubleValue();
    }

    /*Get All cart subtotal Amount*/
    public double getCGST() {
        return realm.where(LineItemRealm.class).findAll().sum("ItemCGST").doubleValue();
    }

    public double getSGST() {
        return realm.where(LineItemRealm.class).findAll().sum("ItemSGST").doubleValue();
    }

    public int getItemsQuantity() {
        return realm.where(LineItemRealm.class).findAll().sum("ItemQuantity").intValue();
    }

    /*remove cart item*/
    public void deleteCart() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(LineItemRealm.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
               /* Intent i = new Intent(printScreen, RegisterScreen.class);
                printScreen.startActivity(i);*/
            }
        });
    }

    /*remove cart item*/
    public void deleteStoreItems() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ItemsRealm.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        });
    }

    /*remove cart item*/
    public void EmptyCart(final OrderItemScreen context) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(LineItemRealm.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Intent i = new Intent(context, MenuScreen.class);
                context.startActivity(i);
                context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                context.finish();
            }
        });
    }

    /*remove cart item*/
    public void removeCartItem(final int cartitemid, final Context mContext) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                LineItemRealm citem = realm.where(LineItemRealm.class).equalTo("Id", cartitemid).findFirst();
                citem.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                AppConstants.arrLineItemRealm = RealmController.with((Activity) mContext).getCart().sort("Id", Sort.ASCENDING);
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(AppConstants.mBroadcastAction);
                mContext.sendBroadcast(broadcastIntent);
            }
        });
    }

    //query a single item with the given id
    public LineItemRealm getCartitem(String ItemId) {

        return realm.where(LineItemRealm.class).equalTo("ItemId", ItemId).findFirst();
    }

    /*Cart Item Exists*/
    public boolean CartitemExists(String ItemId) {

        LineItemRealm mCart = realm.where(LineItemRealm.class).equalTo("ItemId", ItemId).findFirst();
        if (mCart != null) {
            // Exists
            return true;
        } else {
            // Not exist
            return false;
        }

    }

    public Vector<Items> getRecommended() {
        final Vector<Items> arritems = new Vector<>();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ItemsRealm> arrItemsR = realm.where(ItemsRealm.class).equalTo("IsRecommended", true).findAll();
                for (ItemsRealm oitem : arrItemsR) {
                    Items nItem = new Items();
                    nItem.ItemId = oitem.getItemId();
                    nItem.ItemName = oitem.getItemName();
                    nItem.ItemPrice = oitem.getItemPrice();
                    nItem.CategoryId = oitem.getCategoryId();
                    nItem.StoreId = oitem.getStoreId();
                    nItem.IsRecommended = oitem.isRecommended();
                    nItem.IsVeg = oitem.isVeg();
                    nItem.IsDiscount = oitem.isDiscount();
                    nItem.ItemUrl = oitem.getItemUrl();
                    nItem.Availability = oitem.isAvailability();
                    nItem.DiscountPercent = oitem.getDiscountPercent();
                    arritems.add(nItem);
                }
            }
        });
        return arritems;
    }

    public Vector<Items> getItemsByCategory(final String catid) {
        final Vector<Items> arritems = new Vector<>();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ItemsRealm> arrItemsR = realm.where(ItemsRealm.class).equalTo("CategoryId", catid).equalTo("Availability",true).findAll();
                for (ItemsRealm oitem : arrItemsR) {
                    Items nItem = new Items();
                    nItem.ItemId = oitem.getItemId();
                    nItem.ItemName = oitem.getItemName();
                    nItem.ItemPrice = oitem.getItemPrice();
                    nItem.CategoryId = oitem.getCategoryId();
                    nItem.StoreId = oitem.getStoreId();
                    nItem.IsRecommended = oitem.isRecommended();
                    nItem.IsVeg = oitem.isVeg();
                    nItem.IsDiscount = oitem.isDiscount();
                    nItem.ItemUrl = oitem.getItemUrl();
                    nItem.Availability = oitem.isAvailability();
                    nItem.DiscountPercent = oitem.getDiscountPercent();
                    arritems.add(nItem);
                }
            }
        });
        return arritems;
    }
}
