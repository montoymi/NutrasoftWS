package com.amadeus.nutrasoft.model;

import javax.json.bind.annotation.JsonbTransient;

public class ComplianceMeal {
    private Integer id;
    @JsonbTransient
    private Compliance compliance;
    private Meal meal;
    private boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compliance getCompliance() {
        return compliance;
    }

    public void setCompliance(Compliance compliance) {
        this.compliance = compliance;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
