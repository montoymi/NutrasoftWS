package com.amadeus.nutrasoft.model;

import java.util.List;

public class Food {
    private String ndbNo;
    private FoodGroup foodGroup;
    private String name;
    private Float cost;
    private List<FoodNutrient> foodNutrientList;

    public String getNdbNo() {
        return ndbNo;
    }

    public void setNdbNo(String ndbNo) {
        this.ndbNo = ndbNo;
    }

    public FoodGroup getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(FoodGroup foodGroup) {
        this.foodGroup = foodGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public List<FoodNutrient> getFoodNutrientList() {
        return foodNutrientList;
    }

    public void setFoodNutrientList(List<FoodNutrient> foodNutrientList) {
        this.foodNutrientList = foodNutrientList;
    }
}
