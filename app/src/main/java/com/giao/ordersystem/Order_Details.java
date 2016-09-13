package com.giao.ordersystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Long on 5/19/2016.
 */
public class Order_Details extends Activity {
    TextView dishNameTextView;
    EditText quantityEditText;
    EditText priceEditText;
    EditText noteEditText;
    Button addDishButtonOK;
    Button homeButton;
    Order_Details_Event event;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        //String dishID = "";
        int dishID=1;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dishID=getIntent().getIntExtra("dishID",1);
            //dishID = extras.getString("dishID");//Why it is null
        }
        event= new Order_Details_Event(this.getBaseContext(),this);
        //Declare controls
        dishNameTextView=(TextView)findViewById(R.id.dishNameTextView);
        quantityEditText=(EditText)findViewById(R.id.quantityEditText);
        priceEditText=(EditText)findViewById(R.id.priceEditText);
        noteEditText=(EditText)findViewById(R.id.noteEditText);
        addDishButtonOK=(Button)findViewById(R.id.addDishButtonOK);
        homeButton=(Button)findViewById(R.id.homeButton);
        //Load data to fields
        String temp=Integer.toString(dishID);
        DishInfo_OnLoad(temp);

        //addDishButtonOK event OnCick
        addDishButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get dishID and orderID
                int dishID=1;
                String orderID="";
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    dishID=getIntent().getIntExtra("dishID", 1);
                    orderID=extras.getString("orderID");
                }
                String quantity=quantityEditText.getText().toString();
                String price=priceEditText.getText().toString();
                String note=noteEditText.getText().toString();
                String temp=Integer.toString(dishID);
                event.addDishButtonOK_OnClick(orderID,temp,quantity,price,note);
                onBackPressed();
            }
        });
        //homeButton event
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void DishInfo_OnLoad(String dishID)
    {
        DishBO dishBO= event.DishInfo_OnLoad(dishID);
        dishNameTextView.setText(dishBO.getDishName());
        quantityEditText.setText("1");
        priceEditText.setText(Float.toString(dishBO.getDishPrice()));
    }
}
