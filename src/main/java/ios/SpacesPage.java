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
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"More\"])[1]")
    protected WebElement moreButton;

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
        // Wait for the space to be visible
        waitById(moreButton);
        moreButton.click();
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

    public void disableSpace(String name) {
        Log.log(Level.FINE, "Starts: disable space with name: " + name);
        moreButton.click();
        disableSpaceAction.click();
    }

    public void enableSpace(String name) {
        Log.log(Level.FINE, "Starts: enable space with name: " + name);
        moreButton.click();
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

    public boolean isSpaceInDisabledList(List<List<String>> spaces) {
        Log.log(Level.FINE, "Starts: check space is in disabled list");
        spaceActions.click();
        showDisabledSpacesAction.click();
        boolean disabledText = findTextByXpath("Disabled").isDisplayed();
        return disabledText && areAllSpacesVisible(spaces);
    }

    public boolean areAllSpacesVisible(List<List<String>> spaces){
        Log.log(Level.FINE, "Starts: check all spaces are visible");
        for (List<String> rows : spaces) {
            String name = rows.get(0);
            String description = rows.get(1);
            Log.log(Level.FINE, "Space name: " + name + " Space description: " + description);
            if (findListId(name).isEmpty() && findListId(description).isEmpty()){
                Log.log(Level.FINE, "Space not found");
                return false;
            } else {
                Log.log(Level.FINE, "Space found");
            }
        }
        return true;
    }

    public boolean areAllSpacesNotVisible(List<List<String>> spaces){
        Log.log(Level.FINE, "Starts: check all spaces are not visible");
        for (List<String> rows : spaces) {
            String name = rows.get(0);
            String description = rows.get(1);
            if (!findListId(name).isEmpty() && !findListId(description).isEmpty()){
                return false;
            }
        }
        return true;
    }
}
