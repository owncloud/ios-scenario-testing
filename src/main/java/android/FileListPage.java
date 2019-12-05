package android;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class FileListPage extends CommonPage{

    private String headertext_xpath = "//*[@text='ownCloud']";
    private String sharebutton_id = "com.owncloud.android:id/action_share_file";
    private String closeselection_id = "com.owncloud.android:id/action_mode_close_button";
    private String fab_id = "fab_expand_menu_button";
    private String createfolder_id = "fab_mkdir";

    public FileListPage() {
        super();
    }

    public void createFolder(){
        waitById(5, fab_id);
        driver.findElement(MobileBy.id(fab_id)).click();
        driver.findElement(MobileBy.id(createfolder_id)).click();
    }

    public void shareAction (String itemName){
        selectItemList(itemName);
        actions.click(matchById(sharebutton_id)).perform();
    }

    public void renameAction(String itemName) {
        selectItemList(itemName);
        openOptions();
        selectOperation("Rename");
    }

    public void moveAction(String itemName) {
        selectItemList(itemName);
        openOptions();
        selectOperation("Move");
    }
    public void copyAction(String itemName) {
        selectItemList(itemName);
        openOptions();
        selectOperation("Copy");
    }

    public void deleteAction(String itemName) {
        selectItemList(itemName);
        openOptions();
        selectOperation("Remove");
    }

    public boolean isItemInList (String itemName) {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"" + itemName + "\");").isEmpty();
    }

    public boolean isHeader(){
        return !driver.findElements(By.xpath(headertext_xpath)).isEmpty();
    }

    public void selectItemList(String itemName) {
        matchByText(itemName);
        actions.clickAndHold(matchByText(itemName)).perform();
    }

    public void selectOperation(String operation) {
        actions.click(matchByText(operation)).perform();
    }

    public void browse(String folderName){
        actions.click(matchByText(folderName)).perform();
    }

    public void closeSelectionMode(){
        MobileElement backArrow = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+closeselection_id+"\");");
        actions.click(backArrow).perform();
    }

    private void openOptions(){
        MobileElement threeDotButton = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"More options\");");
        actions.click(threeDotButton).perform();
    }
}
