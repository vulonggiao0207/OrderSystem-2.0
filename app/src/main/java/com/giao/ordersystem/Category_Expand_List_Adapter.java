package com.giao.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Long on 9/13/2016.
 */
public class Category_Expand_List_Adapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<CategoryBO> groups;

    public Category_Expand_List_Adapter(Context context, ArrayList<CategoryBO> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<DishBO> chList = groups.get(groupPosition)
                .getDishes();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        DishBO child = (DishBO) getChild(groupPosition,
                childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.order_details_dish_layout, null);
        }
        TextView dish = (TextView) convertView.findViewById(R.id.dishButton);
        dish.setText(child.getDishName().toString());

        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<DishBO> chList = groups.get(groupPosition)
                .getDishes();

        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        CategoryBO group = (CategoryBO) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.order_details_category_layout, null);
        }
        //Button tv = (Button) convertView.findViewById(R.id.categoryButton);
        TextView tv = (TextView) convertView.findViewById(R.id.categoryButton);
        tv.setText(group.getCategoryName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}