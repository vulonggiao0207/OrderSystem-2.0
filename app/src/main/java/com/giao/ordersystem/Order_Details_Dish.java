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
public class Order_Details_Dish extends Activity {
    String categoryName="";
    String orderID="";
    TextView dishTextView;
    Button homeButton;
    ListView dishListView;
    Order_Details_Dish_Event event;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_dish);
        //get categoryName
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryName = extras.getString("categoryName");
            orderID=extras.getString("orderID");
        }
        event= new Order_Details_Dish_Event(this.getBaseContext());
        //Declare controls
        dishTextView=(TextView)findViewById(R.id.dishTextView);
        homeButton=(Button)findViewById(R.id.homeButton);
        dishListView=(ListView)findViewById(R.id.dishListView);
        //Load data to ListView and dishTextView
        dishTextView.setText(categoryName);
        event.categoryListView_OnLoad(dishListView,orderID,categoryName);
        //homeButton event
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}