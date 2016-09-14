package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Long on 5/14/2016.
 */
public class Order_Details_Category_Event extends Activity {
    private Activity activity;
    private OrderDAO orderDAO;
    private CategoryDAO categoryDAO;
    public String orderID;
    //These Attribute used to set ExpandableListView Category
    private Category_Expand_List_Adapter ExpAdapter;
    private ArrayList<CategoryBO> ExpListItems;
    private ExpandableListView ExpandList;
    public Order_Details_Category_Event(Activity activity) {
        this.activity=activity;
        orderDAO= new OrderDAO(activity.getBaseContext());
        categoryDAO= new CategoryDAO(activity.getBaseContext());

    }
    public void categoryListView_OnLoad(ExpandableListView categoryListView, final String orderID)
    {
        this.orderID=orderID;
        // Declare component for ExpandableListView
        ExpandList = categoryListView;
        ExpListItems = SetStandardGroups();
        ExpAdapter = new Category_Expand_List_Adapter(activity, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
        ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String group_name = ExpListItems.get(groupPosition).getCategoryName();
                ArrayList<DishBO> ch_list = ExpListItems.get(groupPosition).getDishes();
                //String child_name = ch_list.get(childPosition).getDishName();
                //showToastMsg(child_name + "\nChild");
                //int dishID= ch_list.get(childPosition).getDishID();
                int dishID= ch_list.get(childPosition).getDishID();
                Intent intent= new Intent(activity.getBaseContext(),Order_Details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dishID", dishID);
                intent.putExtra("orderID",orderID);
                activity.getBaseContext().startActivity(intent);
                return false;
            }
        });
        ExpandList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //String group_name = ExpListItems.get(groupPosition).getCategoryName();
                //showToastMsg(group_name + "\n Expanded");
            }
        });
        ExpandList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //String group_name = ExpListItems.get(groupPosition).getCategoryName();
                //showToastMsg(group_name + "\n Collapse");
            }
        });
    }
    public ArrayList<CategoryBO> SetStandardGroups() {

        ArrayList<CategoryBO> group_list = new ArrayList<>();
        ArrayList<DishBO> child_list;
        //Get all Category
        CategoryDAO categoryDAO = new CategoryDAO(activity.getBaseContext());
        DishDAO dishDAO=new DishDAO(activity.getBaseContext());
        categoryDAO.open();
        group_list= categoryDAO.list();
        categoryDAO.close();
        //Get all Dishes Based on Category
        for(int i=0;i<group_list.size();i++)
        {
            String categoryName=group_list.get(i).getCategoryName();
            child_list= new ArrayList<DishBO>();
            dishDAO.open();
            child_list= dishDAO.list(categoryName);
            dishDAO.close();
            group_list.get(i).setDishes(child_list);
        }
        return group_list;
    }

    public void dishNameAutoTextView_onLoad(AutoCompleteTextView autoCompleteTextView)
    {
        //get list of Dish
        DishDAO dishDAO= new DishDAO(activity.getBaseContext());
        dishDAO.open();
        ArrayList<DishBO> dishList= new ArrayList<>();
        dishList=dishDAO.list();
        ArrayList<String> dishNameList= new ArrayList<>();
        for(int i=0;i<dishList.size();i++)
        {
            dishNameList.add(dishList.get(i).getDishName());
        }
        dishDAO.close();
        //Bind data to autocompleteTextview
        ArrayAdapter adapter = new ArrayAdapter(activity.getBaseContext(),android.R.layout.simple_list_item_1,dishNameList);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
    }
}
