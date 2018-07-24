package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.dao.DishDAO;
import com.amadeus.nutrasoft.dao.DishPartDAO;
import com.amadeus.nutrasoft.dao.DishPartFoodDAO;
import com.amadeus.nutrasoft.dao.FoodDAO;
import com.amadeus.nutrasoft.model.Dish;
import com.amadeus.nutrasoft.model.DishPart;
import com.amadeus.nutrasoft.model.DishPartFood;
import com.amadeus.nutrasoft.mybatis.MyBatisSqlSession;

import java.util.List;

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
}
