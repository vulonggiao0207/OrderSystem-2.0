package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Long on 5/14/2016.
 */
public class Table_Pay_Event extends Activity {
    private Context context;
    private OrderDAO orderDAO;
    private TableDAO tableDAO;
    Activity tableActivity;
    public Table_Pay_Event()
    {

    }
    public Table_Pay_Event(Context _context,Activity tableActivity)
    {
        this.context=_context;
        orderDAO= new OrderDAO(context);
        tableDAO= new TableDAO(context);
        this.tableActivity=tableActivity;
    }
    public void payTable(String tableName, Float payment)
    {
        try {
            int orderID=0;
            orderDAO.open();
            try{orderID=Integer.parseInt(orderDAO.getUnpaidOrder(tableName));}catch(Exception e){orderID=0;}
            orderDAO.close();
            orderDAO.open();
            orderDAO.updateOrderPaid(orderID,payment);
          //  tableActivity.onBackPressed();
            orderDAO.close();
        }
        catch (Exception e){
            Toast.makeText(context, "Failed to update table payment. Try again.", Toast.LENGTH_LONG).show();
            orderDAO.close();
        }
    }
    public Float getPaidAmount(String tableName)
    {
        Float unpaid=0.0f;
        try {
            orderDAO.open();
            String temp=orderDAO.getPaidAmount(tableName);
            orderDAO.close();
            return Float.parseFloat(temp);
        }
        catch(Exception e) {
            return Float.parseFloat("0.0");
        }
    }
    public Float getTotalAmount(String tableName)
    {
        Float unpaid=0.0f;
        try {
            orderDAO.open();
            String temp=orderDAO.getTotalAmount(tableName);
            orderDAO.close();
            return Float.parseFloat(temp);
        }
        catch(Exception e) {
            return Float.parseFloat("0.0");
        }
    }

}

