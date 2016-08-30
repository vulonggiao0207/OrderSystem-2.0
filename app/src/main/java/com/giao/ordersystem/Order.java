package com.giao.ordersystem;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.R.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Order extends Activity{
   // private Tables_Event table_event;
    private Order_Event event;
    private Order_Details_Event orderDetails_event;
    private OrderInfo_Event orderInfo_event;
    private static Button orderButton;
    private static ListView orderListView;
    private static Spinner tableSpinner;
    private static TextView totalTextView;
    private static Button saveButton;
    private static Button homeButton;
    private ArrayList<Order_View> OriginorderDetailCollection;
    private String selectedTable;
    private int orderID;
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
        orderDetails_event= new Order_Details_Event(this.getBaseContext(),this);
        orderInfo_event= new OrderInfo_Event(this.getBaseContext());
        //Declare Controls
        tableSpinner= (Spinner)findViewById(R.id.tableSpinner);
        orderListView=(ListView)findViewById(R.id.orderListView);
        orderButton=(Button)findViewById(R.id.orderButton);
        totalTextView=(TextView)findViewById(R.id.totalTextView);
        homeButton=(Button)findViewById(R.id.homeButton);
        saveButton=(Button)findViewById(R.id.saveButton);
        //Load tableSpinner data
        event.tableSpinner_OnLoad(tableSpinner);
        //Load Dish to ListView
        selectedTable=tableSpinner.getItemAtPosition(0).toString();
        orderID=orderInfo_event.getOrderbyTable(selectedTable);
        event.orderListView_OnLoad(orderListView, selectedTable);
        CaculateTotal();
        //getOrginal List
        OriginorderDetailCollection = new ArrayList<Order_View>();
        ListAdapter originOrderList= orderListView.getAdapter();
        for(int i=0;i<originOrderList.getCount();i++)
        {
            OriginorderDetailCollection.add((Order_View) originOrderList.getItem(i));
        }
        //tableSpinner event
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Set the selected catogory into Red
                ((TextView)parentView.getChildAt(0)).setTextColor(Color.rgb(255, 000, 000));
                //Load Dish to ListView
                selectedTable=tableSpinner.getItemAtPosition(position).toString();
                orderID=orderInfo_event.getOrderbyTable(selectedTable);
                event.orderListView_OnLoad(orderListView, selectedTable);
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
                                                } );
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.orderButton_OnClick(selectedTable);
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
                event.saveButton_OnClick(selectedTable,OriginorderDetailCollection,orderDetailCollection,orderID);
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
