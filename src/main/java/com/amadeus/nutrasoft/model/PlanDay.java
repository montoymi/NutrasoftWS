package com.amadeus.nutrasoft.model;

import javax.json.bind.annotation.JsonbTransient;
import java.util.List;

public class PlanDay {
    private Integer id;
    @JsonbTransient
    private Plan plan;
    private Byte day;
    private Short energExpend;
    private Short energIntake;
    private String macrosRatioType;
    private Byte proEnergPct;
    private Byte choEnergPct;
    private Byte fatEnergPct;
    private Float pro;
    private Float cho;
    private Float fat;
    private Float proBodywt;
    private List<PlanDayActivity> planDayActivityList;

    private String dayName;
    private Boolean checked;
    private Byte totalHours;
    private Boolean showDetails;
    private String icon;
    private Short proEnerg;
    private Short choEnerg;
    private Short fatEnerg;

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

    public Byte getDay() {
        return day;
    }

    public void setDay(Byte day) {
        this.day = day;
    }

    public Short getEnergExpend() {
        return energExpend;
    }

    public void setEnergExpend(Short energExpend) {
        this.energExpend = energExpend;
    }

    public Short getEnergIntake() {
        return energIntake;
    }

    public void setEnergIntake(Short energIntake) {
        this.energIntake = energIntake;
    }

    public String getMacrosRatioType() {
        return macrosRatioType;
    }

    public void setMacrosRatioType(String macrosRatioType) {
        this.macrosRatioType = macrosRatioType;
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

    public Float getPro() {
        return pro;
    }

    public void setPro(Float pro) {
        this.pro = pro;
    }

    public Float getCho() {
        return cho;
    }

    public void setCho(Float cho) {
        this.cho = cho;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getProBodywt() {
        return proBodywt;
    }

    public void setProBodywt(Float proBodywt) {
        this.proBodywt = proBodywt;
    }

    public List<PlanDayActivity> getPlanDayActivityList() {
        return planDayActivityList;
    }

    public void setPlanDayActivityList(List<PlanDayActivity> planDayActivityList) {
        this.planDayActivityList = planDayActivityList;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Byte getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Byte totalHours) {
        this.totalHours = totalHours;
    }

    public Boolean getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(Boolean showDetails) {
        this.showDetails = showDetails;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Short getProEnerg() {
        return proEnerg;
    }

    public void setProEnerg(Short proEnerg) {
        this.proEnerg = proEnerg;
    }

    public Short getChoEnerg() {
        return choEnerg;
    }

    public void setChoEnerg(Short choEnerg) {
        this.choEnerg = choEnerg;
    }

    public Short getFatEnerg() {
        return fatEnerg;
    }

    public void setFatEnerg(Short fatEnerg) {
        this.fatEnerg = fatEnerg;
    }
}
