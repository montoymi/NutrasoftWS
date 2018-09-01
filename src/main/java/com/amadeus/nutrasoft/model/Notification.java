package com.amadeus.nutrasoft.model;

import java.util.Date;

public class Notification {
    private Integer id;
    private Integer notificationTempId;
    private User user;
    private String title;
    private String body;
    private Date messageDate;

    private String sound = "Dream.ogg";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotificationTempId() {
        return notificationTempId;
    }

    public void setNotificationTempId(Integer notificationTempId) {
        this.notificationTempId = notificationTempId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}
