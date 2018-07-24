package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Meal;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MealDAO {
    private SqlSessionFactory sqlSessionFactory;

    public MealDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Meal> getAllMeals(String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Meal.selectAll", lang);
        } finally {
            session.close();
        }
    }
}
