package com.giao.ordersystem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.R.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class Tables extends Activity{
    private Tables_Event event;
    private static Button homeButton;
    private static ListView tableListView;
    @Override
    protected void onResume()
    {
        super.onResume();
        event.tableListView_OnLoad(tableListView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tables);
        event= new Tables_Event(this.getApplicationContext());
        tableListView = (ListView) findViewById(R.id.tableOrderListView);
        tableListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected (AdapterView < ? > parentView, View selectedItemView,
        int position, long id){
            TextView SelectecItemtextView = (TextView) findViewById(R.id.SelectecItemtextView);
            SelectecItemtextView.setText(tableListView.getSelectedItem().toString());
            // your code here
        }
        @Override
        public void onNothingSelected (AdapterView < ? > parentView){
            // your code here
        }

    }

    );
    event.tableListView_OnLoad(tableListView);
    homeButton=(Button)findViewById(R.id.homeButton);
    homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // event.homButton_OnClick();
                onBackPressed();
            }
        });

    }
}