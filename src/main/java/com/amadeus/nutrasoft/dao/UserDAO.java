package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

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

    public List<User> getUsersByUserType(String userType) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("User.selectByUserType", userType);
        } finally {
            session.close();
        }
    }
}
