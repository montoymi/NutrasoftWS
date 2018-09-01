package com.amadeus.nutrasoft;

import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.UserDAO;
import com.amadeus.nutrasoft.model.Message;
import com.amadeus.nutrasoft.model.Notification;
import com.amadeus.nutrasoft.model.User;
import com.amadeus.nutrasoft.service.NotificationService;
import com.amadeus.nutrasoft.service.UserService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class NotificationTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new MyResourceConfig();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(MultiPartFeature.class);
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri("http://localhost:8080").path("nutrasoft-ws").build();
    }

    //@Test
    public void sendNotification() {
        NotificationService notificationService = new NotificationService();
        String token = "f0Y_vb1X5sM:APA91bHSal3b-cv7TjRceA7E7Mywf_L_RrArzJTCiEKKRci5-vaZ_z1sv5WzOLzuKrnSpqB5_G-Mt9uG_Z0OjKtqi9QxczciTFlIBMpNPdKWVvX6g8vT1tze91StkcqS6fAKi8Hwz9ds";

        Message message = new Message();
        message.setTo(token);
        Notification notification = new Notification();
        notification.setTitle("Mensaje de prueba");
        notification.setBody("Hola!");
        message.setNotification(notification);
        notificationService.sendMessage(message);
    }

    //@Test
    public void sendNotification2() {
        UserDAO userDAO = new UserDAO(MyBatisSqlSession.getSqlSessionFactory());

        User coach = userDAO.getUserByEmail("renzo@gmail.com");

        User client = new User();
        client.setName("Fiorella");
        client.setCoach(coach);

        UserService userService = new UserService();
        userService.createNotificationToCoach(client);
    }

    @ApplicationPath("/")
    public class MyResourceConfig extends ResourceConfig {
    }
}
