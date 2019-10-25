package android;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

public class AppiumManager {

    protected AndroidDriver driver;

    public void init() throws MalformedURLException {

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath,"src/test/resources");
        File app = new File(appDir,"owncloud.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability ("platformName", "Android");
        capabilities.setCapability ("deviceName", "test");
        capabilities.setCapability ("app", app.getAbsolutePath());
        capabilities.setCapability ("appPackage", "com.owncloud.android");
        capabilities.setCapability ("appActivity", ".ui.activity.FileDisplayActivity");

        driver = new AndroidDriver (new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public AndroidDriver getDriver(){
        return driver;
    }

}
