package com.giao.ordersystem;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.R.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Order extends Activity{
   // private Tables_Event table_event;
   final Context context = this;
    private Order_Event event;
//    private Order_Details_Event orderDetails_event;
    private OrderInfo_Event orderInfo_event;
    private static Button orderButton;
    private static ListView orderListView;
    private static Spinner tableSpinner;
    private static TextView totalTextView;
    private static Button deleteButton;
    private static Button infoButton;
    private static Button saveButton;
    private static Button homeButton;
    private ArrayList<Order_View> OriginorderDetailCollection;
    private String selectedTable;
    private int orderID;
    //Dialog control
    public static Dialog dialog;
    public static TextView tableNameTextView;
    public static EditText dateEditText;
    public static EditText customerQuantityEditText;
    public static EditText noteEditText;
    public static TextView availableTextView;
    public  static Button closeButton;
    public  static Button orderInfoOK;
    public static OrderInfo_Event info_event;
    @Override
    protected void onResume()
    {
        super.onResume();
        event.orderListView_OnLoad(orderListView,selectedTable);
        CaculateTotal();
        //getOrginal List
        OriginorderDetailCollection = new ArrayList<Order_View>();
        ListAdapter originOrderList= orderListView.getAdapter();
        for(int i=0;i<originOrderList.getCount();i++)
        {
            OriginorderDetailCollection.add((Order_View) originOrderList.getItem(i));
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        //tables_event= new Tables_Event(this.getApplicationContext());
      //  table_event= new Tables_Event(this.getBaseContext());
        event= new Order_Event(this.getBaseContext(),this);
        //orderDetails_event= new Order_Details_Event(this.getBaseContext(),this);
        orderInfo_event= new OrderInfo_Event(this.getBaseContext());
        //Declare Controls to Order
        tableSpinner= (Spinner)findViewById(R.id.tableSpinner);
        orderListView=(ListView)findViewById(R.id.orderListView);
        orderButton=(Button)findViewById(R.id.orderButton);
        totalTextView=(TextView)findViewById(R.id.totalTextView);
        deleteButton=(Button)findViewById(R.id.deleteButton);
        infoButton=(Button)findViewById(R.id.infoButton);
        homeButton=(Button)findViewById(R.id.homeButton);
        saveButton=(Button)findViewById(R.id.saveButton);
        // Declare Controls to Order_Info
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.order_info);
        info_event= new OrderInfo_Event(context);
        tableNameTextView=(TextView)dialog.findViewById(R.id.tableNameTextView);
        dateEditText=(EditText)dialog.findViewById(R.id.dateEditText);
        customerQuantityEditText=(EditText)dialog.findViewById(R.id.customerQuantityEditText);
        noteEditText=(EditText)dialog.findViewById(R.id.noteEditText);
        availableTextView=(TextView)dialog.findViewById(R.id.availableTextView);
        closeButton=(Button)dialog.findViewById(R.id.closeButton);
        orderInfoOK=(Button)dialog.findViewById(R.id.orderInfoOK);
        //Load tableSpinner data
        event.tableSpinner_OnLoad(tableSpinner);
        //Load Dish to ListView
        selectedTable=tableSpinner.getItemAtPosition(0).toString();
        orderID=orderInfo_event.getOrderbyTable(selectedTable);
        event.orderListView_OnLoad(orderListView, selectedTable);
        //set existed table information
        OrderInfo_OnLoad(selectedTable);
        CaculateTotal();
        //getOrginal List
        OriginorderDetailCollection = new ArrayList<Order_View>();
        ListAdapter originOrderList= orderListView.getAdapter();
        for(int i=0;i<originOrderList.getCount();i++)
        {
            OriginorderDetailCollection.add((Order_View) originOrderList.getItem(i));
        }
        //tableSpinner event
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Set the selected catogory into Red
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.rgb(000, 000, 255));
                //Load Dish to ListView
                selectedTable = tableSpinner.getItemAtPosition(position).toString();
                orderID = orderInfo_event.getOrderbyTable(selectedTable);
                event.orderListView_OnLoad(orderListView, selectedTable);
                CaculateTotal();
                //getOrginal List
                OriginorderDetailCollection = new ArrayList<Order_View>();
                ListAdapter originOrderList = orderListView.getAdapter();
                for (int i = 0; i < originOrderList.getCount(); i++) {
                    OriginorderDetailCollection.add((Order_View) originOrderList.getItem(i));
                }
                //Load info of the first selected table
                //OrderInfo_OnLoad(selectedTable);
                // reset value to controls
                tableNameTextView.setText(selectedTable);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
                Date date = new Date();
                dateEditText.setText(dateFormat.format(date));
                customerQuantityEditText.setText("0");
                noteEditText.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        //OrderListView event
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CaculateTotal();
            }
        });

        orderListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                TextView SelectecItemtextView = (TextView) findViewById(R.id.SelectecItemtextView);
                SelectecItemtextView.setText(orderListView.getSelectedItem().toString());
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load Table information
                //OrderInfo_OnLoad(selectedTable);
                //Get information from Order info
                String tableName=tableNameTextView.getText().toString();
                String orderDate=dateEditText.getText().toString();
                //Because after delete Datetime="" -->before save to DB, need to update current time
                if(orderDate.trim().equals("")) {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
                    Date date = new Date();
                    orderDate=dateFormat.format(date);
                }
                int NoCustomer=0;
                if(!customerQuantityEditText.getText().toString().equals(""))
                    NoCustomer=Integer.parseInt(customerQuantityEditText.getText().toString());;
                String orderNote=noteEditText.getText().toString();
                Float orderPaid=Float.parseFloat("0");
                //Save Table info UPDATE status -->RED
                info_event.orderInfoOK_OnClick(tableName, orderDate, NoCustomer, orderNote, orderPaid);
                //Update if any dish has been removed
                event.orderButton_OnClick(tableName, orderDate, NoCustomer, orderNote, orderPaid);
                //availableTextView.setText("Not Available");
                //availableTextView.setTextColor(Color.RED);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderInfo_event.orderInfoDELETE_OnClick(selectedTable);
                // reset value to controls
                tableNameTextView.setText(selectedTable);
                dateEditText.setText("");
                customerQuantityEditText.setText("0");
                noteEditText.setText("");
                //update
                availableTextView.setText("Available");
                availableTextView.setTextColor(Color.GREEN);
                event.orderListView_OnLoad(orderListView, selectedTable);
                CaculateTotal();
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            //orderDAO= new OrderDAO(this.getBaseContext());
            //Declare controls
            @Override
            public void onClick(View v) {

                //infoButton event//
                // reset value to controls
                tableNameTextView.setText(selectedTable);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
                Date date = new Date();
                dateEditText.setText(dateFormat.format(date));
                customerQuantityEditText.setText("0");
                noteEditText.setText("");
                //set existed table information
                OrderInfo_OnLoad(selectedTable);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //orderInfosOK Button Event
                orderInfoOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tableName=tableNameTextView.getText().toString();
                        String orderDate=dateEditText.getText().toString();
                        int NoCustomer=0;
                        if(!customerQuantityEditText.getText().toString().equals("")) NoCustomer=Integer.parseInt(customerQuantityEditText.getText().toString());;
                        String orderNote=noteEditText.getText().toString();
                        Float orderPaid=Float.parseFloat("0");
                        if(info_event.isValidDate(orderDate)==false)
                        {
                            return;
                        }
                        //Save Table info UPDATE status -->RED
                        info_event.orderInfoOK_OnClick(tableName, orderDate, NoCustomer, orderNote, orderPaid);
                        availableTextView.setText("Not Available");
                        availableTextView.setTextColor(Color.RED);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get orderID
                //get current orderDetails in ListView
                ArrayList<Order_View> orderDetailCollection = new ArrayList<Order_View>();
                ListAdapter orderList= orderListView.getAdapter();
                for(int i=0;i<orderList.getCount();i++)
                {
                    orderDetailCollection.add((Order_View) orderList.getItem(i));
                }
                event.saveButton_OnClick(selectedTable, OriginorderDetailCollection, orderDetailCollection, orderID);
                CaculateTotal();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void OrderInfo_OnLoad(String tableName)
    {
        OrderBO orderBO=info_event.OrderInfo_OnLoad(tableName);
        availableTextView.setText("Available");
        availableTextView.setTextColor(Color.GREEN);
        if(orderBO.getTableName()!=null) {
            tableNameTextView.setText(orderBO.getTableName());
            dateEditText.setText(orderBO.getOrderDate());
            customerQuantityEditText.setText(Integer.toString(orderBO.getNumberOfCustomer()));
            noteEditText.setText(orderBO.getOrderNote());
            availableTextView.setText("Not Available");
            availableTextView.setTextColor(Color.RED);
        }
       /* else
        {
            tableNameTextView.setText(selectedTable);
            //dateEditText.setText((new Calendar.getInstance()).ToString());
            //customerQuantityEditText.setText(Integer.toString(orderBO.getNumberOfCustomer()));
        }*/
    }
    public static void CaculateTotal()
    {
        Float total=0.0f;
        ListAdapter orderList= orderListView.getAdapter();
        for(int i=0;i<orderList.getCount();i++)
        {
            total+= ((Order_View) orderList.getItem(i)).getSubtotal();
        }

        String temp=new DecimalFormat(".##").format(total);
        String []arr=temp.toString().split("\\.");
        if(arr[1].length()<2)
            temp=temp+"0";
        totalTextView.setText("Total: "+temp);
    }

}
