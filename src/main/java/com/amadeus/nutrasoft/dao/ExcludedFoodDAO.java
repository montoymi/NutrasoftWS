package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.ExcludedFood;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ExcludedFoodDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ExcludedFoodDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createExcludedFood(ExcludedFood excludedFood) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("ExcludedFood.insert", excludedFood);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteAllExcludedFoods(int userId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("ExcludedFood.deleteAll", userId);
            session.commit();
        } finally {
            session.close();
        }
    }
}

