package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class PublicLinkPage extends SharePage {

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Create Public Link\"]")
    private MobileElement selectCreateLink;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Links\"]")
    private MobileElement header;

    private final String xpath_header = "//XCUIElementTypeStaticText[@name=\"Links\"]";

    public PublicLinkPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void createLink (String linkName) {
        selectCreateLink.click();
    }

    public void openPublicLink(String linkName){
        Log.log(Level.FINE, "Starts: open public link: " + linkName);
        waitByXpath(10, xpath_header);
        findId(linkName).click();
    }

    public boolean isItemInListLinks(String itemName) {
        Log.log(Level.FINE, "Starts: link in list: " + itemName);
        waitByXpath(10, xpath_header);
        return !findListId(itemName).isEmpty();
    }
}
