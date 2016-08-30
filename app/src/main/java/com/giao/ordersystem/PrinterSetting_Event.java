package com.giao.ordersystem;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Long on 6/2/2016.
 */
public class PrinterSetting_Event extends Activity {
    TextView myLabel;
    EditText myTextbox;
    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;


    public PrinterSetting_Event(TextView _myLabel,EditText _myTextbox)
    {
        this.myLabel=_myLabel;
        this.myTextbox=_myTextbox;
    }
    public PrinterSetting_Event()
    {

    }
    void findBT()
    {
        try {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                myLabel.setText("No bluetooth adapter available");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("RPP300")) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            myLabel.setText("Bluetooth device found.");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void openBT() throws IOException
    {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
            beginListenForData();
            myLabel.setText("Bluetooth Opened");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void beginListenForData()
    {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void sendData(String tableName,ArrayList<Order_View> orderList,OrderBO orderBO) throws IOException
    {
        try {
            String msg="";
            if(!tableName.equals("")) {
                // the text typed by the user
                msg = myTextbox.getText().toString();
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
                msg+="--------------------------\n";

            }

            mmOutputStream.write(msg.getBytes());
            // tell the user data were sent
            myLabel.setText("Data sent.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // close the connection to bluetooth printer.
    void closeBT() throws IOException
    {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
