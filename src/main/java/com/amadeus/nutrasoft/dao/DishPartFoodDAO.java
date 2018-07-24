package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.DishPartFood;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DishPartFoodDAO {
    private SqlSessionFactory sqlSessionFactory;

    public DishPartFoodDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createDishPartFood(DishPartFood dishPartFood) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("DishPartFood.insert", dishPartFood);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteAllDishPartFoods(int dishId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("DishPartFood.deleteAll", dishId);
            session.commit();
        } finally {
            session.close();
        }
    }
}
