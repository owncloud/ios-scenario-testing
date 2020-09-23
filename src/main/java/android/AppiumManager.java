package android;

import com.google.common.collect.Lists;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import utils.LocProperties;
import utils.log.Log;

public class AppiumManager {

    private static AppiumManager appiumManager;
    private static AndroidDriver driver;
    private static final String driverURL = LocProperties.getProperties().getProperty("appiumURL");

    private AppiumManager() {
        init();
    }

    private static void init()  {

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        File app = new File(appDir, LocProperties.getProperties().getProperty("apkName"));

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability (MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability (MobileCapabilityType.DEVICE_NAME, "test");
        capabilities.setCapability (MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability ("appPackage",
                LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setCapability ("appActivity",
                "com.owncloud.android.ui.activity.SplashActivity");
        capabilities.setCapability ("appWaitPackage",
                LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setCapability ("autoGrantPermissions", true);
        capabilities.setCapability ("unicodeKeyboard", true);
        capabilities.setCapability ("resetKeyboard", true);
        capabilities.setCapability ("appWaitActivity",
                "com.owncloud.android.ui.activity.WhatsNewActivity");
        capabilities.setCapability (MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
        capabilities.setCapability ("uiautomator2ServerInstallTimeout", 60000);

        try {
            driver = new AndroidDriver (new URL(driverURL), capabilities);
        } catch (MalformedURLException e) {
            Log.log(Level.SEVERE, "Driver could not be created: " + e.getMessage());
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Log.log(Level.FINE, "Device: " +
                driver.getCapabilities().getCapability("deviceManufacturer") + " " +
                driver.getCapabilities().getCapability("deviceModel"));
        Log.log(Level.FINE, "Platform: " +
                driver.getCapabilities().getCapability("platformName") + " " +
                driver.getCapabilities().getCapability("platformVersion"));
        Log.log(Level.FINE, "API Level: " +
                driver.getCapabilities().getCapability("deviceApiLevel") + "\n");

    }

    public static AppiumManager getManager() {
        if (appiumManager == null) {
            appiumManager = new AppiumManager();
        }
        return appiumManager;
    }

    public AndroidDriver getDriver(){
        return driver;
    }

    public void cleanFolder(){
        //needed Appium with option --allow-insecure=adb_shell
        //cleaning only if folder exists
        if (folderExists("/sdcard/owncloud/")) {
            Map<String, Object> args = new HashMap<>();
            args.put("command", "rm -r");
            args.put("args", Lists.newArrayList("/sdcard/owncloud/"));
            getDriver().executeScript("mobile:shell", args);
        }
    }

    private boolean folderExists(String folderLocation){
        Map<String, Object> args = new HashMap<>();
        args.put("command",  "[ ! -d \""+ folderLocation+ "\" ] && echo 1 || echo 0");
        String response = (String) getDriver().executeScript("mobile:shell", args);
        if (response.contains("0")) {
            return true;
        } else {
            return false;
        }
    }
}
