package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.config.Config;
import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.DeviceDAO;
import com.amadeus.nutrasoft.dao.NotificationDAO;
import com.amadeus.nutrasoft.model.Device;
import com.amadeus.nutrasoft.model.Message;
import com.amadeus.nutrasoft.model.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NotificationService {
    private NotificationDAO notificationDAO = new NotificationDAO(MyBatisSqlSession.getSqlSessionFactory());
    private DeviceDAO deviceDAO = new DeviceDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void createNotification(Notification notification) {
        notificationDAO.createNotification(notification);

        /*
         * Envía la notificación a todos los dispositivos del usuario.
         */

        List<Device> deviceList = deviceDAO.getDevicesByUserId(notification.getUser().getId());

        Message message = new Message();
        message.setNotification(notification);

        for (Device device : deviceList) {
            message.setTo(device.getToken());
            sendMessage(message);
        }
    }

    public List<Notification> getNotificationsByUserId(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    public void sendMessage(Message message) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());

        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            json = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        Entity entity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target.request().header("Authorization", "key=" + Config.getInstance().getServerKey()).post(entity);

        assertEquals(200, response.getStatus());

        System.out.println("Output received from web method sendMessage ....");
        System.out.println(response.readEntity(String.class));

        response.close();
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri("https://fcm.googleapis.com/fcm/send").build();
    }
}
