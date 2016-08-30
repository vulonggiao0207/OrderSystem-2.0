package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Long on 2/8/2016.
 */
public class Order_Event extends Activity {
    private Context context;
    private OrderDAO orderDAO;
    private TableDAO tableDAO;
    private OrderDetailsDAO orderDetailsDAO;
    Activity orderActivity;
    public Order_Event()
    {}
    public Order_Event(Context _context,Activity orderActivity)
    {
        this.context=_context;
        orderDAO= new OrderDAO(context);
        tableDAO= new TableDAO(context);
        orderDetailsDAO= new OrderDetailsDAO(context);
        this.orderActivity=orderActivity;
    }
    public void tableSpinner_OnLoad(Spinner tableSpinner)
    {
        tableDAO.open();
        ArrayList<String> tableList = new ArrayList<String>();
        for(int i=0;i<tableDAO.list().size();i++)
        {
            tableList.add(tableDAO.list().get(i).getTableName());
        }
        tableDAO.close();
        ArrayAdapter<String> tableAdapter =  new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,tableList);
        tableAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        tableSpinner.setAdapter(tableAdapter);
        tableDAO.close();
    }
    public void orderButton_OnClick(String tableName)
    {
        Intent intent = new Intent(context,OrderInfo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tableName", tableName);
        context.startActivity(intent);
        finish();

    }
    public void saveButton_OnClick(String tableName,ArrayList<Order_View> orginalOrder_viewArrayList,ArrayList<Order_View> order_viewArrayList,int orderID)
    {
        try {
            //remove all orderdetails where contains OrderID
            if(orginalOrder_viewArrayList.size()!=0) {
                orderDetailsDAO.open();
                for(int i=0;i<orginalOrder_viewArrayList.size();i++) {
                    Order_View temp = (Order_View) orginalOrder_viewArrayList.get(i);
                    orderDetailsDAO.remove(temp.getOrderDetailID());
                }
                orderDetailsDAO.close();
            }
            //re-create all orderdetails
            if (order_viewArrayList.size() != 0 )
            {
                orderDetailsDAO.open();
                for(int i=0;i<order_viewArrayList.size();i++)
                {
                    Order_View temp= (Order_View)order_viewArrayList.get(i);
                    int dishID=temp.getDishID();
                    int quantity=temp.getQuantity();
                    Float price= temp.getSubtotal()/temp.getQuantity();
                    String note=temp.getNote();
                    orderDetailsDAO.create(orderID,dishID,quantity,price,note);
                }
                orderDetailsDAO.close();
            }
            //If all good - Print it
            //get order information
            orderDAO.open();
            OrderBO orderBO=orderDAO.itemOrder(Integer.toString(orderID));
            orderDAO.close();
            //and print it out
            PrinterSetting_Event printer_event= new PrinterSetting_Event();
            printer_event.sendData(tableName,order_viewArrayList,orderBO);

        }
        catch (Exception e)
        {
            Toast.makeText(context, "Failed to save and send order", Toast.LENGTH_LONG).show();
            orderDAO.close();
            orderDetailsDAO.close();
        }
    }
    public void orderListView_OnLoad(ListView list,String tableName){

        orderDetailsDAO.open();
        Order_View_Adapter orderViewAdapter = new Order_View_Adapter(orderActivity,context, orderDetailsDAO.list_orderdetails(tableName));
        orderDetailsDAO.close();
        list.setAdapter(orderViewAdapter);

    }


}
