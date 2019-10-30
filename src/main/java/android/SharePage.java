package android;

import io.appium.java_client.android.AndroidDriver;

public class SharePage {

    private AndroidDriver driver;

    public SharePage(AndroidDriver driver){
        this.driver = driver;
    }

    public boolean isHeader() {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"Share\");").size() > 0;
    }
}
