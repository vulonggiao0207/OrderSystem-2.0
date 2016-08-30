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
        String dishID = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dishID = extras.getString("dishID");
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
        DishInfo_OnLoad(dishID);

        //addDishButtonOK event OnCick
        addDishButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get dishID and orderID
                String dishID = "";
                String orderID="";
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    dishID = extras.getString("dishID");
                    orderID=extras.getString("orderID");
                }
                String quantity=quantityEditText.getText().toString();
                String price=priceEditText.getText().toString();
                String note=noteEditText.getText().toString();
                event.addDishButtonOK_OnClick(orderID,dishID,quantity,price,note);
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
