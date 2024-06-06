package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class PreviewPage extends CommonPage {

    public PreviewPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isFilePreviewed(String itemName) {
        Log.log(Level.FINE, "Starts: is file previewed: " + itemName);
        return findXpath("//XCUIElementTypeStaticText[@name=\"" + itemName + "\"]").isDisplayed();
    }

    public boolean isTextPreviewed(String text) {
        Log.log(Level.FINE, "Starts: is text previewed: " + text);
        return findXpath("//XCUIElementTypeTextView[@value=\"" + text + "\"]").isDisplayed();
    }
}
