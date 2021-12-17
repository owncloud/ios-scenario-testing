package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListPage extends CommonPage {

    @iOSXCUITFindBy(id="client.file-add")
    private MobileElement plusButton;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Create folder\"]")
    private MobileElement createFolder;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeTabBar[@name=\"Tab Bar\"]")
    private MobileElement bottomBar;

    @iOSXCUITFindBy(id="share-add-group")
    private MobileElement share;

    @iOSXCUITFindBy(id="Files")
    private MobileElement browseRoot;

    @iOSXCUITFindBy(id="Close actions menu")
    private MobileElement closeActions;

    //Actions in action menu
    private final String xpath_delete = "//XCUIElementTypeCell[@name=\"com.owncloud.action.delete\"]";
    private final String xpath_rename = "//XCUIElementTypeCell[@name=\"com.owncloud.action.rename\"]";
    private final String xpath_move = "//XCUIElementTypeCell[@name=\"com.owncloud.action.move\"]";
    private final String xpath_copy = "//XCUIElementTypeCell[@name=\"com.owncloud.action.copy\"]";
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

    public void createFolder() {
        Log.log(Level.FINE, "Starts: create folder");
        wait(3);
        plusButton.click();
        createFolder.click();
    }

    public void executeOperation(String operation, String itemName, String typeItem, String menu){
        Log.log(Level.FINE, "Starts: execute operation: " + operation + " " +
                itemName + " "+ menu);
        if (!isItemInList(itemName)){
            Log.log(Level.FINE, "Searching item... swiping: " + itemName);
            swipe(0.50, 0.90, 0.50, 0.20);
        }
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
        if (!isItemInList(itemName)){
            Log.log(Level.FINE, "Searching item... swiping: " + itemName);
            swipe(0.50, 0.90, 0.50, 0.20);
        }
        driver.findElement(By.id(itemName)).click();
    }

    public boolean isItemInList (String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in list: " + itemName);
        //Plus button to assure we are in the filelist
        waitByXpath(10, "//XCUIElementTypeButton[@name=\"client.file-add\"]");
        return !driver.findElements(By.id(itemName)).isEmpty();
    }

    private void selectItemListActions(String itemName) {
        Log.log(Level.FINE, "Starts: select actions item from list: " + itemName);
        waitByXpath(10, "//XCUIElementTypeCell[@name=\"" + itemName + "\"]");
        driver.findElement(By.id(itemName + " Actions")).click();
    }

    private void selectItemListContextual(String itemName) {
        Log.log(Level.FINE, "Starts: select contextual item from list: " + itemName);
        String itemXpath = "//XCUIElementTypeCell[@name=\"" + itemName + "\"]";
        waitByXpath(10, itemXpath);
        MobileElement listCell = findXpath(itemXpath);
        new TouchAction(driver).longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(listCell))).release().perform();
    }

    private void selectItemListSwipe(String itemName) {
        Log.log(Level.FINE, "Starts: select item from list by swiping: " + itemName);
        String itemXpath = "//XCUIElementTypeCell[@name=\"" + itemName + "\"]";
        waitByXpath(10, itemXpath);
        MobileElement listCell = findXpath(itemXpath);
        swipeElementIOS(listCell, "LEFT");
    }

    public void selectOperationFromActions(String itemName, String operationName, String typeItem) {
        MobileElement operation = null;
        Log.log(Level.FINE, "Starts actions: " + operationName);
        waitByXpath(5, xpath_card);
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
                operation = (MobileElement) driver.findElement(By.id(id_favorite));
                break;
            default:
                break;
        }
        operation.click();
        if (operationName.equals("copy")){
            Log.log(Level.FINE, "Selecting copy to directory");
            findXpath(xpath_copydirectory).click();
        }
    }

    public void selectOperationFromContextual(String itemName, String operationName, String typeItem) {
        MobileElement operation = null;
        Log.log(Level.FINE, "Starts contextual: " + operationName);
        switch (operationName){
            case "delete":
                operation = (MobileElement) driver.findElement(By.id(id_delete));
                break;
            case "rename":
                operation = (MobileElement) driver.findElement(By.id(id_rename));
                break;
            case "move":
                operation = (MobileElement) driver.findElement(By.id(id_move));
                break;
            case "copy":
                operation = (MobileElement) driver.findElement(By.id(id_copy));
                break;
            case "duplicate":
                operation = (MobileElement) driver.findElement(By.id(id_duplicate));
                break;
            case "make available offline":
                operation = (MobileElement) driver.findElement(By.id(id_avoffline));
                break;
            case "share":
            case "edit share":
                operation = (MobileElement) driver.findElement(By.id(id_share));
                break;
            case "share by link":
            case "edit link":
                operation = (MobileElement) driver.findElement(By.id(id_link));
                break;
            case "favorite":
                operation = (MobileElement) driver.findElement(By.id(id_favorite));
                break;
            default:
                break;
        }
        operation.click();
        if (operationName.equals("copy")){
            Log.log(Level.FINE, "Selecting copy to directory");
            driver.findElement(By.xpath(xpath_copydirectory)).click();
        }
    }

    public void closeActions(){
        Log.log(Level.FINE, "Starts: Close Actions Menu");
        closeActions.click();
    }

    public void browse(String folderName){
        Log.log(Level.FINE, "Starts: browse to " + folderName);
        wait(3);
        driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\""+folderName+"\"]")).click();
    }

    public void browseRoot(){
        Log.log(Level.FINE, "Starts: browse to root");
        browseRoot.click();
    }

    public void acceptDeletion(){
        Log.log(Level.FINE, "Starts: accept deletion");
        driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Delete\"]")).click();
    }

    public boolean fileIsMarkedAsAvOffline(String itemName){
        //Action turns to unavailable offline
        String finalName = null;
        boolean menuUnavoffline = false;
        //depending on the location of the file
        if (itemName.contains("/")){  //we have to browse
            finalName = parsePath(itemName);
            Log.log(Level.FINE, "Final file name: " + finalName);
            selectItemListActions(finalName);
            //Option do not appear if the containing folder is av. offline
            menuUnavoffline = driver.findElements(By.id(id_unavoffline)).isEmpty()
                    && driver.findElements(By.id(id_avoffline)).isEmpty();
        } else {
            selectItemListActions(itemName);
            menuUnavoffline = driver.findElement(By.id(id_unavoffline)).isDisplayed();
        }
        Log.log(Level.FINE, "Av. Offline conditions: " + menuUnavoffline);
        return menuUnavoffline;
    }

    public boolean itemIsFavorite(String itemName){
        selectItemListActions(itemName);
        return driver.findElement(By.id(id_unfavorite)).isDisplayed();
    }

    //Turn to item disappear
    public void waitItemDownloaded(String itemName){
        MobileElement element = (MobileElement) driver.findElement(By.xpath(
                "//XCUIElementTypeCell[@name=\""+ itemName +"\"]/XCUIElementTypeOther[2]"));
        waitByIdInvisible(10, element);
    }

    public boolean displayedList(String path, ArrayList<OCFile> listServer){
        boolean found = true;
        parsePath(path); //moving to the folder
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

    private String parsePath(String path){
        Log.log(Level.FINE, "Path: " + path);
        String lastChunk = null;
        String[] route = path.split("/");
        if (route.length > 1) { //we have to browse
            for (int i = 0; i < route.length - 1; i++) {
                Log.log(Level.FINE, "Managing: " + route[i]);
                browse(route[i]);
                lastChunk = route[i+1];
            }
        }
        return lastChunk;
    }
}
