package com.amadeus.nutrasoft.model;

public class Nutrient {
    public static final String PROCNT = "PROCNT";
    public static final String CHOCDF = "CHOCDF";
    public static final String FAT = "FAT";

    private String nutrNo;
    private String name;
    private String tagName;

    public String getNutrNo() {
        return nutrNo;
    }

    public void setNutrNo(String nutrNo) {
        this.nutrNo = nutrNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
