package com.example.myapplication.util;

public class OrderInfo {
    private String OrderID;
    private String Itemname;
    private String DeliveryID;
    private String CustomerID;
    private String order_status;

    public OrderInfo(String OrderID, String Itemname, String DeliveryID, String CustomerID, String order_status){
        this.OrderID = OrderID;
        this.Itemname = Itemname;
        this.DeliveryID = DeliveryID;
        this.CustomerID = CustomerID;
        this.order_status = order_status;
    }

    public String getOrderID(){
        return OrderID;
    }
    public void setOrderID(){
        OrderID = OrderID;
    }

    public String getitemname(){
        return Itemname;
    }
    public void setItemname(String itemname){
        itemname = itemname;
    }
    public String getDeliveryID(){

        return DeliveryID;
    }
    public void setDeliveryID(String DeliveryID){
        DeliveryID = DeliveryID;
    }
    public String getCustomerID(){
        return CustomerID;
    }
    public void setCustomerId(String CustomerID){

        CustomerID = CustomerID;
    }
    public String getOrder_status(){

        return order_status;
    }
    public void setOrder_status(String order_status){
        order_status = order_status;
    }

}
