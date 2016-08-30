package com.giao.ordersystem;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.R.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Menu extends Activity{
    private Menu_Event event;
    private static Spinner categorySpinner;
    private static Button homeButton;
    private static Button addDishButton;
    private static ListView dishListView;
    private String selectedCategory;
    private static DishDAO dishDAO;
    @Override
    protected void onResume()
    {
        super.onResume();
        event.dishListView_OnLoad(dishListView,selectedCategory);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        event= new Menu_Event(this.getApplicationContext());
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        homeButton=(Button)findViewById(R.id.homeButton);
        addDishButton=(Button)findViewById(R.id.addDishButton);
        dishListView=(ListView)findViewById(R.id.dishListView);
        //Load data to Spinner Category
        event.categorySpinner_OnLoad(categorySpinner);
        selectedCategory=categorySpinner.getItemAtPosition(0).toString();
        //Load Dish to ListView
        event.dishListView_OnLoad(dishListView,selectedCategory);

        //Add OnItemSelected event to categorySpinner
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Set the selected catogory into Red
                ((TextView)parentView.getChildAt(0)).setTextColor(Color.rgb(255, 000, 000));
                selectedCategory=parentView.getItemAtPosition(position).toString();
                //Load Dish to ListView
                event.dishListView_OnLoad(dishListView,selectedCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        addDishButton=(Button)findViewById(R.id.addDishButton);
        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName=selectedCategory;
                event.addDishButton_Onclick(categoryName);
            }
        });
        homeButton=(Button)findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //event.homButton_OnClick();
                onBackPressed();
            }
        });
    }
}