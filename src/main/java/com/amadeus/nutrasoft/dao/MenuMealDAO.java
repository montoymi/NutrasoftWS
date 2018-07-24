package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.MenuMeal;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MenuMealDAO {
    private SqlSessionFactory sqlSessionFactory;

    public MenuMealDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createMenuMeal(MenuMeal menuMeal) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("MenuMeal.insert", menuMeal);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateMenuMeal(MenuMeal menuMeal) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("MenuMeal.update", menuMeal);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteAllMenuMeals(int menuId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("MenuMeal.deleteAll", menuId);
            session.commit();
        } finally {
            session.close();
        }
    }
}
