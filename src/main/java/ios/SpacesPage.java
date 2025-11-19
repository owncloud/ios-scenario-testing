package ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class SpacesPage extends CommonPage {

    @iOSXCUITFindBy(id = "client.space-actions")
    protected WebElement spaceActions;

    @iOSXCUITFindBy(id = "Create space")
    protected WebElement createSpaceAction;

    @iOSXCUITFindBy(id = "Show disabled spaces")
    protected WebElement showDisabledSpacesAction;

    @iOSXCUITFindBy(id = "Hide disabled spaces")
    protected WebElement hideDisabledSpacesAction;

    @iOSXCUITFindBy(id = "Name")
    protected WebElement spaceName;

    @iOSXCUITFindBy(id = "Subtitle")
    protected WebElement spaceSubtitle;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Create\"]")
    protected WebElement spaceCreateButton;

    // More button for the first space in the list
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"More\"])")
    protected List<WebElement> moreButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Save\"]")
    protected WebElement saveButton;

    @iOSXCUITFindBy(id ="com.owncloud.action.managespace")
    protected WebElement editSpaceAction;

    @iOSXCUITFindBy(id ="com.owncloud.action.disablespace")
    protected WebElement disableSpaceAction;

    @iOSXCUITFindBy(xpath ="//XCUIElementTypeButton[@name=\"Enable\"]")
    protected WebElement enableSpaceAction;

    @iOSXCUITFindBy(id ="Delete")
    protected WebElement deleteSpaceAction;

    public static SpacesPage instance;

    private SpacesPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static SpacesPage getInstance() {
        if (instance == null) {
            instance = new SpacesPage();
        }
        return instance;
    }

    public void createSpace (String name, String subtitle) {
        Log.log(Level.FINE, "Starts: create space with name: " + name + " and subtitle: " + subtitle);
        spaceActions.click();
        createSpaceAction.click();
        writeSpaceNameSubtitle(name, subtitle);
        spaceCreateButton.click();
    }

    public void editSpace (String name, String subtitle) {
        Log.log(Level.FINE, "Starts: edit space with name: " + name + " and subtitle: " + subtitle);
        waitByList(moreButton);
        moreButton.get(0).click();
        editSpaceAction.click();
        writeSpaceNameSubtitle(name, subtitle);
        saveButton.click();
    }

    private void writeSpaceNameSubtitle(String name, String subtitle) {
        Log.log(Level.FINE, "Starts: write space name and subtitle");
        spaceName.clear();
        spaceName.sendKeys(name);
        spaceSubtitle.clear();
        spaceSubtitle.sendKeys(subtitle);
    }

    public void disableSpace(String name, String subtitle) {
        Log.log(Level.FINE, "Starts: disable space ");
        waitByList(moreButton);
        moreButton.get(0).click();
        disableSpaceAction.click();
    }

    public void enableSpace(String name) {
        Log.log(Level.FINE, "Starts: enable space");
        waitByList(moreButton);
        moreButton.get(0).click();
        enableSpaceAction.click();
    }

    public void showDisabledSpaces() {
        Log.log(Level.FINE, "Starts: Show disabled spaces");
        spaceActions.click();
        showDisabledSpacesAction.click();
    }

    public void hideDisabledSpaces() {
        Log.log(Level.FINE, "Starts: Hide disabled spaces");
        spaceActions.click();
        hideDisabledSpacesAction.click();
    }

    public boolean isSpaceInDisabledList(String name, String subtitle) throws InterruptedException {
        Log.log(Level.FINE, "Starts: check space" + name + "is in disabled list");
        showDisabledSpaces();
        boolean disabledText = findTextByXpath("Disabled").isDisplayed();
        return disabledText && isSpaceVisible(name, subtitle);
    }

    public boolean isSpaceVisible(String name, String subtitle) throws InterruptedException {
        Log.log(Level.FINE, "Starts: check " + name + " space are visible");
        //Ugly waiter because space list refreshes randomly
        Thread.sleep(5000);
        Log.log(Level.FINE, "Space name: " + name + " Space description: " + subtitle);
        if (findListId(name).isEmpty() && findListId(subtitle).isEmpty()){
            Log.log(Level.FINE, "Space not found");
            return false;
        } else {
            Log.log(Level.FINE, "Space found");
            return true;
        }
    }
}
