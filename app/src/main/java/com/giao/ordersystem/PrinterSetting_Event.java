package com.giao.ordersystem;
import android.content.Context;
import android.content.Intent;
import com.zj.btsdk.BluetoothService;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.util.Log;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Long on 6/2/2016.
 */
public class PrinterSetting_Event extends Activity {
    private static final int REQUEST_ENABLE_BT = 2;
    public static BluetoothService mService = null;//Bluetooth is open or not
    public static BluetoothDevice con_dev = null;//Is there any Bluetooth Devices connect to this device
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private final Context context;


    public PrinterSetting_Event(Context context)
    {
        this.context=context;
        // Create a new Bluetooth Service
        mService = new BluetoothService(context, mHandler);
        CreateNewBTService();
    }

    public void btnSearch_onClick()
    {
        Intent serverIntent = new Intent(context,DeviceList.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);//Intent of List of Bluetooth Device
    }
    public void btnSend_onClick(String text)
    {
        if( text.length() > 0 ){
            mService.sendMessage(text+"\n\n\n", "GBK");
        }
    }
    public void btnClose_onClick()
    {
        mService.stop();
    }

    public void CreateNewBTService()
    {
        // Create a new Bluetooth Service

        if( mService.isAvailable() == false ){
            Toast.makeText(context, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    public void checkBTTurnOn()
    {
        if( !mService.isBTopen())
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);//Intent of Turn-on Bluetooth
        }
    }
    public void stopBTService()
    {
        if (mService != null)
            mService.stop();
        mService = null;
    }
    private final  Handler mHandler = new Handler() {
        /*When the connection state between Device and Printer change, it will response a message
        the handlerMessage used to control those responses
        IF device try to connect to Printer (STATE_CHANGE_
        -->Connect sucessful
        -->Connecting
        -->Waiting for connection
        IF the connection is lost (CONNECTION_LOST)
        -->Device connection is lost
        IF the Device cannot connect to Printer
        -->Unable to connect to device*/
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case com.zj.btsdk.BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case com.zj.btsdk.BluetoothService.STATE_CONNECTED:
                            Toast.makeText(context, "Connect successful",
                                    Toast.LENGTH_SHORT).show();
                            //btnClose.setEnabled(true);
                            //btnSend.setEnabled(true);
                            break;
                        case com.zj.btsdk.BluetoothService.STATE_CONNECTING:
                            Log.d("Error Debug", "Connecting.....");
                            break;
                        case com.zj.btsdk.BluetoothService.STATE_LISTEN:
                        case com.zj.btsdk.BluetoothService.STATE_NONE:
                            Log.d("Error Debug","Waiting for connection.....");
                            break;
                    }
                    break;
                case com.zj.btsdk.BluetoothService.MESSAGE_CONNECTION_LOST:
                    Toast.makeText(context, "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    //btnClose.setEnabled(false);
                    //btnSend.setEnabled(false);
                    break;
                case com.zj.btsdk.BluetoothService.MESSAGE_UNABLE_CONNECT:
                    Toast.makeText(context, "Unable to connect to device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };
    //After calling 2 System Intents, System will return the resultCode
    //Base on the requestCode we send, we can identify how many result we could receive
    //IF requestCode==2 (meant by REQUEST_ENABLE_BT=2)    --> Can be OK (open) OR No(deny connection)
    //IF requestCode==1 (meant by REQUEST_CONNECT_DEVICE=1)     -->Can be OK (Connect to the BT device which has MAC address XXXX
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(context, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
                break;
            case  REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras()
                            .getString(DeviceList.EXTRA_DEVICE_ADDRESS);
                    con_dev = mService.getDevByMac(address);
                    mService.connect(con_dev);
                }
                break;
        }
    }
    void btnSend_onClick(String tableName,ArrayList<Order_View> orderList,OrderBO orderBO) throws IOException
    {
        String msg="";
        try {
            //String msg="";
            if(!tableName.equals("")) {
                // the text typed by the user
                //msg = myTextbox.getText().toString();
                msg += "\n";
            }
            else
            {
                Float total=0.0f;
                msg="--------------------------\n"+"Table: "+tableName +"\n";
                msg+=orderBO.getOrderDate()+"\n";
                msg+=orderBO.getNumberOfCustomer()+"\n";
                msg+=orderBO.getOrderNote()+"\n";
                msg+="--------------------------\n\n";
                for(int i=0;i<orderList.size();i++)
                {
                    Order_View temp=(Order_View)orderList.get(i);
                    msg +=Integer.toString(temp.getQuantity())+"";
                    msg +=temp.getdishName()+" \n";
                    msg +=Float.toString(temp.getSubtotal())+" \n";
                    msg +=temp.getNote()+"\n";
                    total=total+temp.getSubtotal();
                }
                msg+="--------------------------\n";
                msg+=new DecimalFormat(".##").format(total);
                msg+="--------------------------\n\n\n\n";

            }
            if( msg.length() > 0 ){
                mService.sendMessage(msg+"\n\n\n", "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
