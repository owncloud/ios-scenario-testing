package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class PrivateSharePage extends SharePage {

    @iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"Sharing\"]")
    private MobileElement sharingTitle;

    @iOSXCUITFindBy(id="Add email or name")
    private MobileElement searchSharee;

    @iOSXCUITFindBy(id="Remove Recipient")
    private MobileElement removeShare;

    @iOSXCUITFindBy(id="permission-section-share")
    private MobileElement sharePermission;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Sharing\"]")
    private MobileElement backButton;

    private String xpath_invite = "(//XCUIElementTypeOther[@name=\"INVITE RECIPIENT\"])[2]";

    public PrivateSharePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void searchSharee(String shareeName, String type){
        Log.log(Level.FINE, "Starts: Searching for sharee: " + shareeName + " that is a "+type);
        waitById(5, searchSharee);
        searchSharee.sendKeys(shareeName);
        if (type.equals("group")){
            shareeName += " (Group)";
        }
        waitByXpath(5, xpath_invite);
        findXpath("//XCUIElementTypeStaticText[@name=\"" + shareeName + "\"]").click();
    }

    public boolean isItemInListPrivateShares(String sharee) {
        return !findListId(sharee).isEmpty();
    }

    public void deletePrivateShare(String sharee){
        findId(sharee).click();
        removeShare.click();
    }

    public void openPrivateShare(String sharee){
        findId(sharee).click();
    }

    public boolean displayedPermission(String permissionName){
        return !findListId(permissionName).isEmpty();
    }

    public boolean isPasswordEnabled () {
        //TODO
        return true;
    }

    public void close(){
        backButton.click();
    }
}
