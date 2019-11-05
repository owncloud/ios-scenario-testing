package android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

public class FileListPage extends CommonPage{

    private String headertext_xpath = "//*[@text='ownCloud']";
    private String documentstext_description = "LinearLayout-";
    private String sharebutton_id = "action_share_file";

    public FileListPage(AndroidDriver driver) {
        super(driver);
    }

    public void shareAction (String itemName){
        //Actions needed to longpress
        WebElement element =
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\""+documentstext_description+itemName+"\");");
        actions.clickAndHold(element).perform();
        driver.findElement(By.id(sharebutton_id)).click();
    }

    public boolean isHeader(){
        return driver.findElements(By.xpath(headertext_xpath)).size() > 0;
    }

}
