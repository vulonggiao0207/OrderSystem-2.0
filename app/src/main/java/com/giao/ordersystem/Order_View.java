package com.giao.ordersystem;

/**
 * Created by Long on 5/21/2016.
 */
public class Order_View {
    private int orderDetailID;
    private int quantity;
    private int dishID;
    private String dishName;
    private Float subtotal;
    private String note;
    public Order_View(int orderDetailID,int dishID,int quantity,String dishName,Float subtotal,String note)
    {
        this.orderDetailID=orderDetailID;
        this.dishID=dishID;
        this.quantity=quantity;
        this.dishName=dishName;
        this.subtotal=subtotal;
        this.note=note;
    }
    public void setOrderDetailID(int orderDetailID){this.orderDetailID=orderDetailID;}
    public int getOrderDetailID(){return this.orderDetailID;}
    public void setDishID(int dishID){this.dishID=dishID;}
    public int getDishID(){return this.dishID;}
    public void setQuantity(int quantity){this.quantity=quantity;}
    public int getQuantity(){return this.quantity;}
    public void setdishName(String dishName){this.dishName=dishName;}
    public String getdishName(){return this.dishName;}
    public void setSubtotal(Float subtotal){this.subtotal=subtotal;}
    public Float getSubtotal(){return this.subtotal;}
    public void setNote(String note){this.note=note;}
    public String getNote(){return this.note;}
}
