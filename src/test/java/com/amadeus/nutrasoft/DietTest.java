package com.amadeus.nutrasoft;

import com.amadeus.nutrasoft.commons.Utils;
import com.amadeus.nutrasoft.model.*;
import com.amadeus.nutrasoft.rest.DietResource;
import com.amadeus.nutrasoft.service.DietService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class DietTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new DietTest.MyResourceConfig();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(MultiPartFeature.class);
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri("http://localhost:8080").path("nutrasoft-ws").build();
    }

    @Test
    public void generateDiet() {
        int clientId = 2;
        byte day = 1;
        DietService dietService = new DietService();

        Menu menu = dietService.generateDiet(clientId, (byte) 1, (byte) day, "es");
        PlanDay planDay = menu.getPlanDay();

        // Requerimiento de nutrientes que debe ser cubierto en la comida.
        byte mealCount = 4;
        double pro = planDay.getPro() / mealCount;
        double cho = planDay.getCho() / mealCount;
        double fat = planDay.getFat() / mealCount;

        String goal = "\nGoalPro: " + format(pro) + " GoalCho: " + format(cho) + " GoalFat: " + format(fat);
        System.out.println(goal);

        for (MenuMeal menuMeal : menu.getMenuMealList()) {
            Dish dish = menuMeal.getDish();

            // Total por comida.
            String mealName = "\nMeal: " + menuMeal.getMeal().getName() + " - " + dish.getName();
            String error = "Error: " + dish.getError();
            String nutrients = "TotalPro: " + format(dish.getTotalPro()) + " TotalCho: " + format(dish.getTotalCho()) + " TotalFat: " + format(dish.getTotalFat());

            System.out.println(mealName);
            System.out.println(error);
            System.out.println(nutrients);
            System.out.println("---------------------------------------------");

            for (DishPartFood dishPartFood : dish.getDishPartFoodList()) {
                Food food = dishPartFood.getFood();

                // Aporte de cada alimento.
                String foodName = "Food: " + food.getNdbNo() + " - " + food.getName();
                String weight = "Weight: " + format2(dishPartFood.getWeight()) + " g - WeightPct: " + format2(dishPartFood.getWeightPct()) + "%";
                String nutrients2 = "Pro: " + format(dishPartFood.getPro()) + " Cho: " + format(dishPartFood.getCho()) + " Fat: " + format(
                        dishPartFood.getFat());

                System.out.println(nutrients2 + " - " + weight + " - " + foodName);

            }
        }
    }

    private String format(Object value) {
        return Utils.format(value, "##00.0");
    }

    private String format2(Object value) {
        return Utils.format(value, "#000.0");
    }

    @ApplicationPath("/")
    public class MyResourceConfig extends ResourceConfig {
        MyResourceConfig() {
            super(DietResource.class);
        }
    }
}
