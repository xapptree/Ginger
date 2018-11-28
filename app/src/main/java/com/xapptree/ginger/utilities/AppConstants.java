package com.xapptree.ginger.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xapptree.ginger.model.Categories;
import com.xapptree.ginger.model.Coupons;
import com.xapptree.ginger.model.CustomerAddress;
import com.xapptree.ginger.model.Items;
import com.xapptree.ginger.model.LineItemRealm;
import com.xapptree.ginger.model.OnlineOrders;
import com.xapptree.ginger.model.OrderItems;
import com.xapptree.ginger.model.Stores;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import io.realm.RealmResults;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Akbar on 10/12/2017.
 */

public class AppConstants {
    public static Vector<CustomerAddress> arrAddress = new Vector<CustomerAddress>();
    public static Vector<Categories> arrCategories = new Vector<Categories>();
    public static Vector<Items> arrItems = new Vector<Items>();
    public static Vector<Items> arrStoreItems = new Vector<Items>();
    public static Vector<Coupons> arrCoupons = new Vector<Coupons>();
    public static RealmResults<LineItemRealm> arrLineItemRealm;
    public static OnlineOrders cOrder = new OnlineOrders();
    public static Vector<OrderItems> arrOnlineItems;
    public static Stores CurStore = new Stores();
    public static Vector<Stores> arrStores;
    public static String OrderAddress = "";
    public static String latitude = "";
    public static String longitude = "";
    public static final String mBroadcastAction = "com.xapptree.broadcast.cart";
    public static String StoreId = "0";
    public static String StoreName = "";
    public static String StoreBanner = "";
    public static boolean MapTooltip = false;
    public static boolean Cartooltip = false;

    public static String currentDate() {
        Calendar calander = Calendar.getInstance();
        String dates = "";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        dates = format.format(calander.getTime());
        Log.e("Print result date: ", dates);

        return dates;
    }

    public static String saleDate(String mdate) {

        Date date = null;
        String dates = "";
        DateFormat formatter = new SimpleDateFormat("M/d/yyyy");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = formatter.parse(mdate);
            dates = format.format(date);
            //  Log.e("Print result date: ", dates);

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return dates;
    }

    public static String toDate() {
        Calendar calander = Calendar.getInstance();
        String mdate = "";
        DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.US);
        mdate = formatter.format(calander.getTime());
        Log.e("TSdate", mdate);
        return mdate;
    }

    public static String timeStamp() {
        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();

        return ts;
    }

    public static double getdouble(double ttotal) {
        String s = String.format("%.2f", new BigDecimal(ttotal));
        return Double.valueOf(s);
    }

    public static boolean isValidString(String value) {
        if ((value.length() > 0) && (value.trim() != "") && (value != null)
                && (value != "null"))
            return true;
        else
            return false;
    }

    public static boolean isValidMobile(String value) {
        if ((value.length() > 9) && (value.trim() != "") && (value != null)
                && (value != "null"))
            return true;
        else
            return false;
    }

    public static void overrideFonts(Context context, final View v,
                                     String fontName) {
        if (v == null)
            return;
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child, fontName);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), fontName));

            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), fontName));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static boolean checkForNetworkConnection(Context mcontext) {
        ConnectivityManager connMgr = (ConnectivityManager) mcontext
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return string formatted as "0.00" required by the Instant Buy API.
     */
//    private static String toDollars(long micros) {
//        return new BigDecimal(micros).divide(MICROS)
//                .setScale(2, RoundingMode.HALF_EVEN).toString();
//    }
    public static String bigDecimalString(double dvalue) {
        BigDecimal bigValue = BigDecimal.ZERO;
        BigDecimal itemValue = new BigDecimal(dvalue);
        bigValue = bigValue.add(itemValue);
        return bigValue.setScale(2, RoundingMode.UNNECESSARY).toString();
    }

    public static String uniText(double decivalue) {

        return "₹" + bigDecimalString(decivalue);
    }

    public static String uniString(String decivalue) {

        return "₹" + decivalue;
    }
}
