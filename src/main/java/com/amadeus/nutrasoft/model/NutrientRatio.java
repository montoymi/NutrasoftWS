package com.amadeus.nutrasoft.model;

public class NutrientRatio {
    private Integer id;
    private Item goal;
    private String biotype;
    private Byte proEnergPctMin;
    private Byte proEnergPctMax;
    private Byte choEnergPctMin;
    private Byte choEnergPctMax;
    private Byte fatEnergPctMin;
    private Byte fatEnergPctMax;

    private Byte proEnergPct;
    private Byte choEnergPct;
    private Byte fatEnergPct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getGoal() {
        return goal;
    }

    public void setGoal(Item goal) {
        this.goal = goal;
    }

    public String getBiotype() {
        return biotype;
    }

    public void setBiotype(String biotype) {
        this.biotype = biotype;
    }

    public Byte getProEnergPctMin() {
        return proEnergPctMin;
    }

    public void setProEnergPctMin(Byte proEnergPctMin) {
        this.proEnergPctMin = proEnergPctMin;
    }

    public Byte getProEnergPctMax() {
        return proEnergPctMax;
    }

    public void setProEnergPctMax(Byte proEnergPctMax) {
        this.proEnergPctMax = proEnergPctMax;
    }

    public Byte getChoEnergPctMin() {
        return choEnergPctMin;
    }

    public void setChoEnergPctMin(Byte choEnergPctMin) {
        this.choEnergPctMin = choEnergPctMin;
    }

    public Byte getChoEnergPctMax() {
        return choEnergPctMax;
    }

    public void setChoEnergPctMax(Byte choEnergPctMax) {
        this.choEnergPctMax = choEnergPctMax;
    }

    public Byte getFatEnergPctMin() {
        return fatEnergPctMin;
    }

    public void setFatEnergPctMin(Byte fatEnergPctMin) {
        this.fatEnergPctMin = fatEnergPctMin;
    }

    public Byte getFatEnergPctMax() {
        return fatEnergPctMax;
    }

    public void setFatEnergPctMax(Byte fatEnergPctMax) {
        this.fatEnergPctMax = fatEnergPctMax;
    }

    public Byte getProEnergPct() {
        return proEnergPct;
    }

    public void setProEnergPct(Byte proEnergPct) {
        this.proEnergPct = proEnergPct;
    }

    public Byte getChoEnergPct() {
        return choEnergPct;
    }

    public void setChoEnergPct(Byte choEnergPct) {
        this.choEnergPct = choEnergPct;
    }

    public Byte getFatEnergPct() {
        return fatEnergPct;
    }

    public void setFatEnergPct(Byte fatEnergPct) {
        this.fatEnergPct = fatEnergPct;
    }
}
