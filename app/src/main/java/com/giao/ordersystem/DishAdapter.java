package com.giao.ordersystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Long on 5/5/2016.
 */
public class DishAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DishBO> data;
    private static LayoutInflater inflater = null;
    private TextView mText;
    private Button detailButton;
    private Button deleteButton;
    private DishDAO dishDAO;
    public DishAdapter(Context context, ArrayList<DishBO> data) {
        // TODO Auto-generated constructor stub
        View focusTarget = null;
        this.context = context;
        this.data = new ArrayList<DishBO>(data);
        dishDAO= new DishDAO(context);
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.dish_layout, null, true);
        }
        DishBO temp = (DishBO) data.get(position);
        mText = (TextView) vi.findViewById(R.id.EditDishEditText);
        detailButton = (Button) vi.findViewById(R.id.DetailDishButton);
        deleteButton = (Button) vi.findViewById(R.id.DeleteDishButton);
        mText.setText(temp.getDishName());
        detailButton.setTag(temp.getDishID());
        deleteButton.setTag(R.string.key1,temp.getDishID());
        deleteButton.setTag(R.string.key2,position);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Dish_Details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dishID", v.getTag().toString());
                context.startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int dishID = Integer.parseInt(v.getTag(R.string.key1).toString());
                    int position = Integer.parseInt(v.getTag(R.string.key2).toString());
                    DishBO dish = data.get(position);
                    data.remove(dish);
                    dishDAO.open();
                    dishDAO.remove(dishID);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Dish deleted successfully", Toast.LENGTH_LONG).show();
                    dishDAO.close();
                }
                catch(Exception e)
                {
                    Toast.makeText(context, "Failed to delete. Try again", Toast.LENGTH_LONG).show();
                    dishDAO.close();
                }
            }
        });
        return vi;
    }
}
