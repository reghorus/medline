package by.reghor.medlinetask.util;

import by.reghor.medlinetask.exception.MedlineException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties appProps = new Properties();
    private static final String DB_FILENAME = "db.properties";

    static {

        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String dbConfigPath = rootPath + DB_FILENAME;
            File dbConfigFile = new File(dbConfigPath);
            if (!dbConfigFile.exists()) {
                throw new MedlineException(String.format("Db properties file with %s path doesn't exist",
                        dbConfigPath));
            }
            try (FileInputStream fis = new FileInputStream(dbConfigPath)) {
                appProps.load(fis);
            }
        } catch (Exception e) {
            throw new MedlineException(e);
        }
    }

    private PropertiesUtil() {

    }


    public static String getProperty(String propertyKey) {
        String propertyValue = appProps.getProperty(propertyKey);
        if (propertyValue == null) {
            throw new MedlineException(String.format("Can not find property by key %s", propertyKey));
        }
        return propertyValue;
    }
}
