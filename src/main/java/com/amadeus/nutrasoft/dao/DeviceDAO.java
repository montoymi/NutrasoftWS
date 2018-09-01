package com.amadeus.nutrasoft.dao;

import com.amadeus.nutrasoft.model.Device;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class DeviceDAO {
    private SqlSessionFactory sqlSessionFactory;

    public DeviceDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createDevice(Device device) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("Device.insert", device);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void updateDevice(Device device) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.update("Device.update", device);
            session.commit();
        } finally {
            session.close();
        }
    }

    public List<Device> getDevicesByUserId(int userId) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            return session.selectList("Device.selectByUserId", userId);
        } finally {
            session.close();
        }
    }
}
