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

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Set\"]")
    private WebElement setExpirationDate;

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

    public void setPermission(String permission) {
        Log.log(Level.FINE, "Starts: add permission " + permission);
        findId(permission).click();
    }

    public void setExpirationDate(String days) {
        Log.log(Level.FINE, "Starts: Add expiration date in days " + days);
        setExpirationDate.click();
        findId("Date Picker").click();
        findId("DatePicker.NextMonth").click();
        waitById(days);
        findId(days).click();
    }

    public boolean isExpirationDateCorrect(String days){
        Log.log(Level.FINE, "Starts: Check expiration date correct " + days);
        String date = DateUtils.displayedDate(days);
        Log.log(Level.FINE, "Date displayed: " + date);
        WebElement element = findIOSPredicateSubText(date);
        return element!=null;
    }

    public void shareWithMember(){
        share.click();
    }

    public boolean isUserMember(String userName, String permission) {
        Log.log(Level.FINE, "Starts: is " + userName + " member " + permission);
        List<WebElement> cells = findListCss("XCUIElementTypeCell");
        for (WebElement cell : cells) {
            String text = cell.getAttribute("name");
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
