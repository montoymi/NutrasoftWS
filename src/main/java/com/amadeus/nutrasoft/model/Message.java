package com.amadeus.nutrasoft.model;

/*
 * Protocolo HTTP de Firebase Cloud Messaging
 * https://firebase.google.com/docs/cloud-messaging/http-server-ref?hl=es-419#downstream
 */
public class Message {
    private String to;
    private Notification notification;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
