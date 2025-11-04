package api.helpers.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvHelper {

    private static EnvHelper instance;
    private final Properties properties;

    private EnvHelper() {
        properties = new Properties();
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("env.properties")) {

            if (is == null) {
                throw new RuntimeException("env.properties not found in classpath");
            }

            properties.load(is);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load env.properties", e);
        }
    }

    public static EnvHelper getInstance() {
        if (instance == null) {
            synchronized (EnvHelper.class) {
                if (instance == null) {
                    instance = new EnvHelper();
                }
            }
        }
        return instance;
    }

    public String getBaseUri() {
        return properties.getProperty("api.baseUri", "http://localhost:8080");
    }

    public String getConnectionString() {
        return properties.getProperty("db.connectionString");
    }

    public String getDbLogin() {
        return properties.getProperty("db.login");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getAdminLogin() {
        return properties.getProperty("admin.login");
    }

    public String getAdminPassword() {
        return properties.getProperty("admin.password");
    }
}
