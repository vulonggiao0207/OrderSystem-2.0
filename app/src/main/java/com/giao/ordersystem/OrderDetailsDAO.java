package com.giao.ordersystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 5/19/2016.
 */
public class OrderDetailsDAO {
    public static final String KEY_OrderDetailID="OrderDetailID";
    public static final String OrderDetail_OrderID="OrderID";
    public static final String OrderDetail_DishID="DishID";
    public static final String OrderDetails_Quantity="Quantity";
    public static final String Price="Price";
    public static final String Note="Note";
    private static final String DATABASE_TABLE="OrderDetail";
    private static DatabaseHelper databaseHelper;
    //
    public final Context context;
    private SQLiteDatabase database;
    public OrderDetailsDAO(Context context) {
        this.context=context;
        databaseHelper= new DatabaseHelper(context);
        // TODO Auto-generated constructor stub
    }
    public OrderDetailsDAO open() throws SQLException
    {
        database=databaseHelper.getReadableDatabase();
        return this;
    }
    public void close() throws SQLException
    {
        databaseHelper.close();
    }
    public long create(int orderID, int dishID, int quantity, Float price, String note) throws SQLException
    {
        ContentValues cv= new ContentValues();
        cv.put(OrderDetail_OrderID, orderID);
        cv.put(OrderDetail_DishID, dishID);
        cv.put(OrderDetails_Quantity, quantity);
        cv.put(Price, price);
        cv.put(Note, note);
        return database.insert(DATABASE_TABLE,null ,cv);
    }
    public ArrayList<OrderDetailsBO> list()
    {
        String query="SELECT * FROM OrderDetail";
        Cursor cur=database.rawQuery(query,null);
        ArrayList<OrderDetailsBO> list = new ArrayList<OrderDetailsBO>();
        int iRow= cur.getColumnIndex(KEY_OrderDetailID);
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()) {
            int _OrderDetailID=Integer.parseInt(cur.getString(0).toString());
            int _OrderID=Integer.parseInt(cur.getString(1).toString());
            int _DishID=Integer.parseInt(cur.getString(2).toString());
            int _Quantity=Integer.parseInt(cur.getString(3).toString());
            Float _Price=Float.parseFloat(cur.getString(4).toString());
            String _Note=cur.getString(5).toString();
            OrderDetailsBO record = new OrderDetailsBO(_OrderDetailID, _OrderID,_DishID,_Quantity,_Price,_Note);
            list.add(record);        }
        cur.close();
        return list;
    }
    public ArrayList<Order_View> list_orderdetails(String tableName)
    {
        String query="SELECT orderDetailID,menu.dishID,quantity, dishname, round(quantity*price,2) subtotal,orderdetail.note";
        query+=" FROM orderdetail,menu,tables,orders";
        query+=" WHERE orderdetail.dishid=menu.dishid";
        query+=" AND orders.tablename=tables.tablename";
        query+=" AND orders.orderid=orderdetail.orderid";
        query+=" AND orders.tablename='"+tableName+"'";
        query+=" GROUP BY orderDetailID,menu.dishID,quantity,dishname,subtotal,note";
        query+=" HAVING orderpaid<(SELECT ROUND(SUM(quantity*price),2) FROM orderdetail,menu,tables,orders";
        query+=" WHERE orderdetail.dishid=menu.dishid";
        query+=" AND orders.tablename=tables.tablename";
        query+=" AND orders.orderid=orderdetail.orderid";
        query+=" AND orders.tablename='"+tableName+"')";
        Cursor cur=database.rawQuery(query,null);
        ArrayList<Order_View> list = new ArrayList<Order_View>();
        int iRow= cur.getColumnIndex(KEY_OrderDetailID);
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()) {
            int orderDetailID=Integer.parseInt(cur.getString(0).toString());
            int dishID=Integer.parseInt(cur.getString(1).toString());
            int quantity=Integer.parseInt(cur.getString(2).toString());
            String dishname=cur.getString(3);
            Float subtotal=Float.parseFloat(cur.getString(4));
            String note=cur.getString(5);
            Order_View record = new Order_View(orderDetailID,dishID,quantity,dishname,subtotal,note);
            list.add(record);
            }
        cur.close();
        return list;
    }
    public boolean remove(int orderDetailID)
    {
        return database.delete(DATABASE_TABLE, KEY_OrderDetailID + "=" + orderDetailID, null) > 0;
    }
    public boolean removeAll()
    {
        return database.delete(DATABASE_TABLE, null, null) > 0;
    }
    public int getOrderIDbyDetailsID(int orderDetailID)
    {
        try {
            int orderID;
            String query = "SELECT orderID FROM orderDetails WHERE orderDetailID= "+orderDetailID;
            Cursor cur = database.rawQuery(query, null);
            List<DishBO> list = new ArrayList<DishBO>();
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                orderID=Integer.parseInt(cur.getString(0).toString());
                cur.close();
                break;
            }
            cur.close();
            return 0;
        }
        catch(Exception e)
        {
            return 0;
        }
    }
}
