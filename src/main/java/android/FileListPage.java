package android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

public class FileListPage extends CommonPage{

    private String headertext_xpath = "//*[@text='ownCloud']";
    private String documentstext_description = "LinearLayout-";
    private String sharebutton_id = "com.owncloud.android:id/action_share_file";
    private String fab_id = "fab_expand_menu_button";
    private String createfolder_id = "fab_mkdir";

    public FileListPage(AndroidDriver driver) {
        super(driver);
    }

    public void shareAction (String itemName){
        //Actions needed to longpress
        WebElement itemInList =
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\""+documentstext_description+itemName+"\");");
        actions.clickAndHold(itemInList).perform();
        WebElement shareAction =
                driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+sharebutton_id+"\");");
        actions.click(shareAction).perform();
    }

    public void createFolder(){
        driver.findElement(By.id(fab_id)).click();
        driver.findElement(By.id(createfolder_id)).click();
    }

    public boolean isItemInList (String itemName) {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().description(\""+documentstext_description+itemName+"\");").size() > 0;
    }

    public boolean isHeader(){
        return driver.findElements(By.xpath(headertext_xpath)).size() > 0;
    }

}
