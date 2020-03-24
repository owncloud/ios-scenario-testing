package android;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import utils.log.Log;

public class PublicLinkPage extends CommonPage {

    private String namepubliclink_id = "com.owncloud.android:id/shareViaLinkNameValue";
    private String enablePassword_id = "com.owncloud.android:id/shareViaLinkPasswordSwitch";
    private String textpassword_id = "com.owncloud.android:id/shareViaLinkPasswordValue";
    private String savebutton_id = "com.owncloud.android:id/saveButton";
    private String cancelbutton_id = "com.owncloud.android:id/cancelButton";


    public PublicLinkPage(){
        super();
    }

    public void addLinkName (String linkName) {
        Log.log(Level.FINE, "Starts: Add link name: " + linkName);
        driver.findElement(MobileBy.id(namepubliclink_id)).clear();
        driver.findElement(MobileBy.id(namepubliclink_id)).sendKeys(linkName);
    }

    public void addPassword (String password) {
        Log.log(Level.FINE, "Starts: Add link password: " + password);
        driver.findElement(MobileBy.id(enablePassword_id)).click();
        waitById(5, textpassword_id);
        driver.findElement(MobileBy.id(textpassword_id)).sendKeys(password);
    }

    public boolean isPasswordEnabled () {
        boolean switchEnabled = driver.findElement(MobileBy.id(enablePassword_id)).isEnabled();
        boolean passVisible = driver.findElement(MobileBy.id(textpassword_id)).isDisplayed();
        return switchEnabled && passVisible;
    }

    public void close(){
        Log.log(Level.FINE, "Starts: Cancel public link view");
        driver.findElement(MobileBy.id(cancelbutton_id)).click();
    }

    public void submitLink() {
        Log.log(Level.FINE, "Starts: Submit public link");
        driver.findElement(MobileBy.id(savebutton_id)).click();
    }
}
