package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static String propertyReader(String filePath, String key) {
        String value = null;
        //InputStream is required while loading into properties
        try {
            FileInputStream input = new FileInputStream(filePath);
            //Object creation for property class
            Properties prop = new Properties();
            // Load a properties file
            prop.load(input);
            //getProperty will fetch the value according to the kay
            value = prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}
