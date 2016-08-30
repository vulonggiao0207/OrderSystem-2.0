package com.giao.ordersystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Long on 5/18/2016.
 */
public class Order_Details_Dish_Adapter  extends BaseAdapter {
    private Context context;
    private ArrayList<DishBO> data;
    private static LayoutInflater inflater = null;
    private Button dishButton;
    private String orderID;

    public Order_Details_Dish_Adapter(Context context, ArrayList<DishBO> data,String orderID) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = new ArrayList<DishBO>(data);
        this.orderID=orderID;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public DishBO getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.order_details_dish_layout, null, true);
        }
        DishBO temp = (DishBO) data.get(position);
        dishButton = (Button) vi.findViewById(R.id.dishButton);
        dishButton.setText(temp.getDishName().toString());
        dishButton.setTag(temp.getDishID());
        dishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishID= v.getTag().toString();
                Intent intent= new Intent(context,Order_Details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dishID", dishID);
                intent.putExtra("orderID",orderID);
                context.startActivity(intent);
            }
        });
        return vi;
    }


}
