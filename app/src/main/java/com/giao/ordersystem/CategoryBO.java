package com.giao.ordersystem;

import java.util.ArrayList;

/**
 * Created by Long on 2/13/2016.
 */
public class CategoryBO {
    private String categoryName;
    private ArrayList<DishBO> Dishes;
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
    public ArrayList<DishBO> getDishes() {
        return Dishes;
    }

    public void setDishes(ArrayList<DishBO> Dishes) {
        this.Dishes = Dishes;
    }


}
