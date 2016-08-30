package com.giao.ordersystem;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Long on 2/13/2016.
 */
public class DishDAO {
    private static final String KEY_ROWID = "dishID";
    private static final String CATEGORYID = "categoryName";
    private static final String DISH_NAME = "dishName";
    private static final String DISH_PRICE = "dishPrice";
    private static final String DISH_DESCRIPTION = "dishDescription";
    private static final String DISH_AVAILABILITY="Dishavailability";
    private static final String DATABASE_TABLE = "Menu";
    private static DatabaseHelper databaseHelper;
    //
    public final Context context;
    private SQLiteDatabase database;

    public DishDAO(Context context) {
        this.context = context;
        databaseHelper= new DatabaseHelper(context);
        // TODO Auto-generated constructor stub
    }

    public DishDAO open() throws SQLException {
        database = databaseHelper.getReadableDatabase();
        return this;
    }

    public void close() throws SQLException {
        databaseHelper.close();
    }

    public long create(String categoryName, String dishName, String dishPrice, String dishDecription, String availability) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put(CATEGORYID, categoryName);
        cv.put(DISH_NAME, dishName);
        cv.put(DISH_PRICE, dishPrice);
        cv.put(DISH_DESCRIPTION, dishDecription);
        cv.put(DISH_AVAILABILITY, availability);
        return database.insert(DATABASE_TABLE, null, cv);
    }

    public ArrayList<DishBO> list() throws SQLException {
        String query = "SELECT * FROM Menu";
        Cursor cur = database.rawQuery(query, null);
        ArrayList<DishBO> list = new ArrayList<DishBO>();
        int iRow = cur.getColumnIndex(KEY_ROWID);
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int dishID;
            Float dishprice;
            int availability;
            try{dishID=Integer.parseInt(cur.getString(0));}catch (Exception e){dishID=0;}
            try{dishprice=Float.parseFloat(cur.getString(3));}catch (Exception e){dishprice=Float.parseFloat("0");}
            try{availability=Integer.parseInt(cur.getString(5));}catch (Exception e){availability=0;}
            DishBO record = new DishBO(dishID, cur.getString(1), cur.getString(2),dishprice , cur.getString(4),availability);
            list.add(record);
        }
        cur.close();
        return list;
        }
    public ArrayList<DishBO> list(String categoryName) throws SQLException {
        String query = "SELECT * FROM Menu WHERE CategoryName='"+categoryName+"'";
        Cursor cur = database.rawQuery(query, null);
        ArrayList<DishBO> list = new ArrayList<DishBO>();
        int iRow = cur.getColumnIndex(KEY_ROWID);
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int dishID;
            Float dishprice;
            int availability;
            try{dishID=Integer.parseInt(cur.getString(0));}catch (Exception e){dishID=0;}
            try{dishprice=Float.parseFloat(cur.getString(3));}catch (Exception e){dishprice=Float.parseFloat("0");}
            try{availability=Integer.parseInt(cur.getString(5));}catch (Exception e){availability=0;}
            DishBO record = new DishBO(dishID, cur.getString(1), cur.getString(2),dishprice , cur.getString(4),availability);
            list.add(record);
        }
        cur.close();
        return list;
    }
    public DishBO itemDish(String dishID) throws SQLException {

        String query = "SELECT * FROM Menu WHERE DishID= "+dishID;
        Cursor cur = database.rawQuery(query, null);
        List<DishBO> list = new ArrayList<DishBO>();
        int iRow = cur.getColumnIndex(KEY_ROWID);
        DishBO record= new DishBO();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            Float dishprice;
            int availability;
            try{dishprice=Float.parseFloat(cur.getString(3));}catch (Exception e){dishprice=Float.parseFloat("0");}
            try{availability=Integer.parseInt(cur.getString(5));}catch (Exception e){availability=0;}
            record = new DishBO(Integer.parseInt(dishID), cur.getString(1), cur.getString(2),dishprice , cur.getString(4),availability);
            cur.close();
        }
        cur.close();
        return record;
    }

    public boolean remove(int dishID) throws SQLException {
        return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + dishID, null) > 0;
    }

    public boolean removeAll() {
        return database.delete(DATABASE_TABLE, null, null) > 0;
    }

    public long update(String dishID, String categoryID, String dishName, String dishPrice, String dishDecription, String dishAvailability) throws SQLException
    {
        ContentValues cv = new ContentValues();
        if (categoryID != null)
            cv.put(CATEGORYID, categoryID);
        if (dishName != null)
            cv.put(DISH_NAME, dishName);
        if (dishPrice != null)
            cv.put(DISH_PRICE, dishPrice);
        if (dishDecription != null)
            cv.put(DISH_DESCRIPTION, dishDecription);
            cv.put(DISH_AVAILABILITY,dishAvailability);
        return database.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String[]{dishID});
    }
}

