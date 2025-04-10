package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.date.DateUtils;
import utils.log.Log;

public class PrivateSharePage extends CommonPage {

    @iOSXCUITFindBy(id = "Can view")
    private WebElement viewer;

    @iOSXCUITFindBy(id = "Can edit without versions")
    private WebElement editor;

    @iOSXCUITFindBy(id = "Can upload")
    private WebElement uploader;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Add\"]")
    private WebElement addExpirationDate;

    @iOSXCUITFindBy(id = "Remove expiration date")
    private WebElement removeExpirationDate;

    @iOSXCUITFindBy(id = "Date Picker")
    private WebElement datePicker;

    @iOSXCUITFindBy(id = "DatePicker.NextMonth")
    private WebElement nextMonth;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Invite\"]")
    private WebElement inviteButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Save changes\"]")
    private WebElement saveChanges;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Unshare\"]")
    private WebElement unshare;

    public static PrivateSharePage instance;

    private PrivateSharePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static PrivateSharePage getInstance() {
        if (instance == null) {
            instance = new PrivateSharePage();
        }
        return instance;
    }

    public void searchSharee(String shareeName, String type) {
        Log.log(Level.FINE, "Starts: Searching for sharee: " + shareeName + " that is a " + type);
        String urlShare = System.getProperty("server").split("://")[1].split(":")[0];
        String searchXpath = "//XCUIElementTypeTextField[@name=\"Alice@" + urlShare + "\"]";
        findXpath(searchXpath).sendKeys(shareeName);
        findXpath("(//XCUIElementTypeStaticText[@name=\"" + shareeName + "\"])[1]").click();
    }

    public boolean isNameCorrect(String name){
        Log.log(Level.FINE, "Starts: Checking sharee name: " + name);
        return findXpath("(//XCUIElementTypeStaticText[@name=\"" + name + "\"])[2]").isDisplayed();
    }

    public void setPermissions(String permission) {
        Log.log(Level.FINE, "Starts: Set permissions: " + permission);
        switch (permission) {
            case ("Viewer"): {
                viewer.click();
                break;
            }
            case ("Editor"): {
                editor.click();
                break;
            }
            case ("Upload"): {
                uploader.click();
                break;
            }
        }
    }

    public void setExpiration(String expirationDay) {
        Log.log(Level.FINE, "Starts: Set expiration date: " + expirationDay);
        //expirationDate.click();
        if (!expirationDay.equals("0")){
        addExpirationDate.click();
        datePicker.click();
        nextMonth.click();
        findId(expirationDay).click();
        } else {
            if (hasExpiration()){
                removeExpiration();
            }
        }
    }

    public void removeExpiration() {
        Log.log(Level.FINE, "Starts: Remove expiration date");
        removeExpirationDate.click();
    }

    public boolean hasExpiration() {
        Log.log(Level.FINE, "Starts: Check expiration date");
        return !driver.findElements(By.xpath(
                "//XCUIElementTypeStaticText[contains(@name, 'Expires')]")).isEmpty();
    }

    public boolean isExpirationCorrect(String day) {
        Log.log(Level.FINE, "Starts: Check expiration day: " + day);
        if (!day.equals("0")) {
        String displayedDate = DateUtils.displayedDate(String.valueOf(Integer.parseInt(day)));
        Log.log(Level.FINE, "Date to check: " + displayedDate);
        String dateInPicker = datePicker.getAttribute("value");
        Log.log(Level.FINE, "Date to check in the screen: " + dateInPicker);
        return dateInPicker.equals(displayedDate);
        } else {
            return true;
        }
    }

    public void savePermissions() {
        Log.log(Level.FINE, "Starts: Save permissions private share");
        inviteButton.click();
    }

    public void deletePrivateShare() {
        unshare.click();
    }

    public void saveChanges() {
        saveChanges.click();
    }
}
