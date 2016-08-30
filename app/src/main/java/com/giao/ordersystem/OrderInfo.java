package com.giao.ordersystem;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Long on 5/13/2016.
 */
public class OrderInfo extends Activity{
    private TextView tableNameTextView;
    private EditText dateEditText;
    private EditText customerQuantityEditText;
    private EditText noteEditText;
    private TextView availableTextView;

    private Button homeButton;
    private Button orderInfoOK;
    private Button orderInfoDELETE;
    private OrderInfo_Event event;
    private String tableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tableName = extras.getString("tableName");
        }
        event= new OrderInfo_Event(this.getBaseContext());
        //orderDAO= new OrderDAO(this.getBaseContext());
        //Declare controls
        tableNameTextView=(TextView)findViewById(R.id.tableNameTextView);
        dateEditText=(EditText)findViewById(R.id.dateEditText);
        customerQuantityEditText=(EditText)findViewById(R.id.customerQuantityEditText);
        noteEditText=(EditText)findViewById(R.id.noteEditText);
        availableTextView=(TextView)findViewById(R.id.availableTextView);
        homeButton=(Button)findViewById(R.id.homeButton);
        orderInfoOK=(Button)findViewById(R.id.orderInfoOK);
        orderInfoDELETE=(Button)findViewById(R.id.orderInfoDELETE);
        //set value to controls
        tableNameTextView.setText(tableName);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
        Date date = new Date();
        dateEditText.setText(dateFormat.format(date));
        //set existed table information
        OrderInfo_OnLoad(tableName);
        //homeButton event
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //orderInforsOK Button Event
        orderInfoOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName=tableNameTextView.getText().toString();
                String orderDate=dateEditText.getText().toString();
                int NoCustomer=0;
                if(!customerQuantityEditText.getText().toString().equals("")) NoCustomer=Integer.parseInt(customerQuantityEditText.getText().toString());;
                String orderNote=noteEditText.getText().toString();
                Float orderPaid=Float.parseFloat("0");
                event.orderInfoOK_OnClick(tableName, orderDate, NoCustomer, orderNote, orderPaid);
                availableTextView.setText("Not Available");
                availableTextView.setTextColor(Color.RED);
            }
        });
        orderInfoDELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.orderInfoDELETE_OnClick(tableName);
                availableTextView.setText("Available");
                availableTextView.setTextColor(Color.GREEN);
            }
        });
    }
    public void OrderInfo_OnLoad(String tableName)
    {
        OrderBO orderBO=event.OrderInfo_OnLoad(tableName);
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
    }
}
