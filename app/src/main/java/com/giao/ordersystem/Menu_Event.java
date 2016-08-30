package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;

/**
 * Created by Long on 2/8/2016.
 */
public class Menu_Event extends Activity {
    private Context context;
    private CategoryDAO categoryDAO;
    private DishDAO dishDAO;
    public Menu_Event()
    {}
    public Menu_Event(Context context)
    {
        this.context=context;
        categoryDAO = new CategoryDAO(context);
        dishDAO=new DishDAO(context);
    }
    public void categorySpinner_OnLoad(Spinner categorySpinner)
    {
        categoryDAO.open();
        ArrayList<String> categoryList = new ArrayList<String>();
        for(int i=0;i<categoryDAO.list().size();i++)
        {
            categoryList.add(categoryDAO.list().get(i).getCategoryName());
        }
        categoryDAO.close();
        ArrayAdapter<String> categoryAdapter =  new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categoryList);
        categoryAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
    }
    public void dishListView_OnLoad(ListView dishListView, String categoryName)
    {
        dishDAO.open();
        DishAdapter tableAdapter = new DishAdapter(context,dishDAO.list(categoryName) );
        dishListView.setAdapter(tableAdapter);
        dishDAO.close();
        tableAdapter.notifyDataSetChanged();

    }
    public void addDishButton_Onclick(String categoryName)
    {
        Intent addDishIntent = new Intent(context, AddDish.class);
        addDishIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        addDishIntent.putExtra("categoryName", categoryName);
        context.startActivity(addDishIntent);
        finish();
    }
 /* public void homButton_OnClick()
    {
        onBackPressed();
    }*/
}
