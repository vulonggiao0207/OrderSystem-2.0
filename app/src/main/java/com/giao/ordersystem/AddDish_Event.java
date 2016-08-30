package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Long on 5/6/2016.
 */
public class AddDish_Event extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private final Context context;
    private DishDAO dishDAO;
    public AddDish_Event(Context _context) {
        context=_context;
        dishDAO= new DishDAO(context);
    }
    public void addDishButtonOK_OnClick(String Category, String Name, String Price, String Description, String Availability)
    {
        try {
            dishDAO.open();
            dishDAO.create(Category, Name, Price, Description, Availability);
            Toast.makeText(context, "Insert new dish succesfully", Toast.LENGTH_LONG).show();
            dishDAO.close();
        }
        catch (Exception e) {
            Toast.makeText(context, "Failed. Please try again", Toast.LENGTH_LONG).show();
            dishDAO.close();

    }
    }
 /*   public void homeButton_OnClick()
    {
        onBackPressed();

    }*/
}
