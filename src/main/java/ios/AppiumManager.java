/******
 *
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 *
 * Last Appium review: v2.2.1
 * If posible, execute tests with such version
 */

package ios;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import utils.LocProperties;
import utils.log.Log;

public class AppiumManager {

    private static AppiumManager appiumManager;
    private static IOSDriver driver;
    private static final String driverDefect = LocProperties.getProperties().getProperty("appiumURL");
    private static final String driverURL = System.getProperty("appium");
    private static File app;

    private AppiumManager() {
        init();
    }

    private static void init() {

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        app = new File(appDir, LocProperties.getProperties().getProperty("appName"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        setCapabilities(capabilities);

        try {
            if (!driverURL.isEmpty()) {
                Log.log(Level.FINE, "Appium driver located in: " + driverURL);
                driver = new IOSDriver(new URL(driverURL), capabilities);
            } else {
                Log.log(Level.FINE, "Appium driver located in: " + driverDefect);
                driver = new IOSDriver(new URL(driverDefect), capabilities);
            }
        } catch (MalformedURLException e) {
            Log.log(Level.SEVERE, "Driver could not be created: " + e.getMessage());
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
    }

    public static AppiumManager getManager() {
        if (appiumManager == null) {
            appiumManager = new AppiumManager();
        }
        return appiumManager;
    }

    public IOSDriver getDriver() {
        return driver;
    }

    //Check https://appium.github.io/appium-xcuitest-driver/latest/reference/capabilities/
    private static void setCapabilities(DesiredCapabilities capabilities) {

        capabilities.setCapability("appium:platformName", "iOS");

        if (System.getProperty("device") != null && !System.getProperty("device").isEmpty()) {
            capabilities.setCapability("appium:deviceName", System.getProperty("device"));
        } else {
            capabilities.setCapability("appium:deviceName", "iPhone 16");
        }

        capabilities.setCapability("udid", System.getProperty("udid"));

        capabilities.setCapability("appium:app", app.getAbsolutePath());

        capabilities.setCapability("appium:automationName", AutomationName.IOS_XCUI_TEST);

        capabilities.setCapability("showXcodeLog", true);

        //The following capabilities prevents reinstalling the app every test.
        capabilities.setCapability("appium:fullReset", false);

        capabilities.setCapability("appium:noReset", false);

        capabilities.setCapability("appium:newCommandTimeout", 60);

        capabilities.setCapability("platformVersion", "18.2");

        capabilities.setCapability("appium:useNewWDA", false);

    }
}
