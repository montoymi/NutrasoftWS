package com.amadeus.nutrasoft.model;

import javax.json.bind.annotation.JsonbTransient;

public class PlanDayActivity {
    private Integer id;
    @JsonbTransient
    private PlanDay planDay;
    private Activity activity;
    private Float time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlanDay getPlanDay() {
        return planDay;
    }

    public void setPlanDay(PlanDay planDay) {
        this.planDay = planDay;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }
}
