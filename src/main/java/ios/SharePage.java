package ios;

import org.openqa.selenium.By;
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

    @iOSXCUITFindBy(id = "Can view")
    private WebElement viewerPermission;

    @iOSXCUITFindBy(id = "Can edit without versions")
    private WebElement editorPermission;

    @iOSXCUITFindBy(id = "Can upload")
    private WebElement uploadPermission;

    @iOSXCUITFindBy(id = "Secret File Drop")
    private WebElement secretFileDropPermission;

    @iOSXCUITFindBy(id = "Done")
    private WebElement doneButton;

    public static SharePage instance;

    private SharePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static SharePage getInstance() {
        if (instance == null) {
            instance = new SharePage();
        }
        return instance;
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
                    if (!(remoteShare.getType().equals("3") && remoteShare.hasPassword())
                            && !entry.getValue().equals("\"\"")) {
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
                    /*boolean hasExpiration = !remoteShare.getExpiration().isEmpty();
                    Log.log(Level.FINE, "Expiration: " + remoteShare.getExpiration()
                            + " - Expected: " + entry.getValue());
                    return switch (entry.getValue().toLowerCase()) {
                        case "yes" -> hasExpiration;
                        case "no" -> !hasExpiration;
                        default -> false;
                    };*/

                    //Get only month-day-year
                    String expirationDay = entry.getValue();
                    if (!expirationDay.equals("0")) {
                        String remoteDate = remoteShare.getExpiration().substring(0, 10);
                        String shareType = remoteShare.getType();
                        String expDate = "";
                        if (shareType.equals("0")) {
                            expDate = DateUtils.dateInDaysWithServerFormat(Integer.valueOf(entry.getValue()));
                        } else if (shareType.equals("3")) {
                            expDate = DateUtils.dateInDaysWithServerFormat(Integer.valueOf(entry.getValue())-1);
                        }
                        Log.log(Level.FINE, "Expiration dates: Remote: " + remoteDate
                                + " - Expected: " + expDate);
                        if (!remoteDate.equals(expDate)) {
                            Log.log(Level.FINE, "Expiration dates do not match");
                            return false;
                        }
                    } else {
                        if (remoteShare.getExpiration() != null && !remoteShare.getExpiration().isEmpty()) {
                            Log.log(Level.FINE, "Expiration date not expected " + remoteShare.getExpiration());
                            return false;
                        }
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

    public boolean isNameCorrect(String name) {
        Log.log(Level.FINE, "Starts: Check link name: " + name);
        return findId(name).isDisplayed();
        //return linkName.getAttribute("value").equals(name);
    }

    public boolean isPermissionCorrect(String permission) {
        Log.log(Level.FINE, "Starts: Check link permission: " + permission);
        return switch (permission) {
            case ("Viewer") -> //viewer.isDisplayed();
                    findXpath("//*[contains(@name, 'Can view')]").isDisplayed();
            case ("Editor") -> //editor.isDisplayed();
                    findXpath("//*[contains(@name, 'Can edit')]").isDisplayed();
            case ("Secret") -> //secretFileDrop.isDisplayed();
                    findXpath("//*[contains(@name, 'Secret File Drop')]").isDisplayed();
            default -> false;
        };
    }

    public boolean isExpirationCorrect(String expirationDay) {
        Log.log(Level.FINE, "Starts: Check expiration day: " + expirationDay);
        if (!expirationDay.equals("0")) {
            String displayedDate = DateUtils.displayedDate(expirationDay);
            Log.log(Level.FINE, "Date to check: " + displayedDate);
            return !driver.findElements(By.xpath(
                    "//XCUIElementTypeStaticText[contains(@name, 'Expires " +
                            displayedDate + "')]")).isEmpty();
        } else {
            return true;
        }
    }

    public boolean displayedPermission(String permissionName) {
        return switch (permissionName) {
            case "Viewer" -> viewerPermission.isDisplayed();
            case "Editor" -> editorPermission.isDisplayed();
            case "Upload" -> uploadPermission.isDisplayed();
            default -> false;
        };
    }

    //Permissions from server come in numeric format. This method translates
    private String translatePermissionstoString(String permission) {
        Log.log(Level.FINE, "Permission to translate: " + permission);
        return switch (permission) {
            case "1" -> "Viewer";
            case "3" -> "Editor";
            case "15" -> "Editor";
            case "4" -> "Secret";
            case "5" -> "Contributor";
            default -> "";
        };
    }

    public String translatePermissionsToInt(String permission) {
        Log.log(Level.FINE, "Permission to translate: " + permission);
        return switch (permission) {
            case "Viewer" -> "17";
            case "Editor" -> "15";
            case "Uploader" -> "4";
            case "Contributor" -> "5";
            default -> "";
        };
    }

    private HashMap<String, String> turnListToHashmap(List<List<String>> dataList) {
        HashMap<String, String> mapFields = new HashMap<String, String>();
        for (List<String> rows : dataList) {
            mapFields.put(rows.get(0), rows.get(1));
        }
        return mapFields;
    }
}