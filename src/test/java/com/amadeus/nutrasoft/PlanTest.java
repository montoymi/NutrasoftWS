package com.amadeus.nutrasoft;

import com.amadeus.nutrasoft.model.*;
import com.amadeus.nutrasoft.rest.PlanResource;
import com.amadeus.nutrasoft.service.PlanService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new MyResourceConfig();
    }

    @Override
    protected void configureClient(ClientConfig config) {
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri("http://localhost:8080").path("nutrasoft-ws").build();
    }

    @Test
    public void createPlan() {
        Plan plan = getPlan();
        PlanService planService = new PlanService();
        planService.createPlan(plan);
    }

    private Plan getPlan() {
        List<PlanDay> planDayList = new ArrayList<>();
        List<PlanDayActivity> planDayActivityList = new ArrayList<>();

        PlanDayActivity planDayActivity;
        PlanDay planDay;
        Activity activity;

        Plan plan = new Plan();
        plan.setBeginDate(new Date());
        plan.setEndDate(new Date());
        plan.setHeight(1.75F);
        plan.setNeck(40F);
        plan.setWaist(80F);
        plan.setHip(90F);
        plan.setWeight(72F);
        plan.setHrMax((short) 178);
        plan.setChoEnergPct((byte) 60);
        plan.setEnergVariationPct((byte) 0);

        User client = new User();
        client.setId(7);
        client.setGender("M");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1976, 7, 14);
        client.setBirthdate(calendar.getTime());
        plan.setClient(client);

        Item goal = new Item();
        goal.setId(1);
        plan.setGoal(goal);

        // 1. Inicio. Actividades Lunes.

        planDay = new PlanDay();
        planDay.setDay((byte) 1);
        planDay.setChoEnergPct(plan.getChoEnergPct());

        // Dormir.
        activity = new Activity();
        activity.setId(1);
        activity.setMalePar(1F);
        activity.setFemalePar(1F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(8F);
        planDayActivityList.add(planDayActivity);

        // Vestirse.
        activity = new Activity();
        activity.setId(5);
        activity.setMalePar(2.4F);
        activity.setFemalePar(3.3F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        // Comer y beber.
        activity = new Activity();
        activity.setId(8);
        activity.setMalePar(1.4F);
        activity.setFemalePar(1.6F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        // Pelar vegetales.
        activity = new Activity();
        activity.setId(40);
        activity.setMalePar(1.9F);
        activity.setFemalePar(1.5F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        // Sentado en el escritorio.
        activity = new Activity();
        activity.setId(152);
        activity.setMalePar(1.3F);
        //activity.setFemalePar(1.5F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(8F);
        planDayActivityList.add(planDayActivity);

        // Tareas domésticas (sin especificar).
        activity = new Activity();
        activity.setId(48);
        //activity.setMalePar(1.3F);
        activity.setFemalePar(2.8F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        // Conducir automóvil/camión.
        activity = new Activity();
        activity.setId(19);
        activity.setMalePar(2.0F);
        //activity.setFemalePar(2.8F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        // Caminar cuesta abajo.
        activity = new Activity();
        activity.setId(13);
        activity.setMalePar(3.5F);
        activity.setFemalePar(3.2F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        // Ver televisión.
        activity = new Activity();
        activity.setId(169);
        activity.setMalePar(1.64F);
        activity.setFemalePar(1.72F);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        // Entrenamiento.
        activity = new Activity();
        activity.setId(190);
        planDayActivity = new PlanDayActivity();
        planDayActivity.setActivity(activity);
        planDayActivity.setTime(1F);
        planDayActivityList.add(planDayActivity);

        planDay.setPlanDayActivityList(planDayActivityList);
        planDayList.add(planDay);

        // Fin. Actividades Lunes.

        plan.setPlanDayList(planDayList);

        return plan;
    }

    @ApplicationPath("/")
    public class MyResourceConfig extends ResourceConfig {
        MyResourceConfig() {
            super(PlanResource.class);
        }
    }
}
