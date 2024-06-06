package ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class PreviewPage extends CommonPage {

    @iOSXCUITFindBy(id = "QLPreviewControllerView")
    private WebElement textPreview;

    @iOSXCUITFindBy(id = "loaded-image-gallery")
    private WebElement imageGallery;

    @iOSXCUITFindBy(id = "Outline")
    private WebElement outlinePdf;

    public PreviewPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isTextFilePreviewed(String itemName) {
        Log.log(Level.FINE, "Starts: is file previewed: " + itemName);
        return findXpath("//XCUIElementTypeStaticText[@name=\"" + itemName + "\"]").isDisplayed()
                && textPreview.isDisplayed();
    }

    public boolean isTextPreviewed(String text) {
        Log.log(Level.FINE, "Starts: is text previewed: " + text);
        return findXpath("//XCUIElementTypeTextView[@value=\"" + text + "\"]").isDisplayed();
    }

    public boolean isImagePreviewed(String itemName) {
        Log.log(Level.FINE, "Starts: is image previewed");
        return findXpath("//XCUIElementTypeStaticText[@name=\"" + itemName + "\"]").isDisplayed() &&
                imageGallery.isDisplayed();
    }

    public boolean isPdfPreviewed(String itemName) {
        Log.log(Level.FINE, "Starts: is pdf previewed");
        //Outline is an indicator that only exists in PDF preview
        return findXpath("//XCUIElementTypeStaticText[@name=\"" + itemName + "\"]").isDisplayed() &&
                outlinePdf.isDisplayed();
    }
}
