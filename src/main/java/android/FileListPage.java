package android;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListPage extends CommonPage {

    private String shareoption_id = "com.owncloud.android:id/action_share_file";
    private String renameoption_id = "com.owncloud.android:id/action_rename_file";
    private String moveoption_id = "com.owncloud.android:id/action_move";
    private String copyoption_id = "com.owncloud.android:id/copy_file";
    private String removeoption_id = "com.owncloud.android:id/action_remove_file";
    private String avofflineoption_id = "com.owncloud.android:id/action_set_available_offline";
    private String listcell_id = "com.owncloud.android:id/file_list_constraint_layout";
    private String listitemname_id = "com.owncloud.android:id/Filename";

    @AndroidFindBy(uiAutomator="new UiSelector().resourceId(\"com.owncloud.android:id/action_mode_close_button\");")
    private MobileElement closeSelectionMode;

    @AndroidFindBy(id="fab_expand_menu_button")
    private MobileElement fabButton;

    @AndroidFindBy(id="fab_mkdir")
    private MobileElement createFolder;

    @AndroidFindBy(id="fab_upload")
    private MobileElement uploadOption;

    @AndroidFindBy(id="com.owncloud.android:id/localFileIndicator")
    private MobileElement downloadIndicator;

    @AndroidFindBy(id="com.owncloud.android:id/localFileIndicator")
    private MobileElement avOfflineIndicator;

    @AndroidFindBy(id="com.owncloud.android:id/action_sync_file")
    private MobileElement syncFile;

    @AndroidFindBy(id="toolbar")
    private List<MobileElement> toolbar;

    @AndroidFindBy(id="com.owncloud.android:id/list_root")
    private MobileElement listFiles;

    @AndroidFindBy(id="com.owncloud.android:id/file_list_constraint_layout")
    private MobileElement fileCell;

    @AndroidFindBy(id="com.owncloud.android:id/Filename")
    private MobileElement fileName;

    private HashMap<String, String> operationsMap = new HashMap<String, String>();

    public FileListPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        //Filling up the operations to have the key-value with name and id
        operationsMap.put("share", shareoption_id);
        operationsMap.put("Rename", renameoption_id);
        operationsMap.put("Move", moveoption_id);
        operationsMap.put("Copy", copyoption_id);
        operationsMap.put("Remove", removeoption_id);
        operationsMap.put("Set as available offline", avofflineoption_id);
    }

    public void refreshList(){
        swipe(0.50, 0.20, 0.50, 0.90);
    }

    public void waitToload(){
        try {
            //if list of files is not loaded, we should swipe to get the file list
            waitById(30, listFiles);
        } catch (Exception e) {
            swipe(0.50, 0.20, 0.50, 0.90);
            waitByTextVisible(30, "Documents");
        }
        takeScreenshot("OpenList/fileListLoaded");
    }

    public void createFolder(){
        Log.log(Level.FINE, "Starts: create folder");
        waitById(5, fabButton);
        fabButton.click();
        createFolder.click();
    }

    public void upload(){
        Log.log(Level.FINE, "Starts: upload");
        waitById(5, fabButton);
        fabButton.click();
        uploadOption.click();
    }

    public void pushFile(String itemName){
        Log.log(Level.FINE, "Starts: push file: " + itemName);
        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, LocProperties.getProperties().getProperty("testResourcesPath"));
        File app = new File(appDir, "io/cucumber/example-files/AAA.txt");
        try {
            driver.pushFile("/mnt/sdcard/Download/aaa.txt", app);
        } catch (IOException e){
            Log.log(Level.SEVERE, "IO Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void executeOperation(String operation, String itemName){
        Log.log(Level.FINE, "Starts: execute operation: " + operation + " " + itemName);
        if (!isItemInList(itemName)){
            Log.log(Level.FINE, "Searching item... swiping: " + itemName);
            swipe(0.50, 0.90, 0.50, 0.20);
        }
        selectItemList(itemName);
        selectOperation(operation);
    }

    public void downloadAction(String itemName) {
        Log.log(Level.FINE, "Starts: download action: " + itemName);
        if (!isItemInList(itemName)){
            Log.log(Level.FINE, "Searching item... swiping: " + itemName);
            swipe(0.50, 0.90, 0.50, 0.20);
        }
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\""+ itemName +"\");")).click();
    }

    public boolean isItemInList (String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in list: " + itemName);
        return !driver.findElementsByAndroidUIAutomator(
                "new UiSelector().text(\"" + itemName + "\");").isEmpty();
    }

    public boolean isHeader(){
        return !toolbar.isEmpty();
    }

    public void selectItemList(String itemName) {
        Log.log(Level.FINE, "Starts: select item from list: " + itemName);
        waitByTextVisible(30, itemName);
        MobileElement element = getElementFromFileList(itemName);
        actions.clickAndHold(element).perform();
    }

    public void selectOperation(String operationName) {
        if (driver.findElementsByAndroidUIAutomator(
                "new UiSelector().resourceId(\"" + operationsMap.get(operationName) + "\");").isEmpty()){
            //Operation inside menu, matching by name
            Log.log(Level.FINE, "Operation: " + operationName + " placed in menu");
            selectOperationMenu(operationName);
        } else {
            //Operation in toolbar, matching by id
            Log.log(Level.FINE, "Operation: " + operationName + " placed in toolbar");
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiSelector().resourceId(\""+ operationsMap.get(operationName) +"\");")).click();
        }
    }

    public void browse(String folderName){
        Log.log(Level.FINE, "Starts: browse to " + folderName);
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\""+ folderName +"\");")).click();
    }

    public void closeSelectionMode(){
        Log.log(Level.FINE, "Starts: close selection mode");
        closeSelectionMode.click();
    }

    public boolean fileIsDownloaded(String fileName) {
        Log.log(Level.FINE, "Starts: Checking file downloaded: " + fileName);
        String urlServer = LocProperties.getProperties().getProperty("serverURL");
        String host = urlServer.split("//")[1];
        //Checking file is downloaded inside the device
        try {
            byte[] downloadedFile = driver.pullFile("/sdcard/owncloud/" +
                    LocProperties.getProperties().getProperty("userName1") +
                    "@" +
                    URLEncoder.encode(host, "UTF-8") + "/" + fileName);
            Log.log(Level.FINE, "Checking file in " + downloadedFile.toString());
            return downloadedFile!=null && downloadedFile.length > 0;
        } catch (UnsupportedEncodingException e) {
            Log.log(Level.SEVERE, "Unsupported Encoding Exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean fileIsMarkedAsDownloaded(String itemName){
        //Enforce this.. downloaded file must fit the itemName
        MobileElement element = getElementFromFileList(itemName);
        takeScreenshot("FileDownloaded/File_"+itemName+"_Downloaded");
        return downloadIndicator.isDisplayed();
    }

    public boolean fileIsMarkedAsAvOffline(String itemName){
        //Wait the file to be downloaded
        waitById(15, syncFile);
        MobileElement element = getElementFromFileList(itemName);
        takeScreenshot("FileAvOffline/File_"+itemName+"_AvOffline");
        return avOfflineIndicator.isDisplayed();
    }

    private void selectOperationMenu(String operationName){
        Log.log(Level.FINE, "Starts: Select operation from the menu: " + operationName);
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"More options\");")).click();
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\""+ operationName +"\");")).click();
        takeScreenshot("SelectOperation/SelectOperation_"+operationName);
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
            while (!isItemInList(ocfile.getName()) && !endList(listServer.size())) {
                Log.log(Level.FINE, "Item " + ocfile.getName() + " not found yet. Swiping");
                swipe(0.50, 0.90, 0.50, 0.20);
            }
            if (!isItemInList(ocfile.getName())) {
                Log.log(Level.FINE, "Item " + ocfile.getName() + " is not in the list");
                found = false;
                break;
            }
            //TODO: swipe to the top to start a new loop
        }
        return found;
    }

    private boolean endList (int numberItems) {
        return !driver.findElements(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\"" + Integer.toString(numberItems-1)
                        + " files\");")).isEmpty();
    }

    private void parsePath(String path){
        String[] route = path.split("/");
        if (route.length > 0) { //we have to browse
            for (int i = 1; i < route.length; i++) {
                browse(route[i]);
            }
        }
    }

    private MobileElement getElementFromFileList(String itemName){
        Log.log(Level.FINE, "Starts: searching item in list: " + itemName);
        List<MobileElement> elementsFileList = listFiles.findElements(MobileBy.id(listcell_id));
        takeScreenshot("ElementFileList/SearchItem_"+itemName);
        for (MobileElement element : elementsFileList) {
            if (element.findElement(By.id(listitemname_id)).getText()
                    .equals(itemName)){
                Log.log(Level.FINE, itemName + " found!!");
                return element;
            }
        }
        Log.log(Level.FINE, itemName + " not found");
        return null;
    }
}
