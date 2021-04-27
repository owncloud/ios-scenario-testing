package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class PrivateSharePage extends SharePage {

    @iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"Sharing\"]")
    private MobileElement sharingTitle;

    @iOSXCUITFindBy(id="Add email or name")
    private MobileElement searchSharee;

    @iOSXCUITFindBy(id="Remove Recipient")
    private MobileElement removeShare;

    @iOSXCUITFindBy(id="permission-section-edit")
    private MobileElement editPermission;

    @iOSXCUITFindBy(id="permission-section-edit-create")
    private MobileElement createPermission;

    @iOSXCUITFindBy(id="permission-section-edit-change")
    private MobileElement changePermission;

    @iOSXCUITFindBy(id="permission-section-edit-delete")
    private MobileElement deletePermission;

    @iOSXCUITFindBy(id="permission-section-share")
    private MobileElement sharePermission;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Sharing\"]")
    private MobileElement backButton;

    public PrivateSharePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void searchSharee(String shareeName, String type){
        Log.log(Level.FINE, "Starts: Searching for sharee: " + shareeName + " that is a "+type);
        searchSharee.sendKeys(shareeName);
        if (type.equals("group")){
            shareeName += " (Group)";
        }
        driver.findElement(By.id(shareeName)).click();
    }

    public boolean isItemInListPrivateShares(String sharee) {
        return !driver.findElements(By.id(sharee)).isEmpty();
    }

    public void deletePrivateShare(String sharee){
        driver.findElement(By.id(sharee)).click();
        removeShare.click();
    }

    public void openPrivateShare(String sharee){
        driver.findElement(By.id(sharee)).click();
    }

    public boolean displayedPermission(String permissionName){
        return !driver.findElements(By.id(permissionName)).isEmpty();
    }

    public void switchCreate() {
        Log.log(Level.FINE, "Starts: Click create checkbox");
        createPermission.click();
    }

    public void switchChange() {
        Log.log(Level.FINE, "Starts: Click change checkbox");
        changePermission.click();
    }

    public void switchDelete() {
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        deletePermission.click();
    }

    public void switchShare() {
        Log.log(Level.FINE, "Starts: Switch share button");
        sharePermission.click();
    }

    private boolean parseIntBool(String s){
        return Boolean.parseBoolean(s);
    }

    public boolean isCreateSelected(){
        return  parseIntBool(createPermission.getAttribute("selected"));
    }

    public boolean isChangeSelected(){
        return parseIntBool(changePermission.getAttribute("selected"));
    }

    public boolean isDeleteSelected(){
        return parseIntBool(deletePermission.getAttribute("selected"));
    }

    public boolean isEditPermission(){
        return isCreateSelected() || isChangeSelected() || isDeleteSelected();
    }

    public boolean isShareEnabled(){
        return parseIntBool(sharePermission.getAttribute("selected"));
    }

    public boolean isEditEnabled(){
        return parseIntBool(editPermission.getAttribute("selected"));
    }

    public boolean isPasswordEnabled () {
        //TODO
        return true;
    }

    public void close(){
        backButton.click();
    }
}
