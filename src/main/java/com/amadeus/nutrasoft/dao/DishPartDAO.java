package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.DishPart;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DishPartDAO {
    private SqlSessionFactory sqlSessionFactory;

    public DishPartDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createDishPart(DishPart dishPart) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("DishPart.insert", dishPart);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateDishPart(DishPart dishPart) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("DishPart.update", dishPart);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteAllDishParts(int dishId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("DishPart.deleteAll", dishId);
            session.commit();
        } finally {
            session.close();
        }
    }
}
