package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Food;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class FoodDAO {
    private SqlSessionFactory sqlSessionFactory;

    public FoodDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Food> getAllFoods(String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Food.selectAll", lang);
        } finally {
            session.close();
        }
    }
}
