package android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.android.AndroidDriver;

public class FileListPage {

    private AndroidDriver driver;
    private Actions actions;
    private String headertext_xpath = "//*[@text='ownCloud']";
    private String sharebutton_id = "action_share_file";
    private String documentstext_description = "LinearLayout-Documents";

    public FileListPage(AndroidDriver driver) {
        this.driver = driver;
        actions = new Actions(driver);
    }

    public void longPress (){
        WebElement element =
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\""+documentstext_description+"\");");
        actions.clickAndHold(element).perform();
    }

    public void shareAction (){
        WebElement element = driver.findElement(By.id(sharebutton_id));
        actions.click(element).perform();
    }

    public boolean isHeader(){
        return driver.findElements(By.xpath(headertext_xpath)).size() > 0;
    }

}
