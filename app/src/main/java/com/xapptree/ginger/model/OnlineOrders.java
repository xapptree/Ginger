package com.xapptree.ginger.model;

/**
 * Created by Akbar on 9/15/2017.
 */

public class OnlineOrders {
    public String Order_Id;
    public String Order_Date;
    public String Order_Time;

    public String PrefferedStore;
    public String StoreName;
    public String StoreBannerUrl;

    public String CustomerUid;
    public String CustomerName;
    public String CustomerMobile;
    public String CustomerEmail;

    public String DeliveryAddress;
    public String Latitude;
    public String Longitude;

    public String Order_SubTotal;
    public String DiscountAmount;
    public String Extended_Subtotal;
    public String Order_GST;
    public String Order_Total;// subtotal-discountamout+Gst
    public String ExtendedTotal;// total-coupon discount value

    public String CouponCode;
    public String CouponDiscountAmount;

    public String OrderPaid_Amount;
    public String OrderPaymentState;//Paid,PartialPaid,Not Paid
    public String Order_PaymentMode; // CASH,ONLINEPAYMENT
    public String Payment_ReferId;

    public String Order_Status;
    public String Order_StatusCode;
    public String Order_Type; //Pickup or HomeDelivery

    public String Order_DeliveryCharge;
    public String Order_ProcessingCharge;
    public String Order_ItemsQuantity;
    public String Order_Instructions;
    public String Order_Modified;
    public String Order_RefundAmount;

    public String AgentId;
    public String AgentName;

    public String Order_SGST;
    public String Order_CGST;

    public OnlineOrders() {
    }
}
