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
    private String acceptdeletion_id = "android:id/button1";
    private String canceldeletion_id = "android:id/button3";

    public PrivateSharePage(){
        super();
    }

    public void switchCreate() {
        Log.log(Level.FINE, "Starts: Click create checkbox");
        driver.findElement(MobileBy.id(createbox_id)).click();
    }

    public void switchChange() {
        Log.log(Level.FINE, "Starts: Click change checkbox");
        driver.findElement(MobileBy.id(changebox_id)).click();
    }

    public void switchDelete() {
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        driver.findElement(MobileBy.id(deletebox_id)).click();
    }

    public void switchShare() {
        Log.log(Level.FINE, "Starts: Switch share button");
        driver.findElement(MobileBy.id(sharebox_id)).click();
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

    public void acceptDeletion(){
        driver.findElement(MobileBy.id(acceptdeletion_id)).click();
    }

    public void cancelDeletion(){
        driver.findElement(MobileBy.id(canceldeletion_id)).click();
    }

    public void closeView(){
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}
