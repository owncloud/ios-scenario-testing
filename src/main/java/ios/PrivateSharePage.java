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

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Invite\"]")
    private MobileElement inviteButton;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Cancel\"]")
    private MobileElement cancelButton;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeTextField[@name=\"user1@192.168.1.20\"]")
    private MobileElement searchField;

    @iOSXCUITFindBy(id="Viewer")
    private MobileElement viewer;

    @iOSXCUITFindBy(id="Editor")
    private MobileElement editor;

    @iOSXCUITFindBy(id="Custom")
    private MobileElement custom;

    @iOSXCUITFindBy(id="Read")
    private MobileElement read;

    @iOSXCUITFindBy(id="Upload")
    private MobileElement upload;

    @iOSXCUITFindBy(id="Delete")
    private MobileElement delete;

    @iOSXCUITFindBy(id="Share")
    private MobileElement share;

    @iOSXCUITFindBy(id="Expiration date")
    private MobileElement expirationDate;


    public PrivateSharePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void searchSharee(String shareeName, String email, String type) {
        Log.log(Level.FINE, "Starts: Searching for sharee: " + shareeName + " that is a "+type);
        searchSharee.sendKeys(shareeName);
        if (type.equals("group")){
            shareeName += " (Group)";
        }
        //oCIS (OIDC) returns email together with username. oC10, does not.
        if (authType.equals("OIDC") && !type.equals("group")) {
            shareeName += " (" + email + ")";
        }
        findXpath("//XCUIElementTypeStaticText[@name=\"" + shareeName + "\"]").click();
        //**/XCUIElementTypeStaticText[`label == "user1"`][3] <- new sharee
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
