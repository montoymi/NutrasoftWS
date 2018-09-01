package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.MealDAO;
import com.amadeus.nutrasoft.dao.MenuDAO;
import com.amadeus.nutrasoft.dao.MenuMealDAO;
import com.amadeus.nutrasoft.model.*;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO = new MenuDAO(MyBatisSqlSession.getSqlSessionFactory());
    private MenuMealDAO menuMealDAO = new MenuMealDAO(MyBatisSqlSession.getSqlSessionFactory());
    private MealDAO mealDAO = new MealDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void createMenu(Menu menu) {
        menuDAO.createMenu(menu);

        for (MenuMeal menuMeal : menu.getMenuMealList()) {
            menuMeal.setMenu(menu);
            menuMealDAO.createMenuMeal(menuMeal);
        }
    }

    public void updateMenu(Menu menu) {
        menuDAO.updateMenu(menu);

        for (MenuMeal menuMeal : menu.getMenuMealList()) {
            menuMeal.setMenu(menu);
            menuMealDAO.updateMenuMeal(menuMeal);
        }
    }

    public void deleteMenu(int id) {
        menuMealDAO.deleteAllMenuMeals(id);
        menuDAO.deleteMenu(id);
    }

    public Menu getNextMenuByCoachId(int coachId, int dietTypeId) {
        Menu menu = menuDAO.getLastMenuByCoachId(coachId, dietTypeId);

        if (menu == null) {
            menu = new Menu();

            User coach = new User();
            coach.setId(coachId);
            menu.setCoach(coach);

            DietType dietType = new DietType();
            dietType.setId(dietTypeId);
            menu.setDietType(dietType);

            menu.setWeek((byte) 1);
            menu.setDay((byte) 1);
        } else {
            if (menu.getDay() < 7) {
                menu.setDay((byte) (menu.getDay() + 1));
            } else {
                menu.setWeek((byte) 1);
                menu.setDay((byte) 1);
            }
        }

        return menu;
    }

    public Menu getMenuById(int id, String lang) {
        return menuDAO.getMenuById(id, lang);
    }

    public List<DietType> getDietTypesByCoachId(int coachId, String lang) {
        return menuDAO.getDietTypesByCoachId(coachId, lang);
    }

    public List<Meal> getAllMeals(String lang) {
        return mealDAO.getAllMeals(lang);
    }
}
