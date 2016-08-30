package com.giao.ordersystem;

import java.util.ArrayList;

/**
 * Created by Long on 2/13/2016.
 */
public class OrderBO {
    private int orderID;
    private String tableName;
    private String orderDate;
    private int numberOfCustomer;
    private String orderNote;
    private Float orderPaid;
    private ArrayList<OrderDetailsBO> orderDetails= new ArrayList<OrderDetailsBO>();
    public OrderBO()
    {}
    public OrderBO(int orderID, String tableName,String orderDate,int numberOfCustomer, String orderNote,Float orderPaid){
        this.orderID=orderID;
        this.tableName=tableName;
        this.orderDate=orderDate;
        this.numberOfCustomer=numberOfCustomer;
        this.orderNote=orderNote;
        this.orderPaid=orderPaid;
    }
    public int getOrderID(){return this.orderID;}
    public void setOrderID(int orderID){this.orderID=orderID;}
    public String getTableName(){return this.tableName;}
    public void setTableName(String tableID){this.tableName=tableName;}
    public String getOrderDate (){return this.orderDate;}
    public void setOrderDate(String orderDate){this.orderDate=orderDate;}
    public int getNumberOfCustomer(){return this.numberOfCustomer;}
    public void setNumberOfCustomer(int numberOfCustomer){this.numberOfCustomer=numberOfCustomer;}
    public String getOrderNote(){return this.orderNote;}
    public void setOrderNote(String orderNote){this.orderNote=orderNote;}
    public Float getOrderPaid(){return this.orderPaid;}
    public void setOrderPaid(Float orderPaid){this.orderPaid=orderPaid;}
    public ArrayList<OrderDetailsBO> getOrderDetails(){return this.orderDetails;}
    public void setOrderDetails(ArrayList<OrderDetailsBO> orderDetails){this.getOrderDetails().addAll(orderDetails);}
}
