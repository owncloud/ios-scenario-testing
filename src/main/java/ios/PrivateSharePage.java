package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class PrivateSharePage extends CommonPage {

    @iOSXCUITFindBy(id="Viewer")
    private MobileElement viewer;

    @iOSXCUITFindBy(id="Editor")
    private MobileElement editor;

    @iOSXCUITFindBy(id="Uploader")
    private MobileElement uploader;

    @iOSXCUITFindBy(id="Contributor")
    private MobileElement contributor;

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

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Invite\"]")
    private MobileElement inviteButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Save changes\"]")
    private MobileElement saveChanges;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Unshare\"]")
    private MobileElement unshare;


    public PrivateSharePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void searchSharee(String shareeName, String type) {
        Log.log(Level.FINE, "Starts: Searching for sharee: " + shareeName + " that is a "+type);
        String urlShare = System.getProperty("server").substring(8)
                .split(":")[0]; //remove protocol, get only URL
        String searchXpath = "//XCUIElementTypeTextField[@name=\"Alice@" + urlShare + "\"]";
        findXpath(searchXpath).sendKeys(shareeName);
        findXpath("(//XCUIElementTypeStaticText[@name=\"" + shareeName + "\"])[1]").click();
    }

    public void setPermissions(String permission) {
        Log.log(Level.FINE, "Starts: Set permissions: " + permission);
        switch (permission){
            case("Viewer"):{
                viewer.click();
                break;
            }
            case("Editor"):{
                editor.click();
                break;
            }
            case("Uploader"):{
                uploader.click();
                break;
            }
            case("Contributor"):{
                contributor.click();
                break;
            }
            //Sharing as default as custom. Changeable if needed
            case("Custom"):{
                custom.click();
                share.click();
                break;
            }
        }
    }

    public void invite(){
        Log.log(Level.FINE, "Starts: Invite sharee");
        inviteButton.click();
    }

    public void removeSharingPermission() {
        Log.log(Level.FINE, "Starts: Remove sharing permission");
        custom.click();
        share.click();
    }

    public void savePermissions() {
        Log.log(Level.FINE, "Starts: Save permissions private share");
        inviteButton.click();
    }

    public void deletePrivateShare(){
        unshare.click();
    }

    public void saveChanges(){
        saveChanges.click();
    }
}
