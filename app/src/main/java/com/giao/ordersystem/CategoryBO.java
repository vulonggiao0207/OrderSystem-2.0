package com.giao.ordersystem;

/**
 * Created by Long on 2/13/2016.
 */
public class CategoryBO {
    private String categoryName;
    public CategoryBO()
    {

    }
    public CategoryBO(String categoryName)
    {
        this.categoryName=categoryName;
    }
    public String getCategoryName()
    {
        return this.categoryName;
    }
    public void setCategoryName(String categoryName)
    {
        this.categoryName=categoryName;
    }
}
