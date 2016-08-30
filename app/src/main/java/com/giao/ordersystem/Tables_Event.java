package com.giao.ordersystem;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

/**
 * Created by Long on 2/8/2016.
 */
public class Tables_Event extends Activity {
    private Context context;
    private TableDAO tableDAO;
    public Tables_Event()
    {}
    public Tables_Event(Context context)
    {
        this.context=context;
        tableDAO= new TableDAO(context);
    }
    public void tableListView_OnLoad(ListView tableListView)
    {
        tableDAO.open();
        Table_PayandMove_Adapter tableAdapter = new Table_PayandMove_Adapter(context, tableDAO.list());
        tableListView.setAdapter(tableAdapter);
        tableDAO.close();
        tableAdapter.notifyDataSetChanged();
    }
    public void tableListView_OnItemSelected(ListView tableListView)
    {

    }
 /*   public void homButton_OnClick()
    {
       onBackPressed();
    }*/

}
