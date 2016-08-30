package com.giao.ordersystem;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.R.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.ServiceLoader;
import android.content.Context;

public class Setting extends Activity{
    private Button addTableButton;
    private Button addCategoryButton;
    private Button saveButton;
    private Button homeButton;
    private EditText tableEditText;
    private EditText categoryEditText;
    private ListView tableListView;
    private ListView catetoryListView;
    private Setting_Event event;
    private TableDAO tableDAO;
    private CategoryDAO categoryDAO;
//    private TableAdapter tableAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        event=new Setting_Event(this.getApplicationContext());
        Init();
        Context context= this.getApplicationContext();
        //Load list of Table to ListView
        tableDAO = new TableDAO(context);
        event.tableListView_OnLoad(tableListView);
        //Load list of Category to ListView
        categoryDAO= new CategoryDAO(context);
        event.categoryListView_OnLoad(catetoryListView);

    }
    private void Init()
    {
        tableListView=(ListView)findViewById(R.id.tableListView);
        catetoryListView=(ListView)findViewById(R.id.categoryListView);
        //tableName EditText
        tableEditText=(EditText)findViewById(R.id.tableNameEditText);
        //CategoryName EditText
        categoryEditText=(EditText)findViewById(R.id.categoryEditText);
        // addTableButton, OclickListener
        addTableButton = (Button)findViewById(R.id.addTableButton);
        addTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName=tableEditText.getText().toString();
                event.addTableButton_Click(tableName,tableListView);
            }
        });
        //addCategoryButton, OnClickListener
        addCategoryButton =(Button)findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName=categoryEditText.getText().toString();
                event.addCategoryButton_Click(categoryName, catetoryListView);      }
        });
        //SaveButton, OnClickListener
        saveButton=(Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<TableBO> tableCollection = new ArrayList<TableBO>();
                ListAdapter tableList= tableListView.getAdapter();
                for(int i=0;i<tableList.getCount();i++)
                {
                    tableCollection.add((TableBO) tableList.getItem(i));
                }
                ArrayList<CategoryBO> categoryCollection=new ArrayList<CategoryBO>();
                ListAdapter categoryList= catetoryListView.getAdapter();
                for(int i=0;i<categoryList.getCount();i++)
                {
                    categoryCollection.add((CategoryBO)categoryList.getItem(i));
                }

                event.saveButton_Click(tableCollection, categoryCollection);
            }
        });
        //HomeButton, OnClickListener
        homeButton=(Button)findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //event.homeButton_Click();
                onBackPressed();
            }
        });
    }


}

