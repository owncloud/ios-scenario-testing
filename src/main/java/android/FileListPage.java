package android;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public class FileListPage extends CommonPage{

    private String headertext_xpath = "//*[@text='ownCloud']";
    private String rename_xpath = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[6]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView";
    private String documentstext_description = "LinearLayout-";
    private String sharebutton_id = "com.owncloud.android:id/action_share_file";
    private String fab_id = "fab_expand_menu_button";
    private String createfolder_id = "fab_mkdir";

    public FileListPage() {
        super();
    }

    public void shareAction (String itemName){
        selectItemList(itemName);
        MobileElement shareAction = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+sharebutton_id+"\");");
        actions.click(shareAction).perform();
    }

    public void createFolder(){
        waitById(5, fab_id);
        driver.findElement(MobileBy.id(fab_id)).click();
        driver.findElement(MobileBy.id(createfolder_id)).click();
    }

    public void renameAction(String itemName) throws InterruptedException{
        selectItemList(itemName);
        //REDO: chek how to click in three-dot-button.
        Thread.sleep(1000);
        TouchAction n3dot = new TouchAction(driver);
        n3dot.tap(PointOption.point(960, 102)).perform();
        //REDO: How to select an option inside the menu
        driver.findElement(MobileBy.xpath(rename_xpath)).click();
    }

    public boolean isItemInList (String itemName) {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().description(\"" + documentstext_description + itemName + "\");").isEmpty();
    }

    public boolean isHeader(){
        return !driver.findElements(By.xpath(headertext_xpath)).isEmpty();
    }

    public void selectItemList(String itemName){
        waitById(10, "new UiSelector().description(\""+documentstext_description+itemName+"\");");
        MobileElement itemInList = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\""+documentstext_description+itemName+"\");");
        actions.clickAndHold(itemInList).perform();
    }

}
