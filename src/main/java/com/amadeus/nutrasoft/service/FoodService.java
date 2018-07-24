package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.dao.FoodDAO;
import com.amadeus.nutrasoft.model.Food;
import com.amadeus.nutrasoft.mybatis.MyBatisSqlSession;

import java.util.List;

public class FoodService {
    private FoodDAO foodDAO = new FoodDAO(MyBatisSqlSession.getSqlSessionFactory());

    public List<Food> getAllFoods(String lang) {
        return foodDAO.getAllFoods(lang);
    }
}
