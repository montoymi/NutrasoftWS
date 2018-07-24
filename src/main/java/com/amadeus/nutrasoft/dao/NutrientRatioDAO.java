package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.NutrientRatio;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class NutrientRatioDAO {
    private SqlSessionFactory sqlSessionFactory;

    public NutrientRatioDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public NutrientRatio getNutrientRatioByParams(NutrientRatio nutrientRatio) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectOne("NutrientRatio.selectByParams", nutrientRatio);
        } finally {
            session.close();
        }
    }
}