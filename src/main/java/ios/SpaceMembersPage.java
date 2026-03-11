package ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.date.DateUtils;
import utils.log.Log;

public class SpaceMembersPage extends CommonPage {

    @iOSXCUITFindBy(xpath = " //XCUIElementTypeStaticText[@name=\"Add members\"]")
    private WebElement addMembers;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Share\"]")
    private WebElement share;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Save changes\"]")
    private WebElement saveChanges;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Set\"]")
    private List<WebElement> setExpirationDate;

    @iOSXCUITFindBy(id = "Remove expiration date")
    private WebElement removeExpirationDate;

    @iOSXCUITFindBy(id = "Unshare")
    private WebElement removeMember;

    public static SpaceMembersPage instance;

    private SpaceMembersPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static SpaceMembersPage getInstance() {
        if (instance == null) {
            instance = new SpaceMembersPage();
        }
        return instance;
    }

    public void addMember(String userName){
        Log.log(Level.FINE, "Starts: add member " + userName);
        addMembers.click();
        WebElement inputUsers = driver.findElement(
                AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField' " +
                        "AND value == 'Search for users or groups'"));
        inputUsers.sendKeys(userName);
        findId(userName).click();
    }

    public void openMember(String userName){
        Log.log(Level.FINE, "Starts: open member: " + userName);
        findId(userName).click();
    }


    public void setPermission(String permission) {
        Log.log(Level.FINE, "Starts: add permission " + permission);
        waitById("PERMISSIONS");
        List<WebElement> permissions = findListId(permission);
        Log.log(Level.FINE, permissions.size() + " permissions found");
        // The second in the list with the exact name of the required permission. To improve somehow...
        permissions.get(1).click();
    }

    public void setExpirationDate(String days) {
        Log.log(Level.FINE, "Starts: Add expiration date in days " + days);
        days = normalizeOptional(days);
        boolean hasDays = days != null;
        if (setExpirationDate.size() > 0) { // there is no expiration date
            setExpirationDate.get(0).click();
            findId("Date Picker").click();
            findId("DatePicker.NextMonth").click();
            waitById(days);
            findId(days).click();
        } else {
            if (!hasDays) {
                removeExpirationDate.click();
                return;
            }
            findId("Date Picker").click();
            findId("DatePicker.NextMonth").click();
            waitById(days);
            findId(days).click();
        }
    }

    public boolean isExpirationDateCorrect(String userName, String days){
        Log.log(Level.FINE, "Starts: Check expiration date correct " + days);
        days = normalizeOptional(days);
        boolean hasDays = days != null;
       openMember(userName);
        if (hasDays) {
            String date = DateUtils.displayedDate(days);
            String dateDisplayed = findId("Date Picker").getAttribute("value");
            Log.log(Level.FINE, "Date to check: " + date);
            Log.log(Level.FINE, "Date displayed: " + dateDisplayed);
            findXpath("//XCUIElementTypeButton[@name=\"Cancel\"]").click();
            return dateDisplayed.equals(date);
        } else {
            findXpath("//XCUIElementTypeButton[@name=\"Cancel\"]").click();
            return !hasDays;
        }
    }

    public void shareWithMember(){
        share.click();
    }

    public void saveChanges(){
        saveChanges.click();
    }

    public void removeMember(){
        removeMember.click();
    }

    public boolean isUserMember(String userName, String permission) {
        Log.log(Level.FINE, "Starts: is " + userName + " member " + permission);
        List<WebElement> cells = findListCss("XCUIElementTypeCell");
        for (WebElement cell : cells) {
            String text = cell.getAttribute("name");
            Log.log(Level.FINE, "Cell text: " + text);
            if (text.contains(userName) && text.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    private String normalizeOptional(String value) {
        if (value == null)
            return null;
        String v = value.trim();
        return v.isEmpty() ? null : v;
    }
}
