package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocProperties {

    private static Properties properties = null;

    private LocProperties() {
        try {
            properties = new Properties();
            FileInputStream inputStream = new FileInputStream("local.properties");
            properties.load(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        if (properties == null) {
                new LocProperties();
        }
        return properties;
    }

}

