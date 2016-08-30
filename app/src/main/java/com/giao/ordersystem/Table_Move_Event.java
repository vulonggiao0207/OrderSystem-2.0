package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
/**
 * Created by Long on 5/14/2016.
 */
public class Table_Move_Event extends Activity {
    private Context context;
    private OrderDAO orderDAO;
    private TableDAO tableDAO;
    Activity tableActivity;
    public Table_Move_Event()
    {

    }
    public Table_Move_Event(Context _context,Activity tableActivity)
    {
        this.context=_context;
        orderDAO= new OrderDAO(context);
        tableDAO= new TableDAO(context);
        this.tableActivity=tableActivity;
    }
    public void moveTable(String to, int orderID)
    {
        try {

            orderDAO.open();
            if(orderDAO.checkTableAvailable(to).toString().equals("")) {
                orderDAO.updateTableName(orderID, to);
                orderDAO.close();
                tableActivity.onBackPressed();
            }
            else
                Toast.makeText(context, to+" is not available. Please select another table", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(context, "Failed to move table. Try again.", Toast.LENGTH_LONG).show();
            orderDAO.close();
        }
    }

}
