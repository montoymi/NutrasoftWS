package com.amadeus.nutrasoft.model;

import java.util.List;

public class Activity {
    private Integer id;
    private String name;
    private Float malePar;
    private Float femalePar;
    private List<Activity> activityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMalePar() {
        return malePar;
    }

    public void setMalePar(Float malePar) {
        this.malePar = malePar;
    }

    public Float getFemalePar() {
        return femalePar;
    }

    public void setFemalePar(Float femalePar) {
        this.femalePar = femalePar;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
}
