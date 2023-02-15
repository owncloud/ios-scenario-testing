package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    @iOSXCUITFindBy(id="Close actions menu")
    private MobileElement closeActions;

    @iOSXCUITFindBy(id="client.folder-action")
    private MobileElement threeDotButton;

    @iOSXCUITFindBy(id="Personal")
    private MobileElement personal;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"Quick Access\"]")
    private MobileElement quickAccess;


    //Actions in action menu
    private final String xpath_delete = "//XCUIElementTypeCell[@name=\"com.owncloud.action.delete\"]";
    private final String xpath_rename = "//XCUIElementTypeCell[@name=\"com.owncloud.action.rename\"]";
    private final String xpath_move = "//XCUIElementTypeCell[@name=\"com.owncloud.action.move\"]";
    private final String xpath_copy = "//XCUIElementTypeCell[@name=\"com.owncloud.action.copy\"]";
    private final String xpath_cut = "//XCUIElementTypeCell[@name=\"com.owncloud.action.cutpasteboard\"]";
    private final String xpath_paste = "//XCUIElementTypeCell[@name=\"com.owncloud.action.importpasteboard\"]";
    private final String xpath_duplicate = "//XCUIElementTypeCell[@name=\"com.owncloud.action.duplicate\"]";
    private final String xpath_avoffline = "//XCUIElementTypeCell[@name=\"com.owncloud.action.makeAvailableOffline\"]";
    private final String xpath_unavoffline = "//XCUIElementTypeCell[@name=\"com.owncloud.action.makeUnavailableOffline\"]";
    private final String xpath_sharefolder = "//XCUIElementTypeStaticText[@name=\"Share this folder\"]";
    private final String xpath_sharefile = "//XCUIElementTypeStaticText[@name=\"Share this file\"]";
    private final String xpath_editshare = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[1]";
    private final String xpath_sharelink = "//XCUIElementTypeStaticText[@name=\"Links\"]";
    private final String xpath_editlink = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[2]";
    private final String xpath_favorite = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[2]";
    private final String xpath_card = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]";
    private final String xpath_copydirectory = "//XCUIElementTypeButton[@name=\"Choose destination directory…\"]";
    private final String xpath_copyclipboard = "//XCUIElementTypeButton[@name=\"Copy to Clipboard\"]";

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
    private final String id_unavoffline = "com.owncloud.action.makeUnavailableOffline";
    private final String id_share = "Sharing";
    private final String id_link = "Links";

    public FileListPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void refreshBySwipe() throws InterruptedException {
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

    public void uploadFromGallery() {
        Log.log(Level.FINE, "Starts: Upload file from Gallery");
        String xpathOptionGallery = "//XCUIElementTypeAlert[@name=\"“ownCloud” Would Like to Access Your Photos\"]" +
                "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeScrollView[2]" +
                "/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther[3]";
        openPlusButton();
        uploadFile.click();
        findXpath(xpathOptionGallery).click();
        //Wait till gallery loads. When the "Cancel" button is present
        waitById(5, "Cancel");
    }

    public void selectPhotoGallery(){
        Log.log(Level.FINE, "Starts: Select Photo Gallery");
        //Very ugly, but not other way to select a picture outside the app
        String xpathAnyPhoto = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
                "/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther" +
                "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
                "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
                "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
                "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView" +
                "/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther" +
                "/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther[@index='0']";
        List<MobileElement> photoList = findListId("Photo");
        photoList.get(0).click();
        findId("Add").click();
        //Wait till upload finishes before asserting
        wait(5);
    }

    public String photoUploaded(ArrayList<OCFile> listFiles){
        Log.log(Level.FINE, "Items: " + listFiles.size());
        for (OCFile ocfile: listFiles) {
            Log.log(Level.FINE, "Item: " + ocfile.getName());
            if ((ocfile.getName().contains("Photo-")) || (ocfile.getName().contains("Video-"))) {
                return ocfile.getName();
            }
        }
        return "";
    }

    public void openPlusButton(){
        Log.log(Level.FINE, "Starts: Open plus button");
        plusButton.click();
    }

    public void openCollection(String collection){
        Log.log(Level.FINE, "Starts: Open Quick Access collection: " + collection);
        findId("Back").click();
        quickAccess.click();
        findId(collection).click();
        //findId(collection+"-collection-row").click();
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
        if (authType.equals("OIDC")){
            Log.log(Level.FINE, "OCIS list");
            return !findListId("No contents").isEmpty();
        } else {
            Log.log(Level.FINE, "No OCIS list");
            return !findListId("Empty folder").isEmpty();
        }
    }

    public boolean isItemInScreen(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in QuickAccess: " + itemName);
        return !findListId(itemName).isEmpty();
    }

    public void itemInScreen(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in QuickAccess: " + itemName);
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
            return findId("show-paths-button").isDisplayed();
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
                operation = (MobileElement) findXpath(xpath_avoffline);
                //The file take some to download
                break;
            case "cut":
                operation = (MobileElement) findXpath(xpath_cut);
                break;
            case "paste":
                operation = (MobileElement) findXpath(xpath_paste);
                break;
            case "share":
                String xpath_sharetype;
                if (typeItem.equalsIgnoreCase("folder")) {
                    xpath_sharetype = xpath_sharefolder;
                } else {
                    xpath_sharetype = xpath_sharefile;
                }
                operation = (MobileElement) findXpath(xpath_sharetype);
                break;
            case "edit share":
                operation = (MobileElement) findXpath(xpath_editshare);
                break;
            case "share by link":
                operation = (MobileElement) findXpath(xpath_sharelink);
                break;
            case "edit link":
                operation = (MobileElement) findXpath(xpath_editlink);
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
            //Condition to be improved
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
            case "edit share":
                operation = findId(id_share);
                break;
            case "share by link":
            case "edit link":
                operation = findId(id_link);
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
            //Condition to be improved
            wait(3);
        }
    }

    public void closeActions(){
        Log.log(Level.FINE, "Starts: Close Actions Menu");
        closeActions.click();
    }

    public void browseRoot(){
        Log.log(Level.FINE, "Starts: browse to root");
        //Different labels in oCIS - oC10
        if (authType.equals("OIDC")) {
            personal.click();
        } else {
            browseRoot.click();
        }
    }

    public void acceptDeletion(){
        Log.log(Level.FINE, "Starts: accept deletion");
        findXpath("//XCUIElementTypeButton[@name=\"Delete\"]").click();
    }

    public void openCard(String itemName){
        Log.log(Level.FINE, "Starts: openCard for " + itemName);
        //waitById(15, itemName);
        //findId(itemName + " Actions").click();
        findId("More").click();
        //MobileElement mobileElement = findId(itemName);
        //mobileElement.getAttribute()

        //.findElement(By.xpath("./..")).findElement(By.xpath("./..")).findElement(By.id("More")).click();
        //findXpath("(//XCUIElementTypeButton[@name=\"More\"])[1]").click();
    }

    public boolean fileIsMarkedAsAvOffline(String itemName) {
        Log.log(Level.FINE, "Starts: Check if file is av. offline");
        boolean menuAvoffline = false;
        String finalName = browseTo(itemName);
        Log.log(Level.FINE, "Final file name: " + finalName);
        openCard(finalName);
        menuAvoffline = findListId(id_avoffline).isEmpty();
        Log.log(Level.FINE, "Av. Offline conditions: " + menuAvoffline);
        return menuAvoffline;
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
