package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class SharePermissionsPage extends CommonPage {

    @iOSXCUITFindBy(id="Save")
    private MobileElement savePermissions;

    @iOSXCUITFindBy(id="Cancel")
    private MobileElement cancelPermissions;

    @iOSXCUITFindBy(id="Can Share")
    private MobileElement sharePermission;

    @iOSXCUITFindBy(id="Can Edit and Change")
    private MobileElement editPermission;

    public SharePermissionsPage()  {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void setSharePermission(){
        sharePermission.click();
    }

    public void setEditPermission(){
        editPermission.click();
    }

    /*public void shareWithUser (String sharee)
            throws InterruptedException {
        Log.log(Level.FINE, "Starts: Share with user: " + sharee);
        waitById(10, shareeUsername);
        shareeUsername.sendKeys(sharee);
        Log.log(Level.WARNING, "Needed better implementation - failure possible");
        selectShareeFromList(sharee);
        //Go back to Share Page
        backListShares();
    }

    private void selectShareeFromList(String sharee)
            throws InterruptedException {
        //REDO: find another way to click in recipients' list
        Thread.sleep(1000);
        takeScreenshot("PrivateShare/SearchSharee_" + sharee);
        TouchAction selectSharee = new TouchAction(driver);
        selectSharee.tap(PointOption.point(500, 470)).perform();
    }*/

    public void savePermissions() {
        Log.log(Level.FINE, "Starts: Save permissions");
        savePermissions.click();
    }

    public void backListShares() {
        Log.log(Level.FINE, "Starts: Back to the list of shares");
        cancelPermissions.click();
    }
}