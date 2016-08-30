package com.giao.ordersystem;

/**
 * Created by Long on 2/13/2016.
 */
public class DishBO {
    private int dishID;
    private String categoryName;
    private String dishName;
    private float dishPrice;
    private String dishDescription;
    private int dishAvailability;
    public DishBO(){}
    public DishBO(int dishId, String categoryName, String dishName,float dishPrice, String dishDescription, int availability)
    {
        this.dishID=dishId;
        this.categoryName=categoryName;
        this.dishName=dishName;
        this.dishPrice=dishPrice;
        this.dishDescription=dishDescription;
        this.dishAvailability=availability;
    }
    public int getDishID()    {return this.dishID;}
    public void setDishID(int dishID)    {this.dishID=dishID;}
    public String getCategoryName()    {return this.categoryName;}
    public void setcategoryName(String categoryName)    {this.categoryName=categoryName;}
    public String getDishName()    {return this.dishName;}
    public void setDishName(String dishName)    {this.dishName=dishName;}
    public float getDishPrice()    {return this.dishPrice;}
    public void setDishPrice(int dishPrice)    {this.dishPrice=dishPrice;}
    public String getDishDescription()    {return this.dishDescription;}
    public void setDishDescription(String dishDescription)    {this.dishDescription=dishDescription;}
    public int getDishAvailability() {return dishAvailability;}
    public void setDishAvailability(int dishAvailability) { this.dishAvailability = dishAvailability;}
}
