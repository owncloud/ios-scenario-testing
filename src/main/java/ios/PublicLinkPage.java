package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.date.DateUtils;
import utils.log.Log;

public class PublicLinkPage extends CommonPage {

    @iOSXCUITFindBy(id = "Name")
    private WebElement linkName;

    @iOSXCUITFindBy(id = "Can view")
    private WebElement viewer;

    @iOSXCUITFindBy(id = "Can edit")
    private WebElement editor;

    @iOSXCUITFindBy(id = "Secret File Drop")
    private WebElement secretFileDrop;

    @iOSXCUITFindBy(id = "Set")
    private WebElement setPasswordButton;

    @iOSXCUITFindBy(id = "Generate")
    private WebElement generatePassword;

    @iOSXCUITFindBy(id = "******")
    private WebElement passwordEnabled;

    @iOSXCUITFindBy(className = "XCUIElementTypeSecureTextField")
    private WebElement passwordField;

    @iOSXCUITFindBy(id = "Remove password")
    private WebElement removePassword;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Add\"]")
    private WebElement addExpirationDate;

    @iOSXCUITFindBy(id = "Date Picker")
    private WebElement datePicker;

    @iOSXCUITFindBy(id = "DatePicker.NextMonth")
    private WebElement nextMonth;

    @iOSXCUITFindBy(id = "Remove expiration date")
    private WebElement removeExpirationDate;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Create\"]")
    private WebElement createButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Save changes\"]")
    private WebElement saveChanges;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Unshare\"]")
    private WebElement unshareLink;

    public static PublicLinkPage instance;

    private PublicLinkPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static PublicLinkPage getInstance() {
        if (instance == null) {
            instance = new PublicLinkPage();
        }
        return instance;
    }

    public void setName(String name) {
        Log.log(Level.FINE, "Starts: Add link name: " + name);
        linkName.sendKeys(name);
    }

    public void setPermission(String permission) {
        Log.log(Level.FINE, "Starts: Set link permission: " + permission);
        switch (permission) {
            case ("Viewer"): {
                viewer.click();
                break;
            }
            case ("Editor"): {
                editor.click();
                break;
            }
            case ("Secret"): {
                secretFileDrop.click();
                break;
            }
        }
    }

    public void setPassword(String password) {
        Log.log(Level.FINE, "Starts: Add link password: " + password);
        if (!password.equals("\"\"")) { //Password creation
            Log.log(Level.FINE, "Creating new password: " + password);
            setPasswordButton.click();
            passwordField.sendKeys(password);
            setPasswordButton.click();
        } else { //Password removal
            Log.log(Level.FINE, "Removing existing password");
            removePassword.click();
        }
    }

    public void setPasswordAuto() {
        Log.log(Level.FINE, "Starts: Add link password automatically");
        generatePassword.click();
    }

    //Day to set: given day of the following month
    public void setExpiration(String expirationDay) {
        Log.log(Level.FINE, "Starts: Set Expiration date: " + expirationDay);
        if (!expirationDay.equals("0")){
            //Given the expiration day, selected the day within the following month
            addExpirationDate.click();
            datePicker.click();
            nextMonth.click();
            findId(expirationDay).click();
        } else {
            //If the expiration date is set, remove it
            if (hasExpiration()){
                removeExpiration();
            }
        }
    }

    public void removeExpiration() {
        Log.log(Level.FINE, "Starts: Remove expiration date");
        removeExpirationDate.click();
    }

    public boolean isNameCorrect(String name) {
        Log.log(Level.FINE, "Starts: Check if name is correct: " + name);
        return findId("Name").getAttribute("value").equals(name);
    }

    public boolean isPasswordEnabled(String itemName, String password) {
        Log.log(Level.FINE, "Starts: Check password enabled: " + itemName + " with password: " + password);
        if (password.equals("\"\"")) { //Password not set
            return generatePassword.isDisplayed() && setPasswordButton.isDisplayed();
        } else { //Password set
            return passwordEnabled.isDisplayed();
        }
    }

    public boolean hasExpiration() {
        Log.log(Level.FINE, "Starts: Check expiration date");
        return !driver.findElements(By.xpath(
                "//XCUIElementTypeStaticText[contains(@name, 'Expires')]")).isEmpty();
    }

    public boolean isExpirationCorrect(String day) {
        Log.log(Level.FINE, "Starts: Check expiration day: " + day);
        if (!day.equals("0")) { //Adding 1 to the day, because UTC retrieved by the server
            String displayedDate = DateUtils.displayedDate(String.valueOf(Integer.parseInt(day) +1 ));
            Log.log(Level.FINE, "Date to check: " + displayedDate);
            String dateInPicker = datePicker.getAttribute("value");
            Log.log(Level.FINE, "Date to check in the screen: " + dateInPicker);
            return dateInPicker.equals(displayedDate);
        } else { //Expiration not set, "Add" is visible
            return addExpirationDate.isDisplayed();
        }
    }

    public void submitLink() {
        createButton.click();
    }

    public void saveChanges() {
        saveChanges.click();
    }

    public void deleteLink() {
        unshareLink.click();
    }
}
