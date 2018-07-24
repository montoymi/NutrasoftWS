package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.ExerciseZone;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ExerciseZoneDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ExerciseZoneDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<ExerciseZone> getAllExerciseZones() {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("ExerciseZone.selectAll");
        } finally {
            session.close();
        }
    }
}
