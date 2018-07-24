package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.DietType;
import com.amadeus.nutrasoft.model.Menu;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuDAO {
    private SqlSessionFactory sqlSessionFactory;

    public MenuDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createMenu(Menu menu) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Menu.insert", menu);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateMenu(Menu menu) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Menu.update", menu);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteMenu(int id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Menu.delete", id);
            session.commit();
        } finally {
            session.close();
        }
    }

    public Menu getLastMenu(int coachId, int dietTypeId) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("coachId", coachId);
        map.put("dietTypeId", dietTypeId);

        try {
            return session.selectOne("Menu.selectLast", map);
        } finally {
            session.close();
        }
    }


    public Menu getMenuById(int id, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("lang", lang);

        try {
            return session.selectOne("Menu.selectById", map);
        } finally {
            session.close();
        }
    }

    public List<DietType> getDietTypesByCoachId(int coachId, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("coachId", coachId);
        map.put("lang", lang);

        try {
            return session.selectList("Menu.selectByCoachId", map);
        } finally {
            session.close();
        }
    }
}
