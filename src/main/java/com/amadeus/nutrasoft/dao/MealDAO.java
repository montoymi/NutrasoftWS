package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Meal;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Meal> getMealsByClientId(int clientId, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("clientId", clientId);
        map.put("lang", lang);

        try {
            return session.selectList("Meal.selectByClientId", map);
        } finally {
            session.close();
        }
    }
}
