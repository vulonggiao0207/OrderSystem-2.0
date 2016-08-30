package com.giao.ordersystem;

/**
 * Created by Long on 2/12/2016.
 */
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Order_Details_Category_Adapter extends BaseAdapter{
    private Context context;
    private ArrayList<CategoryBO> data;
    private static LayoutInflater inflater = null;
    private Button categoryButton;
    private String orderID="";

    public Order_Details_Category_Adapter(Context context, ArrayList<CategoryBO> data,String orderID) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = new ArrayList<CategoryBO>(data);
        this.orderID=orderID;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public CategoryBO getItem(int position) {
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
            vi = inflater.inflate(R.layout.order_details_category_layout, null, true);
        }
        CategoryBO temp = (CategoryBO) data.get(position);
        categoryButton = (Button) vi.findViewById(R.id.categoryButton);
        categoryButton.setText(temp.getCategoryName().toString());
        categoryButton.setTag(temp.getCategoryName().toString());
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName= v.getTag().toString();
                Intent intent= new Intent(context,Order_Details_Dish.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("categoryName", categoryName);
                intent.putExtra("orderID",orderID);
                context.startActivity(intent);
            }
        });
        return vi;
    }


}
