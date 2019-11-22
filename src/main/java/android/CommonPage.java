package android;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class CommonPage {

    protected AndroidDriver driver;
    protected Actions actions;

    public CommonPage()  {
        AppiumManager manager = AppiumManager.getManager();
        driver = manager.getDriver();
        actions = new Actions(driver);
    }

    public static boolean wait(int timeToWait, String resourceId){
        AndroidDriver driver = AppiumManager.getManager().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        boolean isElementPresent = false;
        try{
            AndroidElement mobileElement =  (AndroidElement) driver.findElement(By.xpath(resourceId));;
            wait.until(ExpectedConditions.visibilityOf(mobileElement));
            isElementPresent = mobileElement.isDisplayed();
            return isElementPresent;
        } catch(Exception e){
            isElementPresent = false;
            System.out.println(e.getMessage());
            return isElementPresent;
        }
    }

    public static boolean waitById(int timeToWait, String resourceId){
        AndroidDriver driver = AppiumManager.getManager().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        boolean isElementPresent = false;
        try{

            AndroidElement mobileElement =  (AndroidElement) driver.findElement(MobileBy.id(resourceId));
            wait.until(ExpectedConditions.visibilityOf(mobileElement));
            isElementPresent = mobileElement.isDisplayed();
            return isElementPresent;
        } catch(Exception e){
            isElementPresent = false;
            System.out.println(e.getMessage());
            return isElementPresent;
        }
    }
}