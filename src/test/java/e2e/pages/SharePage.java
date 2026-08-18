package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import e2e.support.log.Log;

public class SharePage extends CommonPage {

    @iOSXCUITFindBy(id = "//XCUIElementTypeStaticText[@name=\"Add members\"]")
    private WebElement inviteButton;

    @iOSXCUITFindBy(id = "person.3.fill")
    private List<WebElement> shareeGroup;

    @iOSXCUITFindBy(id = "Create link")
    private WebElement createLinkButton;

    @iOSXCUITFindBy(id = "Copy Private Link")
    private WebElement copyPrivateLink;

    @iOSXCUITFindBy(id = "Can view")
    private WebElement viewerPermission;

    @iOSXCUITFindBy(id = "Can edit")
    private WebElement editorPermission;

    @iOSXCUITFindBy(id = "Can edit with trashbin")
    private WebElement editorTrashbin;

    @iOSXCUITFindBy(id = "Can upload")
    private WebElement uploadPermission;

    @iOSXCUITFindBy(id = "Secret File Drop")
    private WebElement secretFileDropPermission;

    @iOSXCUITFindBy(id = "Done")
    private WebElement doneButton;

    public SharePage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void invite() {
        Log.log(Level.FINE, "Starts: Invite");
        findXpath("//XCUIElementTypeStaticText[@name=\"Add members\"]").click();
    }

    public void openPublicLink(String linkName) {
        Log.log(Level.FINE, "Starts: open public link: " + linkName);
        if (linkName.equals("")){
            final String linkOpener = "link";
            driver.findElement(By.id(linkOpener)).click();
        } else {
            driver.findElement(By.id(linkName)).click();
        }
    }

    public void openPublicLink() {
        Log.log(Level.FINE, "Starts: open public link with default name");
        final String linkOpener = "link";
        driver.findElement(By.id(linkOpener)).click();
    }

    public void openPrivateShare(String sharee) {
        Log.log(Level.FINE, "Starts: open private share");
        findId(sharee).click();
    }

    public void createLink() {
        createLinkButton.click();
    }

    public boolean isItemInListLinks(String linkName) {
        Log.log(Level.FINE, "Starts: link in list");
        return !findLinkByName().isEmpty();
    }

    private List<WebElement> findLinkByName() {
        // Default name for links
        return driver.findElements(By.id("Link"));
    }

    public boolean isItemInListPrivateShares(String sharee) {
        Log.log(Level.FINE, "Starts: Share in list: " + sharee);
        return !findListId(sharee).isEmpty();
    }

    public boolean isNameCorrect(String name) {
        Log.log(Level.FINE, "Starts: Check link name: " + name);
        return !findListId((name)).isEmpty();
    }

    public boolean isGroup() {
        Log.log(Level.FINE, "Starts: Check group or user");
        return !shareeGroup.isEmpty();
    }

    public boolean isSharePermissionCorrect(String permissionName) {
        return switch (permissionName) {
            case "Viewer" -> viewerPermission.isDisplayed();
            case "Editor" -> editorPermission.isDisplayed();
            case "EditorTrashbin" -> editorTrashbin.isDisplayed();
            case "Upload" -> uploadPermission.isDisplayed();
            default -> false;
        };
    }

}
