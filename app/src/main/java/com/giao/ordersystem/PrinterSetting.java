package com.giao.ordersystem;
import android.content.Intent;
import com.zj.btsdk.BluetoothService;
import com.zj.btsdk.PrintPic;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.util.Log;


public class PrinterSetting extends Activity {
    Button btnSearch;
    Button btnSend;
    Button btnClose;
    EditText edtContext;
    Button homeButton;
    PrinterSetting_Event event;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer_setting);
        event= new PrinterSetting_Event(this.getApplicationContext());//errror is here
        // Create a new Bluetooth Service
        event.CreateNewBTService();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*If the Bluetooth is not turned on
        -->Require application to Turn-on Bluetooth
        * */
        event.checkBTTurnOn();
        try {
            btnSearch = (Button) this.findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(new ClickEvent());
            btnSend = (Button) this.findViewById(R.id.btnSend);
            btnSend.setOnClickListener(new ClickEvent());
            btnClose = (Button) this.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new ClickEvent());
            edtContext = (EditText) findViewById(R.id.txt_content);
            //btnClose.setEnabled(false);
            //btnSend.setEnabled(false);
            //homeButton ONCLICK event
            homeButton=(Button)findViewById(R.id.homeButton);
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception ex) {
            Log.e("Error message",ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Stop and clear BT Service
 //       event.stopBTService();
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            //Open the List of Bluetooth Devices
            if (v == btnSearch) {
                //Intent of List of Bluetooth Device
                event.btnSearch_onClick();
            }
            //Print from the selected bluetooth
            else if (v == btnSend) {
                event.btnSend_onClick(edtContext.getText().toString());
            }
            //Close the Bluetooth connection
            else if (v == btnClose) {
                event.btnClose_onClick();
            }
        }
    }



}

