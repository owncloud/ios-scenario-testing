package android;

import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.android.AndroidDriver;

public class CommonPage {

    protected AndroidDriver driver;
    protected Actions actions;

    public CommonPage(AndroidDriver driver){
        this.driver = driver;
        actions = new Actions(driver);
    }
}
