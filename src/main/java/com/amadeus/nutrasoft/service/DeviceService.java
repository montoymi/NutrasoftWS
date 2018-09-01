package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.DeviceDAO;
import com.amadeus.nutrasoft.model.Device;

public class DeviceService {
    private DeviceDAO deviceDAO = new DeviceDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void createDevice(Device device) {
        deviceDAO.createDevice(device);
    }

    public void updateDevice(Device device) {
        deviceDAO.updateDevice(device);
    }
}
