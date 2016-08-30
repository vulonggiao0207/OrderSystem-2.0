package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;

import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 2/8/2016.
 */
public class Setting_Event extends Activity {
    private Context context;
    private TableDAO tableDAO;
    private CategoryDAO categoryDAO;
    public Setting_Event(Context _context) {
        this.context = _context;
        tableDAO= new TableDAO(this.context);
        categoryDAO= new CategoryDAO(this.context);
    }
    public void tableListView_OnLoad(ListView list){

        tableDAO.open();
        TableAdapter tableAdapter = new TableAdapter(context, tableDAO.list());
        list.setAdapter(tableAdapter);
        tableDAO.close();
    }
    public void categoryListView_OnLoad(ListView list){

        categoryDAO.open();
        CategoryAdapter categoryAdapter = new CategoryAdapter(context, categoryDAO.list());
        categoryDAO.close();
        list.setAdapter(categoryAdapter);

    }
    public void addTableButton_Click(String tableName,ListView list)
    {
        try {
            tableDAO.open();
            tableDAO.create(tableName);
            Toast.makeText(context, "Table inserted successful",Toast.LENGTH_LONG ).show();
            TableAdapter tableAdapter = new TableAdapter(context, tableDAO.list());
            list.setAdapter(tableAdapter);
            tableDAO.close();
        }
        catch (Exception e) {
            Toast.makeText(context, "Failed. Please try again",Toast.LENGTH_LONG ).show();
            tableDAO.close();
        }
    }
    public void addCategoryButton_Click(String categoryName, ListView list)
    {
        try {
            categoryDAO.open();
            categoryDAO.create(categoryName);
            Toast.makeText(context, "Food Category inserted successful",Toast.LENGTH_LONG ).show();
            CategoryAdapter categoryAdapter = new CategoryAdapter(context, categoryDAO.list());
            list.setAdapter(categoryAdapter);
            categoryDAO.close();
        }
        catch (Exception e) {
            Toast.makeText(context, "Failed. Please try again", Toast.LENGTH_LONG).show();
            categoryDAO.close();
        }
    }
    private AlertDialog.Builder build;
    public void saveButton_Click(ArrayList<TableBO> tableCollection, ArrayList<CategoryBO> categoryCollection)
    {

        //remove all
        tableDAO.open();
        tableDAO.removeAll();
        tableDAO.close();
        categoryDAO.open();
        categoryDAO.removeAll();
        categoryDAO.close();
        //then insert all
        tableDAO.open();
        for(TableBO tb:tableCollection)
        {
            tableDAO.create(tb.getTableName());
        }
        tableDAO.close();
        categoryDAO.open();
        for(CategoryBO ct:categoryCollection)
        {
            categoryDAO.create(ct.getCategoryName());
            //Insert each row
        }
        categoryDAO.close();

    }
  /*  public void homeButton_Click() {
        onBackPressed();
    }*/
}
