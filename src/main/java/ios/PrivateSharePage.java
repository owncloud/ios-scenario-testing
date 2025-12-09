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

    @iOSXCUITFindBy(id = "Can view View and download.")
    private WebElement viewer;

    @iOSXCUITFindBy(id = "Can upload View, download and upload.")
    private WebElement uploader;

    @iOSXCUITFindBy(id = "Can edit without versions View, download, upload, edit, add and delete.")
    private WebElement editor;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Set\"]")
    private WebElement addExpirationDate;

    @iOSXCUITFindBy(id = "Remove expiration date")
    private WebElement removeExpirationDate;

    @iOSXCUITFindBy(id = "Date Picker")
    private WebElement datePicker;

    @iOSXCUITFindBy(id = "DatePicker.NextMonth")
    private WebElement nextMonth;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Share\"]")
    private WebElement shareButton;

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

    public void searchSharee(String shareeName) {
        Log.log(Level.FINE, String.format("Starts: Searching for sharee: %s", shareeName));

        String server = System.getProperty("server");
        String urlShare = server.split("://")[1].split(":")[0];

        String textFieldXpath = String.format("//XCUIElementTypeTextField[@name=\"Alice@%s\"]", urlShare);
        findXpath(textFieldXpath).sendKeys(shareeName);

        findId(shareeName).click();
    }

    public boolean isNameCorrect(String name){
        Log.log(Level.FINE, "Starts: Checking sharee name: " + name);
        return findXpath("(//XCUIElementTypeStaticText[@name=\"" + name + "\"])[2]").isDisplayed();
    }

    public void setPermissions(String permission) {
        Log.log(Level.FINE, "Starts: Set permissions: " + permission);
        switch (permission) {
            case "Viewer" -> viewer.click();
            case "Editor" -> editor.click();
            case "Upload" -> uploader.click();
            default -> Log.log(Level.WARNING, "Unknown permission: " + permission);
        }
    }

    public boolean isPermissionEnabled (String permission) {
        Log.log(Level.FINE, "Starts: Check permission enabled: " + permission);
        String checkmarkId = "checkmark";
        return switch (permission) {
            case ("Viewer") -> viewer.findElement(By.id(checkmarkId)).isDisplayed();
            case ("Editor") -> editor.findElement(By.id(checkmarkId)).isDisplayed();
            case ("Upload") -> uploader.findElement(By.id(checkmarkId)).isDisplayed();
            default -> false;
        };
    }

    public void setExpiration(String expirationDay) {
        Log.log(Level.FINE, "Starts: Set expiration date: " + expirationDay);
        if (!expirationDay.equals("0")){
            addExpirationDate.click();
            datePicker.click();
            nextMonth.click();
            waitById(expirationDay);
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
            String dateInPicker = datePicker.getAttribute("value");
            Log.log(Level.FINE, "Date to check: " + displayedDate +
                    ". Date to check in the screen: " + dateInPicker);
            return dateInPicker.equals(displayedDate);
        } else { //Expiration not set, "Add" is visible
            return addExpirationDate.isDisplayed();
        }
    }

    public void savePermissions() {
        Log.log(Level.FINE, "Starts: Save permissions private share");
        shareButton.click();
    }

    public void deletePrivateShare() {
        Log.log(Level.FINE, "Starts: delete/unshare private share");
        unshare.click();
    }

    public void saveChanges() {
        Log.log(Level.FINE, "Starts: Save changes private share");
        saveChanges.click();
    }
}
