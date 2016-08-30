package com.giao.ordersystem;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Long on 2/13/2016.
 */
public class OrderDAO {
    public static final String KEY_ROWID="OrderID";
    private static final String TABLE_NAME = "TableName";
    private static final String ORDER_DATE = "OrderDate";
    private static final String NUMBER_OF_CUSTOMER = "NumberOfCustomer";
    private static final String ORDER_NOTE = "OrderNote";
    private static final String ORDER_PAID="OrderPaid";
    private static final String DATABASE_TABLE="Orders";
    private final Context context;
    private SQLiteDatabase database;
    private static DatabaseHelper databaseHelper;
    public OrderDAO(Context context) {
        this.context = context;
        databaseHelper= new DatabaseHelper(context);
    }
    public OrderDAO open () throws SQLException
    {
        database=databaseHelper.getReadableDatabase();
        return this;
    }
    public void close() throws SQLException
    {
        databaseHelper.close();
    }
    public long create(String tableName, String orderDate,  int numberOfcustomer, String orderNote,float orderPaid)
    {
        ContentValues cv= new ContentValues();
        //optimize input data
        if (tableName != null)
            cv.put(TABLE_NAME,tableName);
        if (orderDate != null)
            cv.put(ORDER_DATE, orderDate);
        if (Integer.toString(numberOfcustomer) != null)
            cv.put(NUMBER_OF_CUSTOMER,Integer.toString(numberOfcustomer));
        if (orderNote != null)
            cv.put(ORDER_NOTE, orderNote);
        if(Float.toString(orderPaid)!=null)
            cv.put(ORDER_NOTE,orderNote);
        //put data
        cv.put(TABLE_NAME, tableName);
        cv.put(ORDER_DATE,orderDate);
        cv.put(NUMBER_OF_CUSTOMER,numberOfcustomer);
        cv.put(ORDER_NOTE,orderNote);
        cv.put(ORDER_PAID, orderPaid);
        return database.insert(DATABASE_TABLE, null, cv);
    }
    public String checkTableAvailable(String tableName)
    {
        String query = "SELECT orderID FROM Orders WHERE tableName= '"+tableName+"'";
        query+=" AND (OrderID NOT IN(SELECT OrderID FROM OrderDetail)";
        query+=" OR orderpaid< (SELECT SUM(round(quantity*price,2)) FROM orderdetail,menu,tables,orders";
        query+=" WHERE orderdetail.dishid=menu.dishid";
        query+=" AND orders.tablename=tables.tablename";
        query+=" AND orders.orderid=orderdetail.orderid";
        query+=" AND orders.tablename='"+tableName+"')";
        query+=")";
        Cursor cur = database.rawQuery(query, null);
        String record="";
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            record = cur.getString(0);
            break;
        }
        cur.close();
        return record;
    }
    public boolean remove(int orderID) throws SQLException {
        return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + orderID, null) > 0;
    }
    public boolean removeOrderDetails(int orderID) throws SQLException
    {
        return database.delete("OrderDetail", "OrderID" + "=" + orderID, null) > 0;
    }

    public boolean removeAll() {
        return database.delete(DATABASE_TABLE, null, null) > 0;
    }

    public long update(String orderID,String tableName, String orderDate,  int numberOfcustomer, String orderNote,float orderPaid) throws SQLException
    {
        ContentValues cv = new ContentValues();
        if (tableName != null)
            cv.put(TABLE_NAME,tableName);
        if (orderDate != null)
            cv.put(ORDER_DATE, orderDate);
        if (Integer.toString(numberOfcustomer) != null)
            cv.put(NUMBER_OF_CUSTOMER,Integer.toString(numberOfcustomer));
        if (orderNote != null)
            cv.put(ORDER_NOTE, orderNote);
        if(Float.toString(orderPaid)!=null)
            cv.put(ORDER_NOTE,orderNote);
        return database.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String[]{orderID});
    }
    public OrderBO itemOrder(String orderID) throws SQLException {
        String query = "SELECT TableName,OrderDate,NumberofCustomer,OrderNote,OrderPaid FROM Orders WHERE orderID='" + orderID + "'";
        Cursor cur = database.rawQuery(query, null);
        List<OrderBO> list = new ArrayList<OrderBO>();
        OrderBO record = new OrderBO();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
           String tableName=cur.getString(0).toString();
            String orderDate=cur.getString(1).toString();
            int NoCustomer=Integer.parseInt(cur.getString(2).toString());
            String orderNote=cur.getString(3).toString();
            Float orderPaid=Float.parseFloat(cur.getString(4).toString());
            record = new OrderBO(Integer.parseInt(orderID),tableName,orderDate,NoCustomer,orderNote,orderPaid);
            break;
        }
        cur.close();
        return record;
    }
    //this function used to change Table
    public long updateTableName(int orderID, String tableName)
    {
        ContentValues cv = new ContentValues();
        if (tableName != null)
            cv.put(TABLE_NAME,tableName);
        return database.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String[]{Integer.toString(orderID)});
    }
    //these functions used to update payment
    public String getUnpaidOrder(String tableName)
    {
        String orderID="";
        String query = "SELECT Orders.OrderID ";
        query +=" FROM Orders,Tables,OrderDetail";
        query +=" WHERE Orders.tableName=Tables.tableName";
        query +=" AND Orders.OrderID=OrderDetail.OrderID";
        query +=" AND Tables.TableName='"+tableName+"'";
        query +=" GROUP BY 1";
        query +=" HAVING Orders.OrderPaid<(SELECT SUM(quantity*price) FROM OrderDetail)";
        Cursor cur = database.rawQuery(query, null);
        List<OrderBO> list = new ArrayList<OrderBO>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            orderID=cur.getString(0).toString();
            break;
        }
        cur.close();
        return orderID;
    }
    public String getTotalAmount(String tableName)
    {
        String orderID="";
        String query = "SELECT SUM(quantity*price)  as Total ,Tables.TableName";
        query +=" FROM Orders,Tables,OrderDetail";
        query +=" WHERE Orders.tableName=Tables.tableName";
        query +=" AND Orders.OrderID=OrderDetail.OrderID";
        query +=" AND Tables.TableName='"+tableName+"'";
        query +=" GROUP BY Tables.TableName";
        query +=" HAVING Orders.OrderPaid<(SELECT SUM(quantity*price) FROM OrderDetail)";
        Cursor cur = database.rawQuery(query, null);
        List<OrderBO> list = new ArrayList<OrderBO>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            orderID=cur.getString(0).toString();
            break;
        }
        cur.close();
        return orderID;
    }
    public String getPaidAmount(String tableName)
    {
        String orderID="";
        String query = "SELECT OrderPaid ";
        query +=" FROM Orders,Tables,OrderDetail";
        query +=" WHERE Orders.tableName=Tables.tableName";
        query +=" AND Orders.OrderID=OrderDetail.OrderID";
        query +=" AND Tables.TableName='"+tableName+"'";
        query +=" GROUP BY 1";
        query +=" HAVING Orders.OrderPaid<(SELECT SUM(quantity*price) FROM OrderDetail)";
        Cursor cur = database.rawQuery(query, null);
        List<OrderBO> list = new ArrayList<OrderBO>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            orderID=cur.getString(0).toString();
            break;
        }
        cur.close();
        return orderID;
    }
    public long updateOrderPaid(int orderID,Float payment)
    {
        ContentValues cv = new ContentValues();
        cv.put(ORDER_PAID,payment);
        return database.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String[]{Integer.toString(orderID)});
    }

}
