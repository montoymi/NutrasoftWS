package com.amadeus.nutrasoft.service;

import com.amadeus.nutrasoft.dao.PreferenceDAO;
import com.amadeus.nutrasoft.dao.UserDAO;
import com.amadeus.nutrasoft.model.DietType;
import com.amadeus.nutrasoft.model.Preference;
import com.amadeus.nutrasoft.model.User;
import com.amadeus.nutrasoft.mybatis.MyBatisSqlSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.amadeus.nutrasoft.constants.Constants.DIET_TYPE_DEFAULT;
import static com.amadeus.nutrasoft.constants.Constants.MEAL_COUNT_DEFAULT;

public class UserService {
    private static final String UPLOAD_PATH = "/Users/montoymi/Public/Nutrasoft/Uploads/";
    private static final String USER_TYPE_CLIENT = "CLIENT";

    private UserDAO userDAO = new UserDAO(MyBatisSqlSession.getSqlSessionFactory());
    private PreferenceDAO preferenceDAO = new PreferenceDAO(MyBatisSqlSession.getSqlSessionFactory());

    public void uploadPhoto(InputStream inputStream, String name) {
        try {
            // Crea el archivo y lo coloca en la ruta de carga.
            FileUtils.copyInputStreamToFile(inputStream, new File(getPathname(name)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public InputStream downloadPhoto(int id) {
        // Obtiene la foto.
        User user = userDAO.getUserById(id);
        return user.getPhoto() != null ? new ByteArrayInputStream(user.getPhoto()) : null;
    }

    public void createUser(User user) {
        user.setPhoto(getPhoto(user.getPhotoName()));
        userDAO.createUser(user);
        // No se retorna la imagen para no afectar el rendimiento.
        user.setPhoto(null);

        // Se crean las preferencias por defecto.
        Preference preference = new Preference();
        preference.setUser(user);
        preference.setMealCount(MEAL_COUNT_DEFAULT);
        DietType dietType = new DietType();
        dietType.setId(DIET_TYPE_DEFAULT);
        preference.setDietType(dietType);
        preferenceDAO.createPreference(preference);
    }

    public void updateUser(User user) {
        user.setPhoto(getPhoto(user.getPhotoName()));
        userDAO.updateUser(user);
        // No se retorna la imagen para no afectar el rendimiento.
        user.setPhoto(null);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public List<User> getUsersByUserType(String userType) {
        return userDAO.getUsersByUserType(userType);
    }

    /**
     * Obtiene la foto de la ruta de descarga para actualizar el objeto User.
     * Si no se envia el nombre de la foto no se actualiza la foto en la base de datos.
     */
    private byte[] getPhoto(String name) {
        if (name == null) {
            return null;
        }

        byte[] data = null;

        try {
            // Lee el archivo de la ruta de carga y luego lo elimina.
            Path path = Paths.get(getPathname(name));
            data = Files.readAllBytes(path);
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private String getPathname(String name) {
        return UPLOAD_PATH + name;
    }
}
