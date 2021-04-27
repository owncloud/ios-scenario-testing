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

    /*@iOSXCUITFindBy(id="Can Edit and Change")
    private MobileElement editPermission;*/

    @iOSXCUITFindBy(id="permission-section-edit")
    private MobileElement editPermission;

    @iOSXCUITFindBy(id="permission-section-edit-create")
    private MobileElement createPermission;

    @iOSXCUITFindBy(id="permission-section-edit-change")
    private MobileElement changePermission;

    @iOSXCUITFindBy(id="permission-section-edit-delete")
    private MobileElement deletePermission;

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

    public void switchCreate() {
        Log.log(Level.FINE, "Starts: Click create checkbox");
        createPermission.click();
    }

    public void switchChange() {
        Log.log(Level.FINE, "Starts: Click change checkbox");
        changePermission.click();
    }

    public void switchDelete() {
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        deletePermission.click();
    }

    public void switchShare() {
        Log.log(Level.FINE, "Starts: Switch share button");
        sharePermission.click();
    }

    public boolean isCreateSelected(){
        return  parseIntBool(createPermission.getAttribute("selected"));
    }

    public boolean isChangeSelected(){
        return parseIntBool(changePermission.getAttribute("selected"));
    }

    public boolean isDeleteSelected(){
        return parseIntBool(deletePermission.getAttribute("selected"));
    }

    public boolean isEditPermission(){
        return isCreateSelected() || isChangeSelected() || isDeleteSelected();
    }

    public boolean isShareEnabled(){
        return parseIntBool(sharePermission.getAttribute("selected"));
    }

    public boolean isEditEnabled(){
        return parseIntBool(editPermission.getAttribute("selected"));
    }

    public void savePermissions() {
        Log.log(Level.FINE, "Starts: Save permissions");
        savePermissions.click();
    }

    public void backListShares() {
        Log.log(Level.FINE, "Starts: Back to the list of shares");
        cancelPermissions.click();
    }

    private boolean parseIntBool(String s){
        return Boolean.parseBoolean(s);
    }
}