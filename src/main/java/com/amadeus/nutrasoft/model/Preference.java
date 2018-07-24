package com.amadeus.nutrasoft.model;

import java.util.List;

public class Preference {
    private User user;
    private DietType dietType;
    private Byte mealCount;
    private List<ExcludedFood> excludedFoodList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public Byte getMealCount() {
        return mealCount;
    }

    public void setMealCount(Byte mealCount) {
        this.mealCount = mealCount;
    }

    public List<ExcludedFood> getExcludedFoodList() {
        return excludedFoodList;
    }

    public void setExcludedFoodList(List<ExcludedFood> excludedFoodList) {
        this.excludedFoodList = excludedFoodList;
    }
}
