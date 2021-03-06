package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public void orderButton_OnClick(String tableName, String orderDate,  int numberOfcustomer, String orderNote,float orderPaid)
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
                    Float quantity=temp.getQuantity();
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
            //PrinterSetting_Event printer_event= new PrinterSetting_Event(context);
            //printer_event.btnSend_onClick(tableName,order_viewArrayList,orderBO);
            PrinterSetting printerSetting= new PrinterSetting();
            String orderprinted=printOrder(tableName,order_viewArrayList,orderBO);
            printerSetting.mService.sendMessage(orderprinted, "GBK");
            Toast.makeText(context, "Order is printed", Toast.LENGTH_LONG).show();

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

    public String printOrder(String tableName,ArrayList<Order_View> orderList,OrderBO orderBO) throws IOException
    {

        String msg="";
        try {
            if(tableName.equals("")) {
                // the text typed by the user
                //msg = myTextbox.getText().toString();
                return null;
            }
            else
            {
                Float total=0.0f;
                msg+="\n      XYZ RESTAURANT      \n\n";
                msg+="          TAX INVOICE";
                msg+="\n================================\n"+"Table: "+tableName +"\n";
                if(orderBO.getOrderDate().equals("null"))
                {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
                    Date date = new Date();
                    msg +=dateFormat.format(date);
                }
                else
                    msg+=orderBO.getOrderDate()+"\n";
                msg+="Guest: "+orderBO.getNumberOfCustomer()+"\n";
                msg+="Note: "+orderBO.getOrderNote()+"\n";
                msg+="================================\n";
                for(int i=0;i<orderList.size();i++)
                {
                    Order_View temp=(Order_View)orderList.get(i);
                    //Check whether quantity is decimal or integer
                    String quantity_str;
                    try{//Try to convert it into Integer
                        if(temp.getQuantity()-temp.getQuantity().intValue()==0)
                            quantity_str=(Integer.toString(temp.getQuantity().intValue()));
                        else//If not convert to Decimal
                            quantity_str=(Float.toString(temp.getQuantity()));
                    }
                    catch(Exception e)
                    {//Error -->Convert to decimal
                        quantity_str=(Float.toString(temp.getQuantity()));
                    }
                    msg +=quantity_str+" x ";
                    msg +=temp.getdishName();
                    msg +="\n                        $"+Float.toString(temp.getSubtotal())+" \n";
                    if(!(temp.getNote().trim()).equals(""))
                        msg +="["+temp.getNote()+"]\n";
                    total=total+temp.getSubtotal();
                }
                msg+="================================\n";
                msg+="Total: $"+new DecimalFormat(".##").format(total);
                msg+="\n================================\n\n\n\n" ;

            }
            return msg+"\n\n";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
