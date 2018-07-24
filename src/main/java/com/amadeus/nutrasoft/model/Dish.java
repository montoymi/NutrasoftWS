package com.amadeus.nutrasoft.model;

import java.util.List;

public class Dish {
    private Integer id;
    private User coach;
    private String name;
    private List<DishPart> dishPartList;

    private Float totalPro = 0.0F;
    private Float totalCho = 0.0F;
    private Float totalFat = 0.0F;
    private Short totalEnerg;

    private Float cost;

    // Permite poner todos los alimentos juntos. Se elimina
    // el concepto de partes o componentes para mostrarlo
    // en la UI.
    private List<DishPartFood> dishPartFoodList;

    private double error;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DishPart> getDishPartList() {
        return dishPartList;
    }

    public void setDishPartList(List<DishPart> dishPartList) {
        this.dishPartList = dishPartList;
    }

    public Float getTotalPro() {
        return totalPro;
    }

    public void setTotalPro(Float totalPro) {
        this.totalPro = totalPro;
    }

    public Float getTotalCho() {
        return totalCho;
    }

    public void setTotalCho(Float totalCho) {
        this.totalCho = totalCho;
    }

    public Float getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(Float totalFat) {
        this.totalFat = totalFat;
    }

    public Short getTotalEnerg() {
        return totalEnerg;
    }

    public void setTotalEnerg(Short totalEnerg) {
        this.totalEnerg = totalEnerg;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public List<DishPartFood> getDishPartFoodList() {
        return dishPartFoodList;
    }

    public void setDishPartFoodList(List<DishPartFood> dishPartFoodList) {
        this.dishPartFoodList = dishPartFoodList;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}
