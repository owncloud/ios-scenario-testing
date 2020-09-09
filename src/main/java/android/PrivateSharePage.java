package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class PrivateSharePage extends CommonPage {

    @AndroidFindBy(id="canEditCreateCheckBox")
    private MobileElement createPermission;

    @AndroidFindBy(id="canEditChangeCheckBox")
    private MobileElement editPermission;

    @AndroidFindBy(id="canEditDeleteCheckBox")
    private MobileElement deletePermission;

    @AndroidFindBy(id="canShareSwitch")
    private MobileElement sharePermission;

    public PrivateSharePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void switchCreate() {
        boolean status = isCreateSelected();
        Log.log(Level.FINE, "Starts: Click create checkbox");
        createPermission.click();
        waitTillStatus(5, createPermission, !status);
    }

    public void switchChange() {
        boolean status = isChangeSelected();
        Log.log(Level.FINE, "Starts: Click change checkbox");
        editPermission.click();
        waitTillStatus(5, editPermission, !status);
    }

    public void switchDelete() {
        boolean status = isDeleteSelected();
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        deletePermission.click();
        waitTillStatus(5, deletePermission, !status);
    }

    public void switchShare() {
        boolean status = isShareEnabled();
        Log.log(Level.FINE, "Starts: Switch share button");
        sharePermission.click();
        waitTillStatus(5, sharePermission, !status);
    }

    public boolean isCreateSelected(){
        return createPermission.isEnabled();
    }

    public boolean isChangeSelected(){
        return editPermission.isEnabled();
    }

    public boolean isDeleteSelected(){
        return deletePermission.isEnabled();
    }

    public boolean isShareEnabled(){
        return sharePermission.isEnabled();
    }

    public boolean isPasswordEnabled () {
        return true;
    }

    public void close(){
        takeScreenshot("PrivateShare/ItemStatusBeforeClosing");
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}
