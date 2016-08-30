package com.giao.ordersystem;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Long on 5/13/2016.
 */
public class OrderInfo_Event {
    OrderDAO orderDAO;
    Context context;
    public OrderInfo_Event(Context _context)
    {
        context=_context;
        orderDAO= new OrderDAO(context);
    }
    public void orderInfoOK_OnClick(String tableName, String orderDate,  int numberOfcustomer, String orderNote,float orderPaid)
    {
        try {
            orderDAO.open();
            //Check if the table is available
            //IF it available. Add new to Order
            //ELSE update current records in Order
            String orderID=orderDAO.checkTableAvailable(tableName);
            if(orderID.equals("")) {
                orderDAO.create(tableName, orderDate, numberOfcustomer, orderNote, orderPaid);
                Toast.makeText(context, "Insert new table information succesfully", Toast.LENGTH_LONG).show();
            }
            else {
                orderDAO.update(orderID, tableName, orderDate, numberOfcustomer, orderNote, orderPaid);
                //Toast.makeText(context, "Update table information succesfully", Toast.LENGTH_LONG).show();
            }
            orderDAO.close();
            //Open Dish Category
            orderDAO.open();
            orderID=orderDAO.checkTableAvailable(tableName);
            orderDAO.close();
            Intent intent = new Intent(context,Order_Details_Category.class);
            intent.putExtra("orderID",orderID);
            intent.putExtra("tableName",tableName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        catch (Exception e) {
            Toast.makeText(context, "Failed to update table information", Toast.LENGTH_LONG).show();
            orderDAO.close();
        }
    }
    public void orderInfoDELETE_OnClick(String tableName)
    {
        try{
            orderDAO.open();
            String orderID=orderDAO.checkTableAvailable(tableName);
            if(orderID.equals(""))
            {
                Toast.makeText(context, "Table is available", Toast.LENGTH_LONG).show();
                orderDAO.close();
                return;
            }
            orderDAO.remove(Integer.parseInt(orderID));
            Toast.makeText(context, "Delete table sucessfully", Toast.LENGTH_LONG).show();
            orderDAO.close();
        }
        catch (Exception e) {
            Toast.makeText(context, "Failed to delete table", Toast.LENGTH_LONG).show();
            orderDAO.close();
        }
    }
    public OrderBO OrderInfo_OnLoad(String tableName)
    {
        orderDAO.open();
        String orderID=orderDAO.checkTableAvailable(tableName);
        OrderBO orderBO=orderDAO.itemOrder(orderID);
        orderDAO.close();
        return orderBO;

    }
    public int getOrderbyTable(String tableName)
    {
        try {
            orderDAO.open();
            int orderID = Integer.parseInt(orderDAO.checkTableAvailable(tableName));
            orderDAO.close();
            return orderID;
        }
        catch(Exception e)
        {
            return 0;
        }
    }
}
