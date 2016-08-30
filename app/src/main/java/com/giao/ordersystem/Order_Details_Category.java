package com.giao.ordersystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Long on 5/14/2016.
 */
public class Order_Details_Category extends Activity{
    Order_Details_Category_Event event;
    TextView tableNameTextView;
    Button homeButton;
    ListView dishCategoryListView;
    String orderID="";
    String tableName="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_category);
        event= new Order_Details_Category_Event(this.getBaseContext());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = extras.getString("orderID");
            tableName=extras.getString("tableName");
        }
        //
        //Declare controls
        tableNameTextView=(TextView)findViewById(R.id.tableNameTextView);
        dishCategoryListView=(ListView)findViewById(R.id.dishCategoryListView);
        homeButton=(Button)findViewById(R.id.homeButton);
        //Load data to tableNameTextView
        tableNameTextView.setText(tableName);
        //Load data to ListView
        event.categoryListView_OnLoad(dishCategoryListView,orderID);
        //homeButton event
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
