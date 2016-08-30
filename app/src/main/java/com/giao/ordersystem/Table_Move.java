package com.giao.ordersystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Long on 5/14/2016.
 */
public class Table_Move extends Activity {
    private Table_Move_Event event;
    private Order_Event order_event;
    private static Button homeButton;
    private static Button saveButton;
    private static TextView fromTableTextView;
    private static Spinner toTableSpinner;
    private int orderID;
    private String tableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_move);
        event= new Table_Move_Event(this.getBaseContext(),this);
        order_event= new Order_Event(this.getBaseContext(),this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = Integer.parseInt(extras.getString("orderID"));
            tableName=extras.getString("tableName");
        }
        //Delcare controls
        homeButton=(Button)findViewById(R.id.homeButton);
        saveButton=(Button)findViewById(R.id.saveButton);
        fromTableTextView=(TextView)findViewById(R.id.fromTableTextView);
        toTableSpinner=(Spinner)findViewById(R.id.toTableSpinner);
        //Load tableName
        fromTableTextView.setText(tableName.toString());
        //Load data to Table Spinner
        order_event.tableSpinner_OnLoad(toTableSpinner);
        //homeButton event
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //saveButton event
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.moveTable(toTableSpinner.getSelectedItem().toString(),orderID);
            }
        });
    }
}
