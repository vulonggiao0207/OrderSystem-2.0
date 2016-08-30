package com.giao.ordersystem;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.R.*;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity{

    private Button menuButton;
    private Button orderButton;
    private Button tableButton;
    private Button settingButton;
    private Button printerSettingButton;
    private Button exitButton;
    private MainActivity_Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        event=new MainActivity_Event(getBaseContext());
        Init();
        Init_Database();
    }
    protected void Init()
    {
        //Order Button
        orderButton=(Button)findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.orderButton_Click();
            }
        });
        //Menu Button
        menuButton=(Button)findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.menuButton_Click();
            }
        });
        //Table Button
        tableButton=(Button)findViewById(R.id.tableButton);
        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.tableButton_Click();
            }
        });
        //Setting Button
        settingButton=(Button)findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.settingButton_Click();
            }
        });
        //Printer Setting Button
        printerSettingButton=(Button)findViewById(R.id.printerSettingButton);
        printerSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.printerSettingButton_Click();
            }
        });
        //Exit Button
        exitButton=(Button)findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.exitButton_Click();
            }
        });
    }
    protected void Init_Database()
    {
        //Create database
        DatabaseHelper dbHelper= new DatabaseHelper(this.getBaseContext());
        dbHelper.getReadableDatabase();
        //Put data to database when install
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            InitData initData = new InitData(this.getBaseContext());
            initData.InitDatabase();
            //    InitDatabase(this.context);
            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }


}
