package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Weight;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightDAO {
    private SqlSessionFactory sqlSessionFactory;

    public WeightDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Weight> getWeightsByNdbno(String ndbno, String lang) {
        List<Weight> list;
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("ndbno", ndbno);
        map.put("lang", lang);

        try {
            list = session.selectList("Weight.selectByNdbno", map);
        } finally {
            session.close();
        }

        return list;
    }
}
