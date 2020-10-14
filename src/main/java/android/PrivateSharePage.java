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

    @AndroidFindBy(id="canEditSwitch")
    private MobileElement editPermission;

    @AndroidFindBy(id="canEditCreateCheckBox")
    private MobileElement createPermission;

    @AndroidFindBy(id="canEditChangeCheckBox")
    private MobileElement changePermission;

    @AndroidFindBy(id="canEditDeleteCheckBox")
    private MobileElement deletePermission;

    @AndroidFindBy(id="canShareSwitch")
    private MobileElement sharePermission;

    public PrivateSharePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void switchCreate() {
        Log.log(Level.FINE, "Starts: Click create checkbox");
        boolean status = isCreateSelected();
        createPermission.click();
    }

    public void switchChange() {
        Log.log(Level.FINE, "Starts: Click change checkbox");
        boolean status = isChangeSelected();
        changePermission.click();
    }

    public void switchDelete() {
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        boolean status = isDeleteSelected();
        deletePermission.click();
    }

    public void switchShare() {
        Log.log(Level.FINE, "Starts: Switch share button");
        sharePermission.click();
    }

    public boolean isCreateSelected(){
        return createPermission.isEnabled();
    }

    public boolean isChangeSelected(){
        return changePermission.isEnabled();
    }

    public boolean isDeleteSelected(){
        return deletePermission.isEnabled();
    }

    public boolean isEditPermission(){
        return isCreateSelected() || isChangeSelected() || isDeleteSelected();
    }

    public boolean isShareEnabled(){
        return sharePermission.isEnabled();
    }

    public boolean isEditEnabled(){
        return editPermission.isEnabled();
    }

    public boolean isPasswordEnabled () {
        return true;
    }

    public void close(){
        takeScreenshot("PrivateShare/ItemStatusBeforeClosing");
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}
