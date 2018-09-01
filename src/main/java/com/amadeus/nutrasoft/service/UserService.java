package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.calc.Calc;
import com.amadeus.nutrasoft.config.MyBatisSqlSession;
import com.amadeus.nutrasoft.dao.NotificationTempDAO;
import com.amadeus.nutrasoft.dao.PreferenceDAO;
import com.amadeus.nutrasoft.dao.UserDAO;
import com.amadeus.nutrasoft.exception.ApplicationException;
import com.amadeus.nutrasoft.model.DietType;
import com.amadeus.nutrasoft.model.Notification;
import com.amadeus.nutrasoft.model.Preference;
import com.amadeus.nutrasoft.model.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static com.amadeus.nutrasoft.commons.FileUtils.readFile;
import static com.amadeus.nutrasoft.constants.Constants.*;
import static com.amadeus.nutrasoft.exception.ApplicationException.INACTIVE_USER;
import static com.amadeus.nutrasoft.exception.ApplicationException.NO_APPROVED_COACH;

public class UserService {
    private UserDAO userDAO = new UserDAO(MyBatisSqlSession.getSqlSessionFactory());
    private PreferenceDAO preferenceDAO = new PreferenceDAO(MyBatisSqlSession.getSqlSessionFactory());
    private NotificationTempDAO notificationTempDAO = new NotificationTempDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void createUser(User user) {
        user.setPhoto(readFile(user.getPhotoName()));
        userDAO.createUser(user);
        // No se retorna la imagen para no afectar el rendimiento.
        user.setPhoto(null);

        if (user.getUserType().equals(USER_TYPE_CLIENT)) {
            // Se crean las preferencias por defecto.
            Preference preference = new Preference();
            preference.setUser(user);
            preference.setMealCount(MEAL_COUNT_DEFAULT);
            DietType dietType = new DietType();
            dietType.setId(DIET_TYPE_DEFAULT);
            preference.setDietType(dietType);
            preferenceDAO.createPreference(preference);

            createNotificationToCoach(user);
        }
    }

    public void updateUser(User user) {
        user.setPhoto(readFile(user.getPhotoName()));
        userDAO.updateUser(user);
        // No se retorna la imagen para no afectar el rendimiento.
        user.setPhoto(null);
    }

    public User getUserByEmail(String email) {
        User user = userDAO.getUserByEmail(email);
        if (user.getStatus() == USER_STATUS_INACTIVE) {
            throw new ApplicationException(INACTIVE_USER);
        } else if (user.getUserType().equals(USER_TYPE_COACH) && user.getStatus() != USER_STATUS_APPROVED) {
            throw new ApplicationException(NO_APPROVED_COACH);
        }

        return userDAO.getUserByEmail(email);
    }

    public List<User> getAllCoaches() {
        return userDAO.getAllCoaches();
    }

    public List<User> getClientsByCoachId(int coachId) {
        List<User> userList = userDAO.getClientsByCoachId(coachId);

        for (User user : userList) {
            float hrMax = Calc.hrMax(Calc.age(user.getBirthdate()));
            user.setHrMax((short) hrMax);
        }

        return userList;
    }

    public InputStream getPhotoByUserId(int id) {
        // Obtiene la foto.
        User user = userDAO.getUserById(id);
        return user.getPhoto() != null ? new ByteArrayInputStream(user.getPhoto()) : null;
    }

    public void createNotificationToCoach(User client) {
        User coach = client.getCoach();

        // Obtiene la plantilla de notificación de la nuevo cliente.
        Notification notification = notificationTempDAO.getNotificationTempById(1, coach.getPreferredLang());
        notification.setNotificationTempId(notification.getId());
        notification.setUser(coach);
        // Crea el mensaje personalizado.
        // Hola %s, %s se ha registrado en Performance Nutrition. Tu serás su entrenador.
        String body = String.format(notification.getBody(), coach.getName(), client.getName());
        notification.setBody(body);

        NotificationService notificationService = new NotificationService();
        notificationService.createNotification(notification);
    }
}
