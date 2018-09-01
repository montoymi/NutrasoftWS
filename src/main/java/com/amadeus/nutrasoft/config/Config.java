package com.amadeus.nutrasoft.config;

import org.apache.ibatis.io.Resources;

import java.util.Properties;

public class Config {
    private static Config instance;
    private String uploadPath;
    private String serverKey;

    private Config() {
        try {
            Properties properties = Resources.getResourceAsProperties("config.properties");
            uploadPath = properties.getProperty("UPLOAD_PATH");
            serverKey = properties.getProperty("SERVER_KEY");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getServerKey() {
        return serverKey;
    }
}
