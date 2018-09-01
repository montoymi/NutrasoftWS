package com.amadeus.nutrasoft.model;

import java.util.Date;
import java.util.List;

public class Compliance {
    private Integer id;
    private Plan plan;
    private Date dietDate;
    private List<ComplianceMeal> complianceMealList;

    private Byte donePct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Date getDietDate() {
        return dietDate;
    }

    public void setDietDate(Date dietDate) {
        this.dietDate = dietDate;
    }

    public List<ComplianceMeal> getComplianceMealList() {
        return complianceMealList;
    }

    public void setComplianceMealList(List<ComplianceMeal> complianceMealList) {
        this.complianceMealList = complianceMealList;
    }

    public Byte getDonePct() {
        return donePct;
    }

    public void setDonePct(Byte donePct) {
        this.donePct = donePct;
    }
}
