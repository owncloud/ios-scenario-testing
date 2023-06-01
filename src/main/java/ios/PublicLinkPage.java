package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.date.DateUtils;
import utils.log.Log;

public class PublicLinkPage extends CommonPage {

    @iOSXCUITFindBy(id="Viewer")
    private MobileElement viewer;

    @iOSXCUITFindBy(id="Editor")
    private MobileElement editor;

    @iOSXCUITFindBy(id="Uploader")
    private MobileElement uploader;

    @iOSXCUITFindBy(id="Contributor")
    private MobileElement contributor;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Add\"])[1]")
    private MobileElement passwordButton;

    @iOSXCUITFindBy(id = "******")
    private MobileElement passwordEnabled;

    @iOSXCUITFindBy(className = "XCUIElementTypeSecureTextField")
    private MobileElement passwordField;

    @iOSXCUITFindBy(id = "OK")
    private MobileElement OKButton;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Add\"])[2]")
    private MobileElement expirationButton;

    @iOSXCUITFindBy(id = "Date Picker")
    private MobileElement datePicker;

    @iOSXCUITFindBy(id = "Month")
    private MobileElement monthPicker;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther[5]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
            "/XCUIElementTypeOther[2]/XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]" +
            "/XCUIElementTypeDatePicker/XCUIElementTypePicker/XCUIElementTypePickerWheel[1]")
    private MobileElement monthWheel;

    @iOSXCUITFindBy(id = "Create link")
    private MobileElement createLink;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Save changes\"]")
    private MobileElement saveChanges;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Unshare\"]")
    private MobileElement unshareLink;

    public PublicLinkPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void setPermission (String permission) {
        Log.log(Level.FINE, "Starts: Set link permission: " + permission);
        switch (permission){
            case("Viewer"):{
                viewer.click();
                break;
            }
            case("Editor"):{
                editor.click();
                break;
            }
            case("Uploader"):{
                uploader.click();
                break;
            }
            case("Contributor"):{
                contributor.click();
                break;
            }
        }
    }

    public void setPassword (String password) {
        Log.log(Level.FINE, "Starts: Add link password: " + password);
        passwordButton.click();
        passwordField.sendKeys("a");
        OKButton.click();
    }

    public boolean isPasswordEnabled(String itemName) {
        return passwordEnabled.isDisplayed();
    }

    //Day to set: given day of the following month
    public void setExpiration (String day){
        Log.log(Level.FINE, "Starts: Set Expiration date: " + day);
        expirationButton.click();
        datePicker.click();
        monthPicker.click();
        //No matter which month, wheel moves to th next value. Framework issue
        monthWheel.sendKeys("December");
        datePicker.click();
        findId(day).click();
    }

    public boolean isExpirationCorrect(String day){
        Log.log(Level.FINE, "Starts: Check expiration day: " + day);
        String displayedDate = DateUtils.displayedDate(day);
        Log.log(Level.FINE, "Date to compare: " + displayedDate);
        String dateInPicker = datePicker.getAttribute("value");
        Log.log(Level.FINE, "Date to check in the screen: " + dateInPicker);
        return dateInPicker.equals(displayedDate);
    }

    public void submitLink(){
        createLink.click();
    }

    public void saveChanges(){
        saveChanges.click();
    }

    public void deleteLink(){
        unshareLink.click();
    }
}
