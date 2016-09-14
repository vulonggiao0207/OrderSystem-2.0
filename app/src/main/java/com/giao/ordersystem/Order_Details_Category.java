package com.giao.ordersystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Long on 5/14/2016.
 */
public class Order_Details_Category extends Activity{
    Order_Details_Category_Event event;
    TextView tableNameTextView;
    Button homeButton;
    //Button okButton;
    ExpandableListView dishCategoryListView;
    AutoCompleteTextView dishAutoTextView;
    String orderID="";
    String tableName="";
    int curPosition=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_category);
        event= new Order_Details_Category_Event(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = extras.getString("orderID");
            tableName=extras.getString("tableName");
        }
        //Declare controls
        tableNameTextView=(TextView)findViewById(R.id.tableNameTextView);
        //okButton=(Button)findViewById(R.id.OKButton);
        dishAutoTextView=(AutoCompleteTextView)findViewById(R.id.dishNameAutoTextView);
        dishCategoryListView=(ExpandableListView)findViewById(R.id.dishCategoryListView);
        homeButton=(Button)findViewById(R.id.homeButton);
        //Load data to tableNameTextView
        tableNameTextView.setText(tableName);
        //Load data to dishNameAutoTextView
        event.dishNameAutoTextView_onLoad(dishAutoTextView);
        /*okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DishDAO dishDAO = new DishDAO(getBaseContext());
                dishDAO.open();
                ArrayList<DishBO> dishList = new ArrayList<>();
                dishList = dishDAO.list();
                int dishID = dishList.get(curPosition).getDishID();
                //Open Order Details
                dishAutoTextView.setText("");
                Intent intent = new Intent(getBaseContext(), Order_Details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dishID", dishID);
                intent.putExtra("orderID", orderID);
                startActivity(intent);

            }
        });*/
        dishAutoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition=position;
                DishDAO dishDAO = new DishDAO(getBaseContext());
                dishDAO.open();
                ArrayList<DishBO> dishList = new ArrayList<>();
                dishList = dishDAO.list();

                //Get position in Original Dish List
                String selection = (String) parent.getItemAtPosition(position);
                curPosition = -1;
                for (int i = 0; i < dishList.size(); i++) {
                    if (dishList.get(i).getDishName().equals(selection)) {
                        curPosition = i;
                        int dishID = dishList.get(curPosition).getDishID();
                        //Open Order Details
                        dishAutoTextView.setText("");
                        Intent intent = new Intent(getBaseContext(), Order_Details.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("dishID", dishID);
                        intent.putExtra("orderID", orderID);
                        startActivity(intent);
                        break;
                    }
                }


            }
        });
        dishAutoTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
