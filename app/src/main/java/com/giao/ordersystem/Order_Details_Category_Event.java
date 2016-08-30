package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

/**
 * Created by Long on 5/14/2016.
 */
public class Order_Details_Category_Event extends Activity {
    private final Context context;
    private OrderDAO orderDAO;
    private CategoryDAO categoryDAO;
    public Order_Details_Category_Event(Context _context) {
        context=_context;
        orderDAO= new OrderDAO(context);
        categoryDAO= new CategoryDAO(context);
    }
    public void categoryListView_OnLoad(ListView categoryListView,String orderID)
    {
        categoryDAO.open();
        Order_Details_Category_Adapter categoryAdatper = new Order_Details_Category_Adapter(context, categoryDAO.list(),orderID);
        categoryListView.setAdapter(categoryAdatper);
        categoryDAO.close();
        categoryAdatper.notifyDataSetChanged();
    }
}
