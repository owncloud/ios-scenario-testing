package android;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import utils.log.Log;

public class PublicLinkPage extends CommonPage {

    private String namepubliclink_id = "com.owncloud.android:id/shareViaLinkNameValue";
    private String enablePassword_id = "com.owncloud.android:id/shareViaLinkPasswordSwitch";
    private String textpassword_id = "com.owncloud.android:id/shareViaLinkPasswordValue";
    private String downloadview_id = "com.owncloud.android:id/shareViaLinkEditPermissionReadOnly";
    private String downloadviewupload_id =
            "com.owncloud.android:id/shareViaLinkEditPermissionReadAndWrite";
    private String uploadonly_id = "com.owncloud.android:id/shareViaLinkEditPermissionUploadFiles";
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
        swipe(0.50, 0.45, 0.50, 0.30);
    }

    public void setPermission (String permission) {
        Log.log(Level.FINE, "Starts: Set link permission: " + permission);
        switch (permission){
            case("1"):{
                driver.findElement(MobileBy.id(downloadview_id)).click();
                break;
            }
            case("15"):{
                driver.findElement(MobileBy.id(downloadviewupload_id)).click();
                break;
            }
            case("4"):{
                driver.findElement(MobileBy.id(uploadonly_id)).click();
                break;
            }
        }
    }

    public boolean isPasswordEnabled () {
        boolean switchEnabled = driver.findElement(MobileBy.id(enablePassword_id)).isEnabled();
        boolean passVisible = driver.findElement(MobileBy.id(textpassword_id)).isDisplayed();
        return switchEnabled && passVisible;
    }

    public void selectDownloadView(){
        Log.log(Level.FINE, "Starts: Select Download / View");
        driver.findElement(MobileBy.id(downloadview_id)).click();
    }

    public void selectDownloadViewUpload(){
        Log.log(Level.FINE, "Starts: Select Download / View / Upload");
        driver.findElement(MobileBy.id(downloadviewupload_id)).click();
    }

    public void selectUploadOnly(){
        Log.log(Level.FINE, "Starts: Select Upload Only (File drop)");
        driver.findElement(MobileBy.id(uploadonly_id)).click();
    }

    public boolean checkPermissions(String permissions){
            Log.log(Level.FINE, "Starts: Check permissions: " + permissions);
            switch (permissions){
                case("1"):{
                    if (driver.findElement(MobileBy.id(downloadview_id)).isEnabled()){
                        Log.log(Level.FINE, "Download / View is selected");
                        return true;
                    }
                }
                case("15"):{
                    if (driver.findElement(MobileBy.id(downloadviewupload_id)).isEnabled()){
                        Log.log(Level.FINE, "Download / View / Upload is selected");
                        return true;
                    }
                }
                case("4"):{
                    if (driver.findElement(MobileBy.id(uploadonly_id)).isEnabled()){
                        Log.log(Level.FINE, "Upload only is selected");
                        return true;
                    }
                }
            }
            return false;
    }

    public void closeKeyboard(){
        Log.log(Level.FINE, "Starts: Close keyboard");
        if (driver.isKeyboardShown()){
            Log.log(Level.FINE, "Keyboard is displayed");
            //((AppiumDriver)driver).hideKeyboard();
            driver.navigate().back();
        } else {
            Log.log(Level.FINE, "Keyboard is NOT displayed");
        }
    }

    public void close(){
        Log.log(Level.FINE, "Starts: Cancel public link view");
        takeScreenshot("PublicShare/ItemStatusBeforeClosing");
        driver.findElement(MobileBy.id(cancelbutton_id)).click();
    }

    public void submitLink() {
        Log.log(Level.FINE, "Starts: Submit public link");
        takeScreenshot("PublicShare/Submit");
        driver.findElement(MobileBy.id(savebutton_id)).click();
    }
}
