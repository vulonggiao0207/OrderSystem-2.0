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
    Button btnSendDraw;
    Button btnSend;
    Button btnClose;
    EditText edtContext;
    EditText edtPrint;
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    private static final int REQUEST_CONNECT_DEVICE = 1;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
            btnSendDraw = (Button) this.findViewById(R.id.btn_test);
            btnSendDraw.setOnClickListener(new ClickEvent());
            btnSearch = (Button) this.findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(new ClickEvent());
            btnSend = (Button) this.findViewById(R.id.btnSend);
            btnSend.setOnClickListener(new ClickEvent());
            btnClose = (Button) this.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new ClickEvent());
            edtContext = (EditText) findViewById(R.id.txt_content);
            btnClose.setEnabled(false);
            btnSend.setEnabled(false);
            btnSendDraw.setEnabled(false);
        } catch (Exception ex) {
            Log.e("Error message",ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null)
            mService.stop();
        mService = null;
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            if (v == btnSearch) {
                Intent serverIntent = new Intent(PrinterSetting.this,DeviceList.class);
                startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);
            } else if (v == btnSend) {
                String msg = edtContext.getText().toString();
                if( msg.length() > 0 ){
                    mService.sendMessage(msg+"\n", "GBK");
                }
            } else if (v == btnClose) {
                mService.stop();
            } else if (v == btnSendDraw) {
                String msg = "";
                String lang = getString(R.string.strLang);
                //printImage();

                byte[] cmd = new byte[3];
                cmd[0] = 0x1b;
                cmd[1] = 0x21;
                if((lang.compareTo("en")) == 0){
                    cmd[2] |= 0x10;
                    mService.write(cmd);
                    mService.sendMessage("Congratulations!\n", "GBK");
                    cmd[2] &= 0xEF;
                    mService.write(cmd);
                    msg = "  You have sucessfully created communications between your device and our bluetooth printer.\n\n"
                            +"  the company is a high-tech enterprise which specializes" +
                            " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";


                    mService.sendMessage(msg,"GBK");
                }else if((lang.compareTo("vn")) == 0){
                    cmd[2] |= 0x10;
                    mService.write(cmd);
                    mService.sendMessage("Chúc mừng\n", "GBK");
                    cmd[2] &= 0xEF;
                    mService.write(cmd);
                    msg = "  Bạn đã thành công trong việc kết nối thiết bị với máy in Blutooth.\n\n";

                    mService.sendMessage(msg,"GBK");
                }
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
                            btnClose.setEnabled(true);
                            btnSend.setEnabled(true);
                            btnSendDraw.setEnabled(true);
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
                    btnClose.setEnabled(false);
                    btnSend.setEnabled(false);
                    btnSendDraw.setEnabled(false);
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

    //��ӡͼ��
    @SuppressLint("SdCardPath")
    private void printImage() {
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(384);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/icon.jpg");
        sendData = pg.printDraw();
        mService.write(sendData);
    }
}

