package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.date.DateUtils;
import utils.log.Log;

public class PublicLinkPage extends CommonPage {

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkExpirationValue")
    private MobileElement expirationDate;

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkNameValue")
    private MobileElement namePublicLink;

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkPasswordSwitch")
    private MobileElement switchPassword;

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkPasswordValue")
    private MobileElement textPassword;

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkEditPermissionReadOnly")
    private MobileElement downloadViewOption;

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkEditPermissionReadAndWrite")
    private MobileElement downloadViewUploadOption;

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkEditPermissionUploadFiles")
    private MobileElement uploadOnlyOption;

    @AndroidFindBy(id="com.owncloud.android:id/shareViaLinkExpirationSwitch")
    private MobileElement switchExpiration;

    @AndroidFindBy(id="com.owncloud.android:id/cancelButton")
    private MobileElement cancelButton;

    @AndroidFindBy(id="com.owncloud.android:id/saveButton")
    private MobileElement saveButton;

    @AndroidFindBy(id="android:id/button1")
    private MobileElement okButton;

    @AndroidFindBy(id="android:id/next")
    private MobileElement nextButton;

    public PublicLinkPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void addLinkName (String linkName) {
        Log.log(Level.FINE, "Starts: Add link name: " + linkName);
        namePublicLink.clear();
        namePublicLink.sendKeys(linkName);
    }

    public void addPassword (String password) {
        Log.log(Level.FINE, "Starts: Add link password: " + password);
        switchPassword.click();
        waitById(5, textPassword);
        textPassword.sendKeys(password);
        swipe(0.50, 0.45, 0.50, 0.30);
    }

    public void setPermission (String permission) {
        Log.log(Level.FINE, "Starts: Set link permission: " + permission);
        switch (permission){
            case("1"):{
                downloadViewOption.click();
                break;
            }
            case("15"):{
                downloadViewUploadOption.click();
                break;
            }
            case("4"):{
                uploadOnlyOption.click();
                break;
            }
        }
    }

    public void setExpiration (String days){
        Log.log(Level.FINE, "Starts: Set Expiration date in days: " + days);
        switchExpiration.click();
        String dateToSet = DateUtils.dateInDaysAndroidFormat(days);
        if (driver.findElements(new MobileBy.ByAccessibilityId(dateToSet)).isEmpty()){
            Log.log(Level.FINE,"Date not found, next page");
            nextButton.click();
        }
        driver.findElement(new MobileBy.ByAccessibilityId(dateToSet)).click();
        takeScreenshot("PublicShare/ExpirationDateSelected");
        okButton.click();
    }

    public boolean isPasswordEnabled () {
        waitById(5, switchPassword);
        boolean switchEnabled = switchPassword.isEnabled();
        boolean passVisible = textPassword.isDisplayed();
        return switchEnabled && passVisible;
    }

    public void selectDownloadView(){
        Log.log(Level.FINE, "Starts: Select Download / View");
        downloadViewOption.click();
    }

    public void selectDownloadViewUpload(){
        Log.log(Level.FINE, "Starts: Select Download / View / Upload");
        downloadViewUploadOption.click();
    }

    public void selectUploadOnly(){
        Log.log(Level.FINE, "Starts: Select Upload Only (File drop)");
        uploadOnlyOption.click();
    }

    public boolean checkPermissions(String permissions){
            Log.log(Level.FINE, "Starts: Check permissions: " + permissions);
            switch (permissions){
                case("1"):{
                    if (downloadViewOption.isEnabled()){
                        Log.log(Level.FINE, "Download / View is selected");
                        return true;
                    }
                }
                case("15"):{
                    if(downloadViewUploadOption.isEnabled()){
                        Log.log(Level.FINE, "Download / View / Upload is selected");
                        return true;
                    }
                }
                case("4"):{
                    if (uploadOnlyOption.isEnabled()){
                        Log.log(Level.FINE, "Upload only is selected");
                        return true;
                    }
                }
            }
            return false;
    }

    public boolean checkExpiration(String days){
        Log.log(Level.FINE, "Starts: Check expiration in days: " + days);
        String shortDate = DateUtils.shortDate(days);
        Log.log(Level.FINE, "Date to check: " + shortDate);
        takeScreenshot("PublicShare/ExpirationDateChecks");
        boolean switchEnabled = switchExpiration.isEnabled();
        boolean dateCorrect = expirationDate.getText().equals(shortDate);
        return switchEnabled && dateCorrect;
    }

    public void close(){
        Log.log(Level.FINE, "Starts: Cancel public link view");
        takeScreenshot("PublicShare/ItemStatusBeforeClosing");
        cancelButton.click();
    }

    public void submitLink() {
        Log.log(Level.FINE, "Starts: Submit public link");
        takeScreenshot("PublicShare/Submit");
        saveButton.click();
    }
}
