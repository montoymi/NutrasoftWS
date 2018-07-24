package com.amadeus.nutrasoft.model;

import javax.json.bind.annotation.JsonbTransient;
import java.util.List;

public class DishPart {
    private Integer id;
    @JsonbTransient
    private Dish dish;
    private String partCode;
    private List<DishPartFood> dishPartFoodList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public List<DishPartFood> getDishPartFoodList() {
        return dishPartFoodList;
    }

    public void setDishPartFoodList(List<DishPartFood> dishPartFoodList) {
        this.dishPartFoodList = dishPartFoodList;
    }
}
