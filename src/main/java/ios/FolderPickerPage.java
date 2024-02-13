package ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class FolderPickerPage extends CommonPage {

    @iOSXCUITFindBy(id = "Cancel")
    private WebElement cancelButton;

    @iOSXCUITFindBy(id = "Create folder")
    private WebElement createFolder;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[@name=\"Personal\"])[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther")
    private WebElement personalList;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[@name=\"Files\"])[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther")
    private WebElement filesList;

    private String xpath_move = "//XCUIElementTypeButton[@name=\"Move here\"]";
    private String xpath_copy = "//XCUIElementTypeButton[@name=\"Copy here\"]";

    public FolderPickerPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void selectSpace(String action) {
        Log.log(Level.FINE, "Start: Select space");
        if (!authType.equals("OIDC")) {
            Log.log(Level.FINE, "Not OIDC, just selecting Files");
            filesList.click();
        } else {
            if (action.equals("copy")) {
                Log.log(Level.FINE, "OIDC with Copy = Personal");
                personalList.click();
            }
        }
    }

    public void selectFolder(String targetFolder) {
        Log.log(Level.FINE, "Start: Select folder in picker: " + targetFolder);
        if (!targetFolder.equals("/")) { //If it is root, nothing to do
            if (!targetFolder.contains("/")) { //If it does not contain "/", just browse to next level
                findXpath("(//XCUIElementTypeStaticText[@name=\"" + targetFolder + "\"])[2]").click();
            } else { //browsing to deeper
                browseToFolder(targetFolder);
            }
        }
    }

    public void selectFolder(String targetFolder, String action) {
        Log.log(Level.FINE, "Start: Select folder from picker: " + targetFolder);
        selectSpace(action);
        selectFolder(targetFolder);
    }

    public void createFolder() {
        Log.log(Level.FINE, "Start: Create folder");
        createFolder.click();
    }

    public void accept(String operation) {
        Log.log(Level.FINE, "Start: Accept selection picker");
        if (operation.equals("move")) {
            findXpath(xpath_move).click();
        } else if (operation.equals("copy")) {
            findXpath(xpath_copy).click();
        }
        if (dontAllow.size() > 0) {
            dontAllow.get(0).click();
        }
    }

    public void cancel() {
        Log.log(Level.FINE, "Start: Cancel selection picker");
        cancelButton.click();
    }

    public boolean actionEnabled(String actionId) {
        return findId(actionId).isEnabled();
    }

    public boolean isItemEnabled(String itemName) {
        return findId(itemName).getAttribute("enabled").equals("true");
    }
}
