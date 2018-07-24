package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.DietType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class DietTypeDAO {
    private SqlSessionFactory sqlSessionFactory;

    public DietTypeDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<DietType> getAllDietTypes(String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("DietType.selectAll", lang);
        } finally {
            session.close();
        }
    }
}
