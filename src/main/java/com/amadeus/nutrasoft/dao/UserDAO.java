package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.amadeus.nutrasoft.constants.Constants.*;

public class UserDAO {
    private SqlSessionFactory sqlSessionFactory;

    public UserDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createUser(User user) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("User.insert", user);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateUser(User user) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("User.update", user);
            session.commit();
        } finally {
            session.close();
        }
    }

    public User getUserById(int id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectOne("User.selectById", id);
        } finally {
            session.close();
        }
    }

    public User getUserByEmail(String email) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectOne("User.selectByEmail", email);
        } finally {
            session.close();
        }
    }

    /**
     * Retorna los coaches aprobados.
     */
    public List<User> getAllCoaches() {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("userType", USER_TYPE_COACH);
        map.put("status", USER_STATUS_APPROVED);

        try {
            return session.selectList("User.selectByUserType", map);
        } finally {
            session.close();
        }
    }

    /**
     * Retorna los alumnos activos de un coach.
     */
    public List<User> getClientsByCoachId(int coachId) {
        SqlSession session = sqlSessionFactory.openSession();

        Map<String, Object> map = new HashMap<>();
        map.put("coachId", coachId);
        map.put("status", USER_STATUS_ACTIVE);

        try {
            return session.selectList("User.selectByCoachId", map);
        } finally {
            session.close();
        }
    }
}
