package com.giao.ordersystem;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Long on 2/12/2016.
 */
public class TableDAO{
    public static final String KEY_TableName="TableName";
    private static final String DATABASE_TABLE="Tables";
    private static DatabaseHelper databaseHelper;
    //
    public final Context context;
    private SQLiteDatabase database;
    public TableDAO(Context context) {
        this.context=context;
        databaseHelper= new DatabaseHelper(context);
        // TODO Auto-generated constructor stub
    }
    public TableDAO open() throws SQLException
    {
        database=databaseHelper.getReadableDatabase();
        return this;
    }
    public void close() throws SQLException
    {
        databaseHelper.close();
    }
    public long create(String tableName) throws SQLException
    {
        ContentValues cv= new ContentValues();
        cv.put(KEY_TableName, tableName);
        return database.insert(DATABASE_TABLE,null ,cv);
    }
    public ArrayList<TableBO> list()
    {
        String query="SELECT * FROM Tables ORDER BY TableName";
        Cursor cur=database.rawQuery(query,null);
        ArrayList<TableBO> list = new ArrayList<TableBO>();
        int iRow= cur.getColumnIndex(KEY_TableName);
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()) {
            TableBO record = new TableBO(cur.getString(0));
            list.add(record);        }
        cur.close();
        return list;
    }
    public boolean remove(int tableName)
    {
        return database.delete(DATABASE_TABLE, KEY_TableName + "=" + tableName, null) > 0;
    }
    public boolean removeAll()
    {
        return database.delete(DATABASE_TABLE, null, null) > 0;
    }

}
