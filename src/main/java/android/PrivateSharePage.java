package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class PrivateSharePage extends CommonPage {

    /*@AndroidFindBy(id="canEditSwitch")
    private MobileElement editPermission;

    @AndroidFindBy(id="canEditCreateCheckBox")
    private MobileElement createPermission;

    @AndroidFindBy(id="canEditChangeCheckBox")
    private MobileElement changePermission;

    @AndroidFindBy(id="canEditDeleteCheckBox")
    private MobileElement deletePermission;

    @AndroidFindBy(id="canShareSwitch")
    private MobileElement sharePermission;

    @AndroidFindBy(id="closeButton")
    private MobileElement closeButton;*/

    private String createbox_id = "com.owncloud.android:id/canEditCreateCheckBox";
    private String changebox_id = "com.owncloud.android:id/canEditChangeCheckBox";
    private String deletebox_id = "com.owncloud.android:id/canEditDeleteCheckBox";
    private String sharebox_id = "com.owncloud.android:id/canShareSwitch";
    private String closeButtonid = "com.owncloud.android:id/closeButton";

    public PrivateSharePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void switchCreate() {
        Log.log(Level.FINE, "Starts: Click create checkbox");
        waitById(5, createbox_id);
        boolean status = isCreateSelected();
        driver.findElementById(createbox_id).click();
        //createPermission.click();
    }

    public void switchChange() {
        Log.log(Level.FINE, "Starts: Click change checkbox");
        waitById(5, changebox_id);
        boolean status = isChangeSelected();
        driver.findElementById(changebox_id).click();
        //changePermission.click();
    }

    public void switchDelete() {
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        waitById(5, deletebox_id);
        boolean status = isDeleteSelected();
        driver.findElementById(deletebox_id).click();
        //deletePermission.click();
    }

    public void switchShare() {
        Log.log(Level.FINE, "Starts: Switch share button");
        driver.findElementById(sharebox_id).click();
        //sharePermission.click();
    }

    public boolean isCreateSelected(){
        if (!driver.findElementsById(createbox_id).isEmpty()) {
            return driver.findElementById(createbox_id).isEnabled();
        } else {
            return false;
        }
        //return createPermission.isEnabled();
    }

    public boolean isChangeSelected(){
        if (!driver.findElementsById(changebox_id).isEmpty()) {
            return driver.findElementById(changebox_id).isEnabled();
        } else {
            return false;
        }
        //return changePermission.isEnabled();
    }

    public boolean isDeleteSelected(){
        if (!driver.findElementsById(deletebox_id).isEmpty()) {
            return driver.findElementById(deletebox_id).isEnabled();
        } else {
            return false;
        }
        //return deletePermission.isEnabled();
    }

    public boolean isEditPermission(){
        return isCreateSelected() || isChangeSelected() || isDeleteSelected();
    }

    public boolean isShareEnabled(){
        return driver.findElementById(sharebox_id).isEnabled();
        //return sharePermission.isEnabled();
    }

    public boolean isEditEnabled(){
        return driver.findElementById(changebox_id).isEnabled();
        //return editPermission.isEnabled();
    }

    public boolean isPasswordEnabled () {
        return true;
    }

    public void close(){
        takeScreenshot("PrivateShare/ItemStatusBeforeClosing");
        driver.findElementById(closeButtonid).click();
        //closeButton.click();
        //driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}
