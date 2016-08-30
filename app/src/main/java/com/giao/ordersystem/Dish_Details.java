package com.giao.ordersystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Long on 5/11/2016.
 */
public class Dish_Details extends Activity {

    private Button saveDishButtonOK;
    private Button homeButton;
    private Spinner categorySpinner;
    private EditText dishNameEditText;
    private EditText priceEditText;
    private EditText desciptionEditText;
    private EditText availabilityEditText;
    private String dishID;
    private Dish_Details_Event event;
    private DishDAO dishDAO;
   // private CategoryDAO categoryDAO;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_details);
        event = new Dish_Details_Event(getBaseContext());
        dishDAO= new DishDAO(getBaseContext());
        //Declare controls
        //Declare new Controls
        categorySpinner=(Spinner)findViewById(R.id.categorySpinner);
        dishNameEditText=(EditText)findViewById(R.id.dishNameEditText);
        priceEditText=(EditText)findViewById(R.id.priceEditText);
        desciptionEditText=(EditText)findViewById(R.id.descriptionEditText);
        availabilityEditText=(EditText)findViewById(R.id.availabilityEditText);
        saveDishButtonOK=(Button)findViewById(R.id.saveDishButtonOK);
        homeButton=(Button)findViewById(R.id.homeButton);
        //get categoryName
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dishID = extras.getString("dishID");
        }
        //Load Details of Dish
        Load_Dish_Details();
        saveDishButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.saveDishButton_OnClick(dishID,categorySpinner.getSelectedItem().toString(), dishNameEditText.getText().toString(),
                priceEditText.getText().toString(),
                desciptionEditText.getText().toString(),
                availabilityEditText.getText().toString());
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void Load_Dish_Details()
    {
        //Load categorySpinner
        event.categorySpinner_OnLoad(categorySpinner);
        //Load selected dish information
        dishDAO.open();
        DishBO dishBO=dishDAO.itemDish(dishID);
        for(int i=0;i<categorySpinner.getAdapter().getCount();i++)
        {
            if((categorySpinner.getItemAtPosition(i).toString()).equals(dishBO.getCategoryName()))
            {
                categorySpinner.setSelection(i, true);
                break;
            }
        }
        //Set values to other fields
        dishNameEditText.setText(dishBO.getDishName());
        priceEditText.setText(Float.toString(dishBO.getDishPrice()));
        desciptionEditText.setText(dishBO.getDishDescription());
        availabilityEditText.setText(Integer.toString(dishBO.getDishAvailability()));
    }
}
