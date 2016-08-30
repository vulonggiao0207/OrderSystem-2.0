package com.giao.ordersystem;

/**
 * Created by Long on 2/12/2016.
 */
public class TableBO {

    private String tableName;
    public TableBO(String tableName)
    {
        this.tableName=tableName;
    }
    public TableBO()
    {

    }
    public String getTableName()
    {
        return this.tableName;
    }
    public void setTableID(String tableName)
    {
        this.tableName=tableName;
    }


}
