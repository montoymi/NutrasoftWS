package com.amadeus.nutrasoft.model;

import java.util.Date;
import java.util.List;

public class Plan {
    private Integer id;
    private User client;
    private Item goal;
    private Date beginDate;
    private Date endDate;
    private Float height;
    private Float neck;
    private Float waist;
    private Float hip;
    private Float weight;
    private Short hrMax;
    private Short energBasal;
    private Byte energVariationPct;
    private Byte choEnergPct;
    private String biotype;
    private List<PlanDay> planDayList;
    private List<Compliance> complianceList;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Item getGoal() {
        return goal;
    }

    public void setGoal(Item goal) {
        this.goal = goal;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Short getEnergBasal() {
        return energBasal;
    }

    public void setEnergBasal(Short energBasal) {
        this.energBasal = energBasal;
    }

    public Byte getEnergVariationPct() {
        return energVariationPct;
    }

    public void setEnergVariationPct(Byte energVariationPct) {
        this.energVariationPct = energVariationPct;
    }

    public Byte getChoEnergPct() {
        return choEnergPct;
    }

    public void setChoEnergPct(Byte choEnergPct) {
        this.choEnergPct = choEnergPct;
    }

    public String getBiotype() {
        return biotype;
    }

    public void setBiotype(String biotype) {
        this.biotype = biotype;
    }

    public List<PlanDay> getPlanDayList() {
        return planDayList;
    }

    public void setPlanDayList(List<PlanDay> planDayList) {
        this.planDayList = planDayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Compliance> getComplianceList() {
        return complianceList;
    }

    public void setComplianceList(List<Compliance> complianceList) {
        this.complianceList = complianceList;
    }
}
