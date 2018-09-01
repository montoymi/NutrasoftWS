package com.amadeus.nutrasoft.model;

public class CalcDishParam {
    private PlanDay planDay;
    private Dish dish;
    private String lang;

    public PlanDay getPlanDay() {
        return planDay;
    }

    public void setPlanDay(PlanDay planDay) {
        this.planDay = planDay;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
