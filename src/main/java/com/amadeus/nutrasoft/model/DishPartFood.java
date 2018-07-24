package com.amadeus.nutrasoft.model;

import javax.json.bind.annotation.JsonbTransient;

public class DishPartFood {
    private Integer id;
    @JsonbTransient
    private DishPart dishPart;
    private Food food;

    private Float weightPct;
    private Float weight;
    private Float pro = 0.0F;
    private Float cho = 0.0F;
    private Float fat = 0.0F;
    private Short energ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DishPart getDishPart() {
        return dishPart;
    }

    public void setDishPart(DishPart dishPart) {
        this.dishPart = dishPart;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Float getWeightPct() {
        return weightPct;
    }

    public void setWeightPct(Float weightPct) {
        this.weightPct = weightPct;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getPro() {
        return pro;
    }

    public void setPro(Float pro) {
        this.pro = pro;
    }

    public Float getCho() {
        return cho;
    }

    public void setCho(Float cho) {
        this.cho = cho;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Short getEnerg() {
        return energ;
    }

    public void setEnerg(Short energ) {
        this.energ = energ;
    }
}
