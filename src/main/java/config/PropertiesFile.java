package config;

import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesFile {
    public static String sysPath = System.getProperty("user.dir");
    public static Properties prop = new Properties();

    // Test method to get Properties from config file
    public static String getProperties(String propertyValue) {
        try {
            InputStream input = new FileInputStream(sysPath + "/src/main/java/config/config.properties");
            prop.load(input);
        } catch (Exception e) {
            Reporter.log("[ERROR] Exception Cause " + e.getMessage());
        }
        return prop.getProperty(propertyValue);
    }

    //Test method to set properties in config file
    public static void setProperties(String propertyKey, String propertyValue) {
        try {
            InputStream input = new FileInputStream(sysPath + "/src/main/java/config/config.properties");
            prop.load(input);
            prop.setProperty(propertyKey, propertyValue);

        } catch (Exception e) {
            Reporter.log("[ERROR] Exception Cause " + e.getMessage());
        }
    }
}
