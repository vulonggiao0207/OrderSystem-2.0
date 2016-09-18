package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Long on 5/14/2016.
 */
public class Table_Pay extends Activity {
    private Table_Pay_Event event;
    private Order_Event order_event;
    private static Button homeButton;
    private static Button saveButton;
    private static TextView tableNameTextView;
    private static TextView totalTextView;
    private static TextView paidTextView;
    private static TextView remainingTextView;
    private static TextView refundTextView;
    public static EditText payEditText;
    private int orderID;
    private String tableName;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_pay);
        event = new Table_Pay_Event(this.getBaseContext(), this);
        order_event = new Order_Event(this.getBaseContext(), this);
        context=this.getBaseContext();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = Integer.parseInt(extras.getString("orderID"));
            tableName = extras.getString("tableName");
        }
        //Delcare controls
        homeButton = (Button) findViewById(R.id.homeButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        tableNameTextView = (TextView) findViewById(R.id.tableNameTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        paidTextView = (TextView) findViewById(R.id.paidTextView);
        remainingTextView = (TextView) findViewById(R.id.remainingTextView);
        refundTextView=(TextView)findViewById(R.id.refundTextView);
        payEditText=(EditText)findViewById(R.id.payEditText);
        //load data to controls
        loadDataToControls();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(totalTextView.getText().equals("0.0"))
                    {
                        OrderInfo_Event order_event= new OrderInfo_Event(context);
                        order_event.orderInfoDELETE_OnClick(tableName);
                        onBackPressed();
                    }
                    else
                    {
                        if (payEditText.getText().toString().trim().equals("")) {
                            Toast.makeText(getBaseContext(), "Please enter payment. Try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        BigDecimal currentPayment = BigDecimal.valueOf(Float.parseFloat(payEditText.getText().toString()));
                        BigDecimal totalPayment = currentPayment.add(BigDecimal.valueOf(Float.parseFloat(paidTextView.getText().toString())));
                        BigDecimal remainingPayment = BigDecimal.valueOf(Float.parseFloat(remainingTextView.getText().toString()));
                        event.payTable(tableName, totalPayment.floatValue());
                        //Reload data to Controls
                        BigDecimal refund = remainingPayment.subtract(currentPayment);
                        if (refund.signum() < 0) {
                            String temp = new DecimalFormat("#.##").format(refund.multiply(BigDecimal.valueOf(-1)));
                            refundTextView.setText(temp);
                            saveButton.setEnabled(false);
                        } else {
                            saveButton.setEnabled(true);
                        }
                        if (remainingPayment.signum() <= 0) {
                            saveButton.setEnabled(false);
                        }
                    }
                    loadDataToControls();

                }
                catch(Exception e)
                {
                    Toast.makeText(getBaseContext(), "Failed to update table payment. Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    //Load data to controls
    public void loadDataToControls()
    {
        Float total= event.getTotalAmount(tableName);
        Float paid=event.getPaidAmount(tableName);
        Float remaining=total-paid;
        tableNameTextView.setText(tableName.toString());
        if(total!=0)totalTextView.setText(Float.toString(total));
        if(paid!=0)paidTextView.setText(Float.toString(paid));

        try{
            String temp ="";
            if(remaining<=0)
                remainingTextView.setText("0.0");

            else {
                temp = new DecimalFormat("#.##").format(remaining);
                remainingTextView.setText(temp);
            }
            if (remainingTextView.getText() == "0.0")
                Toast.makeText(this, "Thank you! Purchase is fully successful! Table will be cleared!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Remaining purchase: $" + temp, Toast.LENGTH_LONG).show();

        }
        catch(Exception e)
        {
            remainingTextView.setText((Float.toString(remaining)));
            Toast.makeText(this,"Purchasing error! Please try again", Toast.LENGTH_LONG).show();
        }

    }
}