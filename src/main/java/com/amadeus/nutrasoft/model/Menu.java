package com.amadeus.nutrasoft.model;

import java.util.List;

public class Menu {
    private Integer id;
    private User coach;
    private DietType dietType;
    private byte week;
    private byte day;
    private List<MenuMeal> menuMealList;

    // Parámetros para generateDiet.
    private Byte mealCount;
    private String lang;
    private PlanDay planDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public byte getWeek() {
        return week;
    }

    public void setWeek(byte week) {
        this.week = week;
    }

    public byte getDay() {
        return day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    public List<MenuMeal> getMenuMealList() {
        return menuMealList;
    }

    public void setMenuMealList(List<MenuMeal> menuMealList) {
        this.menuMealList = menuMealList;
    }

    public Byte getMealCount() {
        return mealCount;
    }

    public void setMealCount(Byte mealCount) {
        this.mealCount = mealCount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public PlanDay getPlanDay() {
        return planDay;
    }

    public void setPlanDay(PlanDay planDay) {
        this.planDay = planDay;
    }
}
