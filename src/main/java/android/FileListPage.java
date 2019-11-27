package android;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class FileListPage extends CommonPage{

    private String headertext_xpath = "//*[@text='ownCloud']";
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

    public void renameAction(String itemName) {
        selectItemList(itemName);
        MobileElement threeDotButton = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"More options\");");
        actions.click(threeDotButton).perform();
        selectOperation("Rename");
    }

    public void deleteAction(String itemName) {
        selectItemList(itemName);
        MobileElement threeDotButton = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"More options\");");
        actions.click(threeDotButton).perform();
        selectOperation("Remove");
    }

    public boolean isItemInList (String itemName) {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().description(\"" + documentstext_description + itemName + "\");").isEmpty();
    }

    public boolean isHeader(){
        return !driver.findElements(By.xpath(headertext_xpath)).isEmpty();
    }

    public void selectItemList(String itemName) {
        MobileElement itemInList = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\""+documentstext_description+itemName+"\");");
        actions.clickAndHold(itemInList).perform();
    }

    public void selectOperation(String operation) {
        MobileElement selection = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+ operation +"\");");
        actions.click(selection).perform();
    }

}
