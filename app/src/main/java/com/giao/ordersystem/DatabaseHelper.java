package com.giao.ordersystem;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Long on 2/13/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="OrderSystem";
    private static final String TABLE_TABLES="Tables";
    private static final String TABLE_CATEGORY="Category";
    private static final String TABLE_ORDER="Orders";
    private static final String TABLE_ORDERDETAIL="OrderDetail";
    private static final String TABLE_MENU="Menu";
    //Tables Table columns
    public static final String TableName="TableName";
    //Category Table columns
    public static final String CategoryName="CategoryName";
    //Order Table columns
    public static final String OrderID="OrderID";
    public static final String Order_TableName="TableName";
    public static final String OrderDate="OrderDate";
    public static final String NumberOfCustomer="NumberOfCustomer";
    public static final String OrderNote="OrderNote";
    public static final String OrderPaid="OrderPaid";
    //OrderDetails Table columns
    public static final String OrderDetailID="OrderDetailID";
    public static final String OrderDetail_OrderID="OrderID";
    public static final String OrderDetail_DishID="DishID";
    public static final String OrderDetails_Quantity="Quantity";
    public static final String Price="Price";
    public static final String Note="Note";
    //Menu Table columns
    public static final String DishID="DishID";
    public static final String Dish_CategoryName="CategoryName";
    public static final String DishName="DishName";
    public static final String DishPrice="DishPrice";
    public static final String DishDescription="DishDescription";
    public static final String DishAvailability="DishAvailability";
    private static final int DATABASE_VERSION=5;

    private static Context context;
    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CreateTABLE_TABLES="CREATE TABLE IF NOT EXISTS "+TABLE_TABLES+" ("
                + TableName+ " TEXT PRIMARY KEY NOT NULL "
                +");";
        String CreateTABLE_CATEGORY="CREATE TABLE IF NOT EXISTS "+TABLE_CATEGORY+" ("
                + CategoryName+ " TEXT PRIMARY KEY NOT NULL "
                +");";
        String CreateTABLE_ORDER=//"CREATE TABLE IF NOT EXISTS Orders(OrderID INTEGER PRIMARY KEY AUTOINCREMENT, TableName TEXT NOT NULL, OrderDate NUMERIC NOT NULL, NumberOfCustomer INTEGER, OrderNote TEXT, OrderPaid REAL)";
                "CREATE TABLE IF NOT EXISTS "+TABLE_ORDER+ " ("
                +OrderID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Order_TableName+ " TEXT NOT NULL, "
                + OrderDate+ " NUMERIC NOT NULL, "
                + NumberOfCustomer+ " INTEGER, "
                + OrderNote+ " TEXT, "
                + OrderPaid+ " REAL "
                +");";
        String CreateTABLE_MENU="CREATE TABLE IF NOT EXISTS "+TABLE_MENU+" ("+DishID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Dish_CategoryName+ " TEXT NOT NULL, "
                + DishName+ " TEXT NOT NULL, "
                + DishPrice+ " REAL, "
                + DishDescription+ " TEXT, "
                + DishAvailability+ " REAL"
                +");";
        String CreateTABLE_ORDERDETAIL="CREATE TABLE IF NOT EXISTS "+TABLE_ORDERDETAIL+" ("+OrderDetailID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderDetail_OrderID+ " INTEGER NOT NULL, "
                + OrderDetail_DishID+ " INTEGER NOT NULL, "
                + OrderDetails_Quantity+ " INTEGER NOT NULL, "
                + Price+ " REAL, "
                + Note+ " TEXT NOT NULL "
                +");";
        db.execSQL(CreateTABLE_TABLES);
        db.execSQL(CreateTABLE_CATEGORY);
        db.execSQL(CreateTABLE_ORDER);
        db.execSQL(CreateTABLE_MENU);
        db.execSQL(CreateTABLE_ORDERDETAIL);



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.w(TableDAO.class.getName(), "Upgrading database from old to new version...");
        db.execSQL("DROP TABLE IF EXISTs " + TABLE_TABLES);
        db.execSQL("DROP TABLE IF EXISTs " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTs " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTs " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTs " + TABLE_ORDERDETAIL);
        onCreate(db);
    }

}
