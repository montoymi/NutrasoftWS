package com.amadeus.nutrasoft.model;

public class Measurement {
    private User client;
    private Float height;
    private Float neck;
    private Float waist;
    private Float hip;
    private Float weight;
    private Short hrMax;
    private Byte fatPct;
    private Byte musclePct;
    private Byte waterPct;
    private Byte bonePct;

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getNeck() {
        return neck;
    }

    public void setNeck(Float neck) {
        this.neck = neck;
    }

    public Float getWaist() {
        return waist;
    }

    public void setWaist(Float waist) {
        this.waist = waist;
    }

    public Float getHip() {
        return hip;
    }

    public void setHip(Float hip) {
        this.hip = hip;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Short getHrMax() {
        return hrMax;
    }

    public void setHrMax(Short hrMax) {
        this.hrMax = hrMax;
    }

    public Byte getFatPct() {
        return fatPct;
    }

    public void setFatPct(Byte fatPct) {
        this.fatPct = fatPct;
    }

    public Byte getMusclePct() {
        return musclePct;
    }

    public void setMusclePct(Byte musclePct) {
        this.musclePct = musclePct;
    }

    public Byte getWaterPct() {
        return waterPct;
    }

    public void setWaterPct(Byte waterPct) {
        this.waterPct = waterPct;
    }

    public Byte getBonePct() {
        return bonePct;
    }

    public void setBonePct(Byte bonePct) {
        this.bonePct = bonePct;
    }
}
