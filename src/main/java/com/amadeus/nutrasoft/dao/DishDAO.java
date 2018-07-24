package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Dish;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishDAO {
    private SqlSessionFactory sqlSessionFactory;

    public DishDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createDish(Dish dish) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Dish.insert", dish);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateDish(Dish dish) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Dish.update", dish);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteDish(int id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Dish.delete", id);
            session.commit();
        } finally {
            session.close();
        }
    }

    public Dish getDishById(int id, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("lang", lang);

        try {
            return session.selectOne("Dish.selectById", map);
        } finally {
            session.close();
        }
    }

    public List<Dish> getDishesByCoachId(int coachId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Dish.selectByCoachId", coachId);
        } finally {
            session.close();
        }
    }
}
