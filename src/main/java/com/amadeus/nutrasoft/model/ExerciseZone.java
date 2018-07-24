package com.amadeus.nutrasoft.model;

public class ExerciseZone {
    private Activity activity;
    private String code;
    private Byte hrMaxPctMin;
    private Byte hrMaxPctMax;
    private String exerciseType;
    private Float proBodywt;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getHrMaxPctMin() {
        return hrMaxPctMin;
    }

    public void setHrMaxPctMin(Byte hrMaxPctMin) {
        this.hrMaxPctMin = hrMaxPctMin;
    }

    public Byte getHrMaxPctMax() {
        return hrMaxPctMax;
    }

    public void setHrMaxPctMax(Byte hrMaxPctMax) {
        this.hrMaxPctMax = hrMaxPctMax;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public Float getProBodywt() {
        return proBodywt;
    }

    public void setProBodywt(Float proBodywt) {
        this.proBodywt = proBodywt;
    }
}