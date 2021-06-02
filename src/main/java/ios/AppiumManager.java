package ios;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import utils.LocProperties;
import utils.log.Log;


public class AppiumManager {

    private static AppiumManager appiumManager;
    private static IOSDriver driver;
    private static final String driverDefect = LocProperties.getProperties().getProperty("appiumURL");
    private static final String driverURL = System.getProperty("appium");

    private AppiumManager() {
        init();
    }

    private static void init()  {

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        File app = new File(appDir, LocProperties.getProperties().getProperty("appName"));

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        if (System.getProperty("device") != null && !System.getProperty("device").isEmpty()) {
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, System.getProperty("device"));
        } else { //Will use iPhone X as default simulator
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone X");
        }
        capabilities.setCapability("udid", System.getProperty("udid"));

        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability("showXcodeLog", true);

        try {
            if (driverURL.equals(null)) {
                driver = new IOSDriver(new URL(driverURL), capabilities);
            } else {
                driver = new IOSDriver(new URL(driverDefect), capabilities);
            }
        } catch (MalformedURLException e) {
            Log.log(Level.SEVERE, "Driver could not be created: " + e.getMessage());
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static AppiumManager getManager() {
        if (appiumManager == null) {
            appiumManager = new AppiumManager();
        }
        return appiumManager;
    }

    public IOSDriver getDriver(){
        return driver;
    }
}
