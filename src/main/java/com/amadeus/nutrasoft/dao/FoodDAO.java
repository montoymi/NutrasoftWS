package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Food;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDAO {
    private SqlSessionFactory sqlSessionFactory;

    public FoodDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Food> getAllFoods(String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Food.selectAll", lang);
        } finally {
            session.close();
        }
    }

    public Food getFoodByNdbno(String ndbno, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("ndbno", ndbno);
        map.put("lang", lang);

        try {
            return session.selectOne("Food.selectByNdbno", map);
        } finally {
            session.close();
        }
    }

    public Food getAlternativeFood(int groupId, String ndbno, String lang) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("ndbno", ndbno);
        map.put("lang", lang);

        try {
            return session.selectOne("Food.selectAlternative", map);
        } finally {
            session.close();
        }
    }
}
