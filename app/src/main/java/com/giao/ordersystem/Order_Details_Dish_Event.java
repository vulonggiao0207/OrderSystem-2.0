package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

/**
 * Created by Long on 5/14/2016.
 */
public class Order_Details_Dish_Event extends Activity {
    private final Context context;
    private DishDAO dishDAO;
    public Order_Details_Dish_Event(Context _context) {
        context=_context;
        dishDAO= new DishDAO(context);
    }
    public void categoryListView_OnLoad(ListView dishListView,String orderID,String categoryName)
    {
        dishDAO.open();
        Order_Details_Dish_Adapter dishAdatper = new Order_Details_Dish_Adapter(context, dishDAO.list(categoryName),orderID);
        dishListView.setAdapter(dishAdatper);
        dishDAO.close();
        dishAdatper.notifyDataSetChanged();
    }
}
