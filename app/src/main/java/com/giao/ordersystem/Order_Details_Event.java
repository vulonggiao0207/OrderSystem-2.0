package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by Long on 5/14/2016.
 */
public class Order_Details_Event extends Activity {
    Context context;
    OrderDetailsDAO orderDetailsDAO;
    Activity order_details;
    public Order_Details_Event(Context _context,Activity order_details)
    {
        context=_context;
        orderDetailsDAO = new OrderDetailsDAO(context);
        this.order_details=order_details;
    }
    public DishBO DishInfo_OnLoad(String dishID)
    {
        DishDAO dishDAO= new DishDAO(context);
        dishDAO.open();
        DishBO record=dishDAO.itemDish(dishID);
        dishDAO.close();
        return record;
    }
    public void addDishButtonOK_OnClick(String orderID, String dishID, String quantity, String price, String note)
    {
        orderDetailsDAO.open();
        try {
            int _orderID = Integer.parseInt(orderID);
            int _dishID = Integer.parseInt(dishID);
            Float _quantity = Float.parseFloat("0");
            try{_quantity=Float.parseFloat(quantity);}catch(Exception e){_quantity=Float.parseFloat("0");}
            Float _price=Float.parseFloat("0");
            try{_price=Float.parseFloat(price);}catch(Exception e){_price=Float.parseFloat("0");}
            if(Float.parseFloat(quantity)<=0){Toast.makeText(context, "Quantity should more than 0",Toast.LENGTH_LONG ).show();return;}
            if(Float.parseFloat(price)<=0){Toast.makeText(context, "Price should more than $0",Toast.LENGTH_LONG ).show();return;}
            String _note = note;
            orderDetailsDAO.create(_orderID, _dishID, _quantity, _price, _note);
            orderDetailsDAO.close();
            //order_details.onBackPressed();
        }
        catch(Exception e)
        {
            Toast.makeText(context, "Failed. Please try again",Toast.LENGTH_LONG ).show();
            orderDetailsDAO.close();
        }
    }

}
