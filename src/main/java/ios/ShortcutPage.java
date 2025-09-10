package ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class ShortcutPage extends CommonPage {

    @iOSXCUITFindBy(id = "URL")
    private WebElement urlInput;

    @iOSXCUITFindBy(id = "Name")
    private WebElement nameInput;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Create shortcut\"]")
    private WebElement createShortcut;

    @iOSXCUITFindBy(id = "Pick file or folder")
    private WebElement pickFile;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeImage[@name=\"person\"])[2]")
    private WebElement personalList;

    @iOSXCUITFindBy(id = "Safari")
    private WebElement safariBrowser;

    public static ShortcutPage instance;

    private ShortcutPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static ShortcutPage getInstance() {
        if (instance == null) {
            instance = new ShortcutPage();
        }
        return instance;
    }

    public void createShortcutWeb(String urlTo, String name) {
        Log.log(Level.FINE, "Starts: create shortcut: " + urlTo + " " + name);
        urlInput.sendKeys(urlTo);
        nameInput.sendKeys(name);
        createShortcut.click();
    }

    public void createShortcutFile(String itemName, String shortcutName) {
        Log.log(Level.FINE, "Starts: create shortcut over file: "
                + itemName + " with name: " + shortcutName);
        pickFile.click();
        personalList.click();
        findXpath("(//XCUIElementTypeStaticText[@name=\"" + itemName + "\"])[2]").click();
        createShortcut.click();
    }

    public boolean isBrowserVisible() {
        return safariBrowser.isEnabled();
    }

}
