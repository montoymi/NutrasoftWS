package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Menu;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DietDAO {
    private SqlSessionFactory sqlSessionFactory;

    public DietDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Menu getDietByParams(Menu menu) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectOne("Diet.selectByParams", menu);
        } finally {
            session.close();
        }
    }
}