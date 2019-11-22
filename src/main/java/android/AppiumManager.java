package android;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

public class AppiumManager {

    private static AppiumManager appiumManager;
    private static AndroidDriver driver;
    private static final String driverURL = "http://127.0.0.1:4723/wd/hub";

    private AppiumManager() {
        init();
    }

    private static void init()  {

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath,"src/test/resources");
        File app = new File(appDir,"owncloud.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability ("platformName", "Android");
        capabilities.setCapability ("deviceName", "test");
        capabilities.setCapability ("app", app.getAbsolutePath());
        capabilities.setCapability ("appPackage", "com.owncloud.android");
        capabilities.setCapability ("appActivity", ".ui.activity.FileDisplayActivity");
        try {
            driver = new AndroidDriver (new URL(driverURL), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public static AppiumManager getManager() {

        //System.out.println("CREANDO MANAGER");
        if (appiumManager == null) {
            //System.out.println("APPIUM MANAGER NULO");
            appiumManager = new AppiumManager();
        } else {
            //System.out.println("APPIUM MANAGER NO NULO");
        }
        return appiumManager;
    }

    public AndroidDriver getDriver(){
        //System.out.println("DEVOLVEMOS DRIVER");
        return driver;
    }

}
