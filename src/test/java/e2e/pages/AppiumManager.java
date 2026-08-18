/******
 *
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 *
 * Last Appium review: v2.2.1
 * If posible, execute tests with such version
 */

package e2e.pages;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import utils.LocProperties;
import e2e.support.log.Log;

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

        XCUITestOptions options = buildOptions();

        try {
            if (!driverURL.isEmpty()) {
                Log.log(Level.FINE, "Appium driver located in: " + driverURL);
                driver = new IOSDriver(new URL(driverURL), options);
            } else {
                Log.log(Level.FINE, "Appium driver located in: " + driverDefect);
                driver = new IOSDriver(new URL(driverDefect), options);
            }
        } catch (MalformedURLException e) {
            Log.log(Level.SEVERE, "Driver could not be created: " + e.getMessage());
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
    private static XCUITestOptions buildOptions() {
        XCUITestOptions options = new XCUITestOptions();

        if (System.getProperty("device") != null && !System.getProperty("device").isEmpty()) {
            options.setDeviceName(System.getProperty("device"));
        } else {
            options.setDeviceName("iPhone 17e");
        }

        options.setUdid(System.getProperty("udid"));
        options.setApp(app.getAbsolutePath());
        options.setAutomationName(AutomationName.IOS_XCUI_TEST);
        options.setShowXcodeLog(true);
        options.setFullReset(false);
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(60));
        options.setCapability("appium:commandTimeouts", 5000);
        options.setPlatformVersion("26.4");
        options.setCapability("appium:useNewWDA", false);
        options.setCapability("appium:autoFillPasswords", false);

        return options;
    }
}
