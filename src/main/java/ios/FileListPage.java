package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.regex.Pattern;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListPage extends CommonPage {

    @iOSXCUITFindBy(id="client.file-add")
    private MobileElement plusButton;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Create folder\"]")
    private MobileElement createFolder;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Upload from your photo library\"]")
    private MobileElement uploadFile;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeTabBar[@name=\"Tab Bar\"]")
    private MobileElement bottomBar;

    @iOSXCUITFindBy(id="share-add-group")
    private MobileElement share;

    @iOSXCUITFindBy(id="Files")
    private MobileElement browseRoot;

    @iOSXCUITFindBy(id="Back")
    private MobileElement backArrow;

    @iOSXCUITFindBy(id="Close actions menu")
    private MobileElement closeActions;

    @iOSXCUITFindBy(id="client.folder-action")
    private MobileElement threeDotButton;

    @iOSXCUITFindBy(id="Personal")
    private MobileElement personal;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Toggle sidebar\"])[2]")
    private MobileElement sideMenuOpener;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"Quick Access\"]")
    private MobileElement quickAccess;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"Spaces\"]")
    private MobileElement spaces;

    @iOSXCUITFindBy(xpath ="//XCUIElementTypeButton[@name=\"Delete\"]")
    private MobileElement delete;


    //Actions in action menu
    private final String xpath_delete = "//XCUIElementTypeCell[@name=\"com.owncloud.action.delete\"]";
    private final String xpath_rename = "//XCUIElementTypeCell[@name=\"com.owncloud.action.rename\"]";
    private final String xpath_move = "//XCUIElementTypeCell[@name=\"com.owncloud.action.move\"]";
    private final String xpath_copy = "//XCUIElementTypeCell[@name=\"com.owncloud.action.copy\"]";
    private final String xpath_cut = "//XCUIElementTypeCell[@name=\"com.owncloud.action.cutpasteboard\"]";
    private final String xpath_paste = "//XCUIElementTypeCell[@name=\"com.owncloud.action.importpasteboard\"]";
    private final String xpath_duplicate = "//XCUIElementTypeCell[@name=\"com.owncloud.action.duplicate\"]";
    private final String xpath_avoffline = "//XCUIElementTypeCell[@name=\"com.owncloud.action.availableOffline\"]";
    private final String xpath_copydirectory = "//XCUIElementTypeButton[@name=\"Choose destination directory…\"]";

    //Actions in contextual menu menu
    private final String id_delete = "Delete";
    private final String id_rename = "Rename";
    private final String id_move = "Move";
    private final String id_copy = "Copy";
    private final String id_cut = "Cut";
    private final String id_duplicate = "Duplicate";
    private final String id_avoffline = "Make available offline";
    private final String id_favorite = "Favorite item";
    private final String id_unfavorite = "Unfavorite item";
    private final String id_sharing = "com.owncloud.action.collaborate";
    private final String id_sharing_c = "Sharing";

    public FileListPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void refreshBySwipe() {
        swipe(0.50, 0.40, 0.50, 0.90);
    }

    public void createFolder() {
        Log.log(Level.FINE, "Starts: create folder");
        //Needed a short wait, to improve
        wait(1);
        openPlusButton();
        createFolder.click();
    }

    public void openThreeDotButton() {
        Log.log(Level.FINE, "Starts: Open three dot button");
        threeDotButton.click();
    }

    public void pasteAction() {
        Log.log(Level.FINE, "Starts: Paste action");
        findXpath(xpath_paste).click();
    }

    public void openPlusButton(){
        Log.log(Level.FINE, "Starts: Open plus button");
        plusButton.click();
    }

    public void uploadFromGallery() {
        Log.log(Level.FINE, "Starts: Upload file from Gallery");
        String idPermission = "Allow Full Access";
        openPlusButton();
        uploadFile.click();
        if (!findListId(idPermission).isEmpty()){
            findId(idPermission).click();
        }
        //Wait till gallery loads. When the "Cancel" button is present
        waitById(5, "Cancel");
    }

    public void openCollection(String collection){
        Log.log(Level.FINE, "Starts: Open Quick Access collection: " + collection);
        String collectionXpath = "//XCUIElementTypeStaticText[@name=\"" + collection + "\"]";
        sideMenuOpener.click();
        quickAccess.click();
        findXpath(collectionXpath).click();
    }

    public void openSpacesList(){
        Log.log(Level.FINE, "Starts: Open Spces list");
        sideMenuOpener.click();
        spaces.click();
    }

    public void executeOperation(String operation, String itemName, String typeItem, String menu){
        Log.log(Level.FINE, "Starts: execute operation: " + operation + " " +
                itemName + " "+ menu);
        switch (menu) {
            case "Actions":
                selectItemListActions(itemName);
                selectOperationFromActions(itemName, operation, typeItem);
                break;
            case "Contextual":
                selectItemListContextual(itemName);
                selectOperationFromContextual(itemName, operation, typeItem);
                break;
            case "Swipe":
                selectItemListSwipe(itemName);
                break;
        }
    }

    public void downloadAction(String itemName) {
        Log.log(Level.FINE, "Starts: download action: " + itemName);
        findId(itemName).click();
    }

    public boolean isItemInList(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in list: " + itemName);
        wait (2);
        return findId(itemName).isDisplayed();
    }

    public boolean isNotItemInList(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is NOT in list: " + itemName);
        return findListId(itemName).isEmpty();
    }

    public boolean isListEmpty() {
        Log.log(Level.FINE, "Starts: Check if filelist is empty");
        return !findListId("No contents").isEmpty();
    }

    public boolean isItemInScreen(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in screen: " + itemName);
        //A system notification can rise at this point. We refuse it
        //This can be managed via capabilities but affects other test cases.
        if (dontAllow.size() > 0){
            dontAllow.get(0).click();
        }
        return !findListId(itemName).isEmpty();
    }

    public void itemInScreen(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in screen: " + itemName);
        findListId(itemName).isEmpty();
    }

    public void selectItemListActions(String itemName) {
        Log.log(Level.FINE, "Starts: select actions item from list: " + itemName);
        String name = itemName;
        name = browseTo(itemName);
        openCard(name);
    }

    private void selectItemListContextual(String itemName) {
        Log.log(Level.FINE, "Starts: select contextual item from list: " + itemName);
        MobileElement listCell = findId(itemName);
        new TouchAction(driver).longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(listCell))).release().perform();
    }

    public String getPrivateLink(String scheme, String linkOriginal) {
        Log.log(Level.FINE, "Starts: Create private link: " + scheme + " " + linkOriginal);
        String originalScheme = getScheme(linkOriginal);
        Log.log(Level.FINE, "Original scheme: " + originalScheme);
        String linkToOpen = linkOriginal.replace(originalScheme, scheme);
        Log.log(Level.FINE, "Link to open: " + linkToOpen);
        return linkToOpen;
    }

    public void openPrivateLink(String privateLink) {
        Log.log(Level.FINE, "Starts: Open private link: " + privateLink);
        driver.get(privateLink);
        //Let some time to load... did not found a reliable condition to avoid this ugly wait
        wait (5);
    }

    public void openFakePrivateLink() {
        Log.log(Level.FINE, "Starts: Open fake private link");
        String originalScheme = getScheme(System.getProperty("server"));
        String fakeURL = System.getProperty("server").replace(originalScheme, "owncloud") + "/f/11111111111";
        Log.log(Level.FINE, "Fake URL: " + fakeURL);
        driver.get(fakeURL);
        //Let some time to load... did not found a reliable condition to avoid this ugly wait
        wait (5);
    }

    private String getScheme (String originalURL){
        return originalURL.split("://")[0];
    }

    public boolean privateLinkFailed() {
        return findXpath("//XCUIElementTypeStaticText[@name=\"Link resolution failed\"]").isDisplayed();
    }

    public boolean itemOpened (String itemType, String itemName) {
        Log.log(Level.FINE, "Starts: checking if item is opened: " + itemType + " " + itemName);
        if (itemType.equals("file")) {
            Log.log(Level.FINE, "Opening file");
            return findXpath("//XCUIElementTypeStaticText[@name=\"" + itemName + "\"]").isDisplayed();
        } else if (itemType.equals("folder")) {
            Log.log(Level.FINE, "Opening folder");
            return findXpath("//XCUIElementTypeStaticText[@name=\"" + itemName + "\"]").isDisplayed();
            //return findId("show-paths-button").isDisplayed();
        }
        return false;
    }

    protected String browseTo(String path){
        Log.log(Level.FINE, "Browse to path: " + path);
        String completePath = Pattern.quote("/");
        String[] route = path.split(completePath);
        Log.log(Level.FINE, "Route lenght: " + route.length);
        for (int j = 0 ; j < route.length ; j++) {
            Log.log(Level.FINE, "Chunk: " + j + ": " + route[j]);
        }
        if (route.length > 0) { //we have to browse
            int i;
            for (i = 0; i < route.length - 1 ; i++) {
                Log.log(Level.FINE, "Browsing to: " + route[i]);
                browse(route[i]);
            }
            return route[i];
        } else { //root folder, nothing to do
            return path;
        }
    }

    private void selectItemListSwipe(String itemName) {
        Log.log(Level.FINE, "Starts: select item from list by swiping: " + itemName);
        MobileElement listCell = findId(itemName);
        swipeElementIOS(listCell, "LEFT");
    }

    public void selectOperationFromActions(String itemName, String operationName, String typeItem) {
        MobileElement operation = null;
        Log.log(Level.FINE, "Starts actions: " + operationName);
        switch (operationName){
            case "delete":
                operation = (MobileElement) findXpath(xpath_delete);
                break;
            case "rename":
                operation = (MobileElement) findXpath(xpath_rename);
                break;
            case "move":
                operation = (MobileElement) findXpath(xpath_move);
                break;
            case "copy":
                operation = (MobileElement) findXpath(xpath_copy);
                break;
            case "duplicate":
                operation = (MobileElement) findXpath(xpath_duplicate);
                break;
            case "make available offline":
                operation = (MobileElement) findId(id_avoffline);
                //The file take some to download
                break;
            case "cut":
                operation = (MobileElement) findXpath(xpath_cut);
                break;
            case "paste":
                operation = (MobileElement) findXpath(xpath_paste);
                break;
            case "share":
                operation = (MobileElement) findId(id_sharing);
                break;
            case "favorite":
                operation = findId(id_favorite);
                break;
            case "unfavorite":
                operation = findId(id_unfavorite);
                break;
            default:
                break;
        }
        operation.click();
        if (operationName.equals("copy")){
            Log.log(Level.FINE, "Selecting copy to directory");
            findXpath(xpath_copydirectory).click();
        } else if (operationName.equals("make available offline")){
            Log.log(Level.FINE, "Wait file to be downloaded");
            //Condition to be improved. Check how to verify a file is downlaoded
            wait(3);
        }
    }

    public void selectOperationFromContextual(String itemName, String operationName, String typeItem) {
        MobileElement operation = null;
        Log.log(Level.FINE, "Starts contextual: " + operationName);
        switch (operationName){
            case "delete":
                operation = findId(id_delete);
                break;
            case "rename":
                operation = findId(id_rename);
                break;
            case "move":
                operation = findId(id_move);
                break;
            case "copy":
                operation = findId(id_copy);
                break;
            case "cut":
                operation = findId(id_cut);
                break;
            case "duplicate":
                operation = findId(id_duplicate);
                break;
            case "make available offline":
                operation = findId(id_avoffline);
                break;
            case "share":
                operation = findId(id_sharing_c);
                break;
            case "favorite":
                operation = findId(id_favorite);
                break;
            case "unfavorite":
                operation = findId(id_unfavorite);
                break;
            default:
                break;
        }
        operation.click();
        if (operationName.equals("copy")){
            Log.log(Level.FINE, "Selecting copy to directory");
            findXpath(xpath_copydirectory).click();
        } else if (operationName.equals("make available offline")){
            Log.log(Level.FINE, "Wait file to be downloaded");
            //Condition to be improved. Check how to verify a file is downlaoded
            wait(3);
        }
    }

    public void closeActions(){
        Log.log(Level.FINE, "Starts: Close Actions Menu");
        closeActions.click();
    }

    public void browseRoot(){
        Log.log(Level.FINE, "Starts: browse to root");
        //assuming 1st level... to improve
        backArrow.click();
    }

    public void acceptDeletion(){
        Log.log(Level.FINE, "Starts: accept deletion");
        delete.click();
    }

    public void openCard(String itemName){
        Log.log(Level.FINE, "Starts: openCard for " + itemName);
        findId("More").click();
    }

    public boolean isMarkedAsAvOffline(String itemName) {
        Log.log(Level.FINE, "Starts: Check if file is av. offline");
        String finalName = browseTo(itemName);
        Log.log(Level.FINE, "Final file name: " + finalName);
        refreshBySwipe();
        openCard(finalName);
        String switchAvOff = "//XCUIElementTypeSwitch[@name=\"Make available offline\"]";
        boolean switchStatus = findXpath(switchAvOff).getAttribute("value").equals("1");
        Log.log(Level.FINE, "Av. Offline status: " + switchStatus);
        return switchStatus;
    }

    public boolean isAvOfflineAvailable(String itemName) {
        Log.log(Level.FINE, "Starts: Check if av. offline is available");
        String finalName = browseTo(itemName);
        Log.log(Level.FINE, "Final file name: " + finalName);
        refreshBySwipe();
        openCard(finalName);
        return !findListId(id_avoffline).isEmpty();
    }

    public boolean itemIsFavorite(String itemName){
        selectItemListActions(itemName);
        return !findListId(id_unfavorite).isEmpty();
    }

    public boolean displayedList(String path, ArrayList<OCFile> listServer){
        boolean found = true;
        browseToFolder(path); //moving to the folder
        Iterator iterator = listServer.iterator();
        while (iterator.hasNext()){
            OCFile ocfile = (OCFile) iterator.next();
            Log.log(Level.FINE, "Checking item in list: " + ocfile.getName());
            //Server returns the username as value. Here, we skip it.
            //in oCIS, id is returned instead of name in reference.
            //Shortcut: username > 15 = id (check a best method)
            if (ocfile.getName().equalsIgnoreCase(LocProperties.getProperties().getProperty("userName1")) ||
                    ocfile.getName().length() > 15) {
                continue;
            }
            if (!isItemInList(ocfile.getName())) {
                Log.log(Level.FINE, "Item " + ocfile.getName() + " is not in the list");
                found = false;
                break;
            }
        }
        return found;
    }
}
