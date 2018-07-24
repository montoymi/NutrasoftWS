package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Preference;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

public class PreferenceDAO {
    private SqlSessionFactory sqlSessionFactory;

    public PreferenceDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createPreference(Preference preference) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Preference.insert", preference);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updatePreference(Preference preference) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Preference.update", preference);
            session.commit();
        } finally {
            session.close();
        }
    }

    public Preference getPreferenceByUserId(int userId, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("lang", lang);

        try {
            return session.selectOne("Preference.selectById", map);
        } finally {
            session.close();
        }
    }
}
