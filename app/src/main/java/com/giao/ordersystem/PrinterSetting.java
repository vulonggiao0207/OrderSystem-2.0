package com.giao.ordersystem;
import android.content.Intent;
//import com.zj.btsdk.BluetoothService;
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
    //PrinterSetting_Event event= new PrinterSetting_Event(getApplicationContext());
    private static final int REQUEST_ENABLE_BT = 2;
    public static BluetoothService mService = null;
    public static BluetoothDevice con_dev = null;
    private static final int REQUEST_CONNECT_DEVICE = 1;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer_setting);
        mService = new BluetoothService(this, mHandler);
        if( mService.isAvailable() == false ){
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if( mService.isBTopen() == false)
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
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
        //if (mService != null)
            //mService.stop();
        //mService = null;
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            if (v == btnSearch) {
                Intent serverIntent = new Intent(PrinterSetting.this,DeviceList.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            } else if (v == btnSend) {
                String msg = edtContext.getText().toString();
                if( msg.length() > 0 ){
                    mService.sendMessage(msg+"\n\n\n", "GBK");
                }
            } else if (v == btnClose) {
                mService.stop();
            }
        }
    }


    private final  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(), "Connect successful",
                                    Toast.LENGTH_SHORT).show();
                            //btnClose.setEnabled(true);
                            //btnSend.setEnabled(true);
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.d("Error Debug","Connecting.....");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            Log.d("Error Debug","Waiting for connection.....");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:
                    Toast.makeText(getApplicationContext(), "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    //btnClose.setEnabled(false);
                    //btnSend.setEnabled(false);
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:
                    Toast.makeText(getApplicationContext(), "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
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

}

