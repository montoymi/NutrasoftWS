package com.amadeus.nutrasoft.model;

import javax.json.bind.annotation.JsonbTransient;

public class ExcludedFood {
    @JsonbTransient
    private Preference preference;
    private Food food;

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
