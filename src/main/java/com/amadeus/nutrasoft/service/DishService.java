package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.calc.DietSolver;
import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.DishDAO;
import com.amadeus.nutrasoft.dao.DishPartDAO;
import com.amadeus.nutrasoft.dao.DishPartFoodDAO;
import com.amadeus.nutrasoft.dao.FoodDAO;
import com.amadeus.nutrasoft.model.*;

import java.util.List;

import static com.amadeus.nutrasoft.calc.Macronutrient.*;
import static com.amadeus.nutrasoft.commons.Utils.valueOfPct;

public class DishService {
    private DishDAO dishDAO = new DishDAO(MyBatisSqlSession.getSqlSessionFactory());
    private DishPartDAO dishPartDAO = new DishPartDAO(MyBatisSqlSession.getSqlSessionFactory());
    private DishPartFoodDAO dishPartFoodDAO = new DishPartFoodDAO(MyBatisSqlSession.getSqlSessionFactory());
    private FoodDAO foodDAO = new FoodDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void createDish(Dish dish) {
        dishDAO.createDish(dish);

        for (DishPart dishPart : dish.getDishPartList()) {
            dishPart.setDish(dish);
            dishPartDAO.createDishPart(dishPart);

            for (DishPartFood dishPartFood : dishPart.getDishPartFoodList()) {
                dishPartFood.setDishPart(dishPart);
                dishPartFoodDAO.createDishPartFood(dishPartFood);
            }
        }
    }

    public void updateDish(Dish dish) {
        // Se elimina y luego se crea porque esta actualización es más compleja.
        dishPartFoodDAO.deleteAllDishPartFoods(dish.getId());

        dishDAO.updateDish(dish);

        for (DishPart dishPart : dish.getDishPartList()) {
            dishPart.setDish(dish);
            dishPartDAO.updateDishPart(dishPart);

            for (DishPartFood dishPartFood : dishPart.getDishPartFoodList()) {
                dishPartFood.setDishPart(dishPart);
                dishPartFoodDAO.createDishPartFood(dishPartFood);
            }
        }
    }

    public void deleteDish(int id) {
        dishPartFoodDAO.deleteAllDishPartFoods(id);
        dishPartDAO.deleteAllDishParts(id);
        dishDAO.deleteDish(id);
    }

    public Dish getDishById(int id, String lang) {
        return dishDAO.getDishById(id, lang);
    }

    public List<Dish> getDishesByCoachId(int coachId) {
        return dishDAO.getDishesByCoachId(coachId);
    }

    public void calculateDish(CalcDishParam calcDishParam) {
        PlanDay planDay = calcDishParam.getPlanDay();

        final short energIntake = planDay.getEnergIntake();
        final byte proEnergPct = planDay.getProEnergPct();
        final byte choEnergPct = planDay.getChoEnergPct();

        final short proEnerg = (short) valueOfPct(proEnergPct, energIntake);
        final short choEnerg = (short) valueOfPct(choEnergPct, energIntake);
        final short fatEnerg = (short) (energIntake - (proEnerg + choEnerg));

        final float pro = (float) PRO.weight(proEnerg);
        final float cho = (float) CHO.weight(choEnerg);
        final float fat = (float) FAT.weight(fatEnerg);

        planDay.setPro(pro);
        planDay.setCho(cho);
        planDay.setFat(fat);

        Preference preference = new Preference();
        preference.setMealCount((byte) 1);

        // Obtiene la información nutricional de cada food.
        Dish dish = calcDishParam.getDish();
        for (DishPart dishPart : dish.getDishPartList()) {
            for (DishPartFood dishPartFood : dishPart.getDishPartFoodList()) {
                Food food = foodDAO.getFoodByNdbno(dishPartFood.getFood().getNdbno(), calcDishParam.getLang());
                dishPartFood.setFood(food);
            }
        }

        try {
            DietSolver.solve(planDay, preference, dish);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
