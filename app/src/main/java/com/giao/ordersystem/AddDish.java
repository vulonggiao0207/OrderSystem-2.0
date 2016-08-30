package com.giao.ordersystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Long on 5/6/2016.
 */
public class AddDish extends Activity {
    private AddDish_Event event;
    private Button addDishButtonOK;
    private Button homeButton;
    private TextView categoryNameTextView;
    private EditText dishNameEditText;
    private EditText priceEditText;
    private EditText desciptionEditText;
    private EditText availabilityEditText;
    private String categoryName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dish);
        //get categoryName
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            categoryName = extras.getString("categoryName");
        }

        //Declare new AddDish_Event
        event=new AddDish_Event(getApplicationContext());
        //Declare new Controls
        categoryNameTextView=(TextView)findViewById(R.id.category_dishTextView);
        dishNameEditText=(EditText)findViewById(R.id.dishNameEditText);
        priceEditText=(EditText)findViewById(R.id.priceEditText);
        desciptionEditText=(EditText)findViewById(R.id.descriptionEditText);
        availabilityEditText=(EditText)findViewById(R.id.availabilityEditText);
        //set categoryName to TextView
        categoryNameTextView.setText(categoryName);
        //Add events
        //addDishButton ONCLICK event
        addDishButtonOK=(Button)findViewById(R.id.addDishButtonOK);
        addDishButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category=categoryNameTextView.getText().toString();
                String name=dishNameEditText.getText().toString();
                String price=priceEditText.getText().toString();
                String description=desciptionEditText.getText().toString();
                String availability=availabilityEditText.getText().toString();
                event.addDishButtonOK_OnClick(category,name,price,description,availability);
            }
        });
        //homeButton ONCLICK event
        homeButton=(Button)findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
