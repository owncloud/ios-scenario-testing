package ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.date.DateUtils;
import utils.entities.OCShare;
import utils.log.Log;

public class SharePage extends CommonPage {

    @iOSXCUITFindBy(id = "Invite")
    private WebElement inviteButton;

    @iOSXCUITFindBy(id = "Create link")
    private WebElement createLinkButton;

    @iOSXCUITFindBy(id = "Copy Private Link")
    private WebElement copyPrivateLink;

    @iOSXCUITFindBy(id = "Viewer (Download, preview and share)")
    private WebElement viewerPermission;

    @iOSXCUITFindBy(id = "Editor (Upload, edit, delete, download, preview and share)")
    private WebElement editorPermission;

    @iOSXCUITFindBy(id = "Custom (Set detailed permissions)")
    private WebElement customPermission;

    @iOSXCUITFindBy(id = "Done")
    private WebElement doneButton;

    public SharePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void invite() {
        Log.log(Level.FINE, "Starts: Invite");
        inviteButton.click();
    }

    public void openPublicLink(String linkName) {
        Log.log(Level.FINE, "Starts: open public link: " + linkName);
        findId(linkName).click();
    }

    public void openPublicLink() {
        Log.log(Level.FINE, "Starts: open public link with default name");
        final String linkOpener = "link";
        findId(linkOpener).click();
    }

    public void openPrivateShare(String sharee) {
        Log.log(Level.FINE, "Starts: open private share");
        findId(sharee).click();
    }

    public void createLink() {
        createLinkButton.click();
    }

    public boolean checkCorrectShare(OCShare remoteShare, List<List<String>> dataList) {
        Log.log(Level.FINE, "Starts: Check correct share");
        HashMap<String, String> mapFields = turnListToHashmap(dataList);
        for (Map.Entry<String, String> entry : mapFields.entrySet()) {
            Log.log(Level.FINE, "Entry KEY: " + entry.getKey() + " - VALUE: " + entry.getValue());
            switch (entry.getKey()) {
                case "id": {
                    if (!remoteShare.getId().equalsIgnoreCase(entry.getValue())) {
                        Log.log(Level.FINE, "ID does not match - Remote: " + remoteShare.getId()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "user": {
                    if (remoteShare.getType().equals("0")) { // private share
                        if (!remoteShare.getShareeName().equalsIgnoreCase(entry.getValue())) {
                            Log.log(Level.FINE, "Sharee does not match - Remote: " + remoteShare.getShareeName()
                                    + " - Expected: " + entry.getValue());
                            return false;
                        }
                    }
                    break;
                }
                case "password": {
                    if (!(remoteShare.getType().equals("3") && remoteShare.hasPassword())) {
                        Log.log(Level.FINE, "Password not present");
                        return false;
                    }
                    break;
                }
                case "name": {
                    if (!remoteShare.getLinkName().equals(entry.getValue())) {
                        Log.log(Level.FINE, "Item name does not match - Remote: " + remoteShare.getLinkName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "path": {
                    if (!remoteShare.getItemName().equals(entry.getValue())) {
                        Log.log(Level.FINE, "Item path does not match - Remote: " + remoteShare.getItemName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "uid_owner": {
                    if (!remoteShare.getOwner().equalsIgnoreCase(entry.getValue())) {
                        Log.log(Level.FINE, "Owner name does not match - Remote: " + remoteShare.getOwner()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "permission": {
                    if (!translatePermissionstoString(remoteShare.getPermissions()).equals(entry.getValue())) {
                        Log.log(Level.FINE, translatePermissionstoString(remoteShare.getPermissions()) + " " + entry.getValue());
                        Log.log(Level.FINE, "Permissions do not match - Remote: " + translatePermissionstoString(remoteShare.getPermissions())
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "expiration": {
                    //Get only month-day-year
                    String remoteDate = remoteShare.getExpiration().substring(0, 10);
                    String expDate = DateUtils.dateInDaysWithServerFormat(Integer.valueOf(entry.getValue()));
                    Log.log(Level.FINE, "Expiration dates: Remote: " + remoteDate
                            + " - Expected: " + expDate);
                    if (!remoteDate.equals(expDate)) {
                        Log.log(Level.FINE, "Expiration dates do not match");
                        return false;
                    }
                }
            }
        }
        Log.log(Level.FINE, "All fields match. Returning true");
        return true;
    }

    public boolean isItemInListLinks() {
        Log.log(Level.FINE, "Starts: link in list");
        return !findListId("Link").isEmpty();
    }

    public boolean isItemInListPrivateShares(String sharee) {
        Log.log(Level.FINE, "Starts: Share in list: " + sharee);
        return !findListId(sharee).isEmpty();
    }

    public boolean displayedPermission(String permissionName) {
        switch (permissionName) {
            case "Viewer": {
                return viewerPermission.isDisplayed();
            }
            case "Editor": {
                return editorPermission.isDisplayed();
            }
            case "Custom": {
                return customPermission.isDisplayed();
            }
            default:
                return false;
        }
    }

    //Permissions from server come in numeric format. This method translates
    private String translatePermissionstoString(String permission) {
        Log.log(Level.FINE, "Permission to translate: " + permission);
        if (permission.equals("1"))
            return "Viewer";
        if (permission.equals("3"))
            return "Editor";
        if (permission.equals("15"))
            return "Editor";
        if (permission.equals("4"))
            return "Uploader";
        if (permission.equals("5"))
            return "Contributor";
        return "";
    }

    public String translatePermissionsToInt(String permission) {
        Log.log(Level.FINE, "Permission to translate: " + permission);
        if (permission.equals("Viewer"))
            return "17";
        if (permission.equals("Editor"))
            return "15";
        if (permission.equals("Uploader"))
            return "4";
        if (permission.equals("Contributor"))
            return "5";
        return "";
    }

    private HashMap<String, String> turnListToHashmap(List<List<String>> dataList) {
        HashMap<String, String> mapFields = new HashMap<String, String>();
        for (List<String> rows : dataList) {
            mapFields.put(rows.get(0), rows.get(1));
        }
        return mapFields;
    }
}