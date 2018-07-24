package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Item;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ItemDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ItemDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Item> getAllGoals(String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Item.selectAllGoals", lang);
        } finally {
            session.close();
        }
    }
}
