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

    //Actions in action menu
    private String xpath_delete = "//XCUIElementTypeCell[@name=\"com.owncloud.action.delete\"]";
    private String xpath_rename = "//XCUIElementTypeCell[@name=\"com.owncloud.action.rename\"]";
    private String xpath_move = "//XCUIElementTypeCell[@name=\"com.owncloud.action.move\"]";
    private String xpath_copy = "//XCUIElementTypeCell[@name=\"com.owncloud.action.copy\"]";
    private String xpath_duplicate = "//XCUIElementTypeCell[@name=\"com.owncloud.action.duplicate\"]";
    private String xpath_avoffline = "//XCUIElementTypeCell[@name=\"com.owncloud.action.makeAvailableOffline\"]";
    private String xpath_unavoffline = "//XCUIElementTypeCell[@name=\"com.owncloud.action.makeUnavailableOffline\"]";
    private String xpath_sharefolder = "//XCUIElementTypeStaticText[@name=\"Share this folder\"]";
    private String xpath_sharefile = "//XCUIElementTypeStaticText[@name=\"Share this file\"]";
    private String xpath_editshare = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[1]";
    private String xpath_sharelink = "//XCUIElementTypeStaticText[@name=\"Links\"]";
    private String xpath_editlink = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[2]";
    private String xpath_card = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]";

    //Actions in contextual menu menu
    private String id_delete = "Delete, Delete";
    private String id_rename = "Rename";
    private String id_move = "Move";
    private String id_copy = "Copy";
    private String id_duplicate = "Duplicate";
    private String id_avoffline = "Make available offline";
    private String id_unavoffline = "com.owncloud.action.makeUnavailableOffline";
    private String id_share = "Sharing";
    private String id_link = "Links";


    public FileListPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitToload(){
    }

    public void createFolder() {
        Log.log(Level.FINE, "Starts: create folder");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        plusButton.click();
        createFolder.click();
    }

    public void executeOperation(String operation, String itemName, String typeItem, String menu){
        Log.log(Level.FINE, "Starts: execute operation: " + operation + " " +
                itemName + " "+ menu);
        waitToload();
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
        MobileElement listCell = (MobileElement) driver.findElement(By.xpath(itemXpath));
        new TouchAction(driver).longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(listCell))).release().perform();
    }

    private void selectItemListSwipe(String itemName) {
        Log.log(Level.FINE, "Starts: select item from list by swiping: " + itemName);
        String itemXpath = "//XCUIElementTypeCell[@name=\"" + itemName + "\"]";
        waitByXpath(10, itemXpath);
        MobileElement listCell = (MobileElement) driver.findElement(By.xpath(itemXpath));
        swipeElementIOS(listCell, "LEFT");
    }

    public void selectOperationFromActions(String itemName, String operationName, String typeItem) {
        MobileElement operation = null;
        Log.log(Level.FINE, "Starts actions: " + operationName);
        waitByXpath(5, xpath_card);
        switch (operationName){
            case "delete":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_delete));
                break;
            case "rename":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_rename));
                break;
            case "move":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_move));
                break;
            case "copy":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_copy));
                break;
            case "duplicate":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_duplicate));
                break;
            case "make available offline":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_avoffline));
                break;
            case "share":
                String xpath_sharetype;
                if (typeItem.equals("folder")) {
                    xpath_sharetype = xpath_sharefolder;
                } else {
                    xpath_sharetype = xpath_sharefile;
                }
                operation = (MobileElement) driver.findElement(By.xpath(xpath_sharetype));
                break;
            case "edit share":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_editshare));
                break;
            case "share by link":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_sharelink));
                break;
            case "edit link":
                operation = (MobileElement) driver.findElement(By.xpath(xpath_editlink));
                break;
            default:
                break;
        }
        operation.click();
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
            default:
                break;
        }
        operation.click();
    }

    public void browse(String folderName){
        Log.log(Level.FINE, "Starts: browse to " + folderName);
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
        selectItemListActions(itemName);
        //Action turns to unavailable offline
        boolean menuUnavoffline = driver.findElement(By.id(id_unavoffline)).isDisplayed();
        Log.log(Level.FINE, "Av. Offline conditions: " + menuUnavoffline);
        return menuUnavoffline;
    }

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
            if (ocfile.getName().equals(LocProperties.getProperties().getProperty("userName1")) ||
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

    private void parsePath(String path){
        String[] route = path.split("/");
        if (route.length > 0) { //we have to browse
            for (int i = 1; i < route.length; i++) {
                browse(route[i]);
            }
        }
    }
}
