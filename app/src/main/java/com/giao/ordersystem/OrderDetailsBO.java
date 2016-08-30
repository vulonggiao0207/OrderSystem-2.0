package com.giao.ordersystem;

/**
 * Created by Long on 2/13/2016.
 */
public class OrderDetailsBO {
    private int orderDetailID;
    private int orderID;
    private int dishID;
    private int quantity;
    private Float price;
    private String note;
    public OrderDetailsBO()
    {

    }
    public OrderDetailsBO(int orderDetailID, int orderID, int dishID,int quantity, Float price, String note)
    {
        this.orderDetailID=orderDetailID;
        this.orderID=orderID;
        this.dishID=dishID;
        this.quantity=quantity;
        this.price=price;
        this.note=note;
    }
    public int getOrderDetailID() {return this.orderDetailID; }
    public void setOrderDetailID(int orderDetailID) {this.orderDetailID = orderDetailID;}
    public int getOrderID() {return orderID;}
    public void setOrderID(int orderID) {this.orderID = orderID;}
    public int getDishID(){return this.dishID;}
    public void setDishID(int note){this.dishID=dishID;}
    public int getQuantity(){return this.quantity;}
    public void setQuantity(int quantity){this.quantity=quantity;}
    public Float getPrice(){return this.price;}
    public void setPrice(Float price){this.price=price;}
    public String getNote(){return this.note;}
    public void setNote(String note){this.note=note;}
}
