package android;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.log.Log;

public class PrivateSharePage extends CommonPage {

    private String createbox_id = "canEditCreateCheckBox";
    private String changebox_id = "canEditChangeCheckBox";
    private String deletebox_id = "canEditDeleteCheckBox";
    private String sharebox_id = "canShareSwitch";

    public PrivateSharePage(){
        super();
    }

    public void switchCreate() {
        boolean status = isCreateSelected();
        Log.log(Level.FINE, "Starts: Click create checkbox");
        driver.findElement(MobileBy.id(createbox_id)).click();
        waitTillStatus(5, createbox_id, !status);
    }

    public void switchChange() {
        boolean status = isChangeSelected();
        Log.log(Level.FINE, "Starts: Click change checkbox");
        driver.findElement(MobileBy.id(changebox_id)).click();
        waitTillStatus(5, changebox_id, !status);
    }

    public void switchDelete() {
        boolean status = isDeleteSelected();
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        driver.findElement(MobileBy.id(deletebox_id)).click();
        waitTillStatus(5, deletebox_id, !status);
    }

    public void switchShare() {
        boolean status = isShareEnabled();
        Log.log(Level.FINE, "Starts: Switch share button");
        driver.findElement(MobileBy.id(sharebox_id)).click();
        waitTillStatus(5, sharebox_id, !status);
    }

    public boolean isCreateSelected(){
        return driver.findElement(MobileBy.id(createbox_id)).isEnabled();
    }

    public boolean isChangeSelected(){
        return driver.findElement(MobileBy.id(changebox_id)).isEnabled();
    }

    public boolean isDeleteSelected(){
        return driver.findElement(MobileBy.id(deletebox_id)).isEnabled();
    }

    public boolean isShareEnabled(){
        return driver.findElement(MobileBy.id(sharebox_id)).isEnabled();
    }

    public boolean isPasswordEnabled () {
        return true;
    }

    public void close(){
        takeScreenshot("PrivateShare/ItemStatusBeforeClosing");
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}
