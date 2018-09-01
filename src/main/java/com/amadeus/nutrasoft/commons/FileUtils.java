package com.amadeus.nutrasoft.commons;

import com.amadeus.nutrasoft.config.Config;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static void writeFile(InputStream inputStream, String name) {
        try {
            // Crea el archivo y lo coloca en la ruta de carga.
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, new File(getPathname(name)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    public static byte[] readFile(String name) {
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

    private static String getPathname(String name) {
        return Config.getInstance().getUploadPath() + name;
    }
}
