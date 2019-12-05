package android;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class CommonPage {

    protected AndroidDriver driver;
    protected Actions actions;

    public CommonPage()  {
        AppiumManager manager = AppiumManager.getManager();
        driver = manager.getDriver();
        actions = new Actions(driver);
    }

    public static void waitByXpath(int timeToWait, String resourceId){
        AndroidDriver driver = AppiumManager.getManager().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(resourceId)));
    }

    public static void waitById(int timeToWait, String resourceId){
        AndroidDriver driver = AppiumManager.getManager().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(resourceId)));
    }

    protected MobileElement matchByText(String text){
        MobileElement selection = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+ text +"\");");
        return selection;
    }

    protected MobileElement matchById(String text){
        MobileElement selection = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+ text +"\");");
        return selection;
    }

}