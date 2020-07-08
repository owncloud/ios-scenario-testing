package android;

import org.openqa.selenium.By;

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
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListPage extends CommonPage {

    private String closeselection_id = "com.owncloud.android:id/action_mode_close_button";
    private String fab_id = "fab_expand_menu_button";
    private String createfolder_id = "fab_mkdir";
    private String uploadfab_id = "fab_upload";
    private String uploadoption_id = "files_linear_layout";
    private String downloaded_id = "com.owncloud.android:id/localFileIndicator";
    private String avoffline_id = "com.owncloud.android:id/localFileIndicator";
    private String shareoption_id = "com.owncloud.android:id/action_share_file";
    private String renameoption_id = "com.owncloud.android:id/action_rename_file";
    private String moveoption_id = "com.owncloud.android:id/action_move";
    private String copyoption_id = "com.owncloud.android:id/copy_file";
    private String removeoption_id = "com.owncloud.android:id/action_remove_file";
    private String avofflineoption_id = "com.owncloud.android:id/action_set_available_offline";
    private String syncoption_text = "Refresh account";
    private String syncfileption_id = "com.owncloud.android:id/action_sync_file";
    private String toolbar_id = "toolbar";
    private String progress_id = "com.owncloud.android:id/syncProgressBar";
    private String listfiles_id = "com.owncloud.android:id/list_root";
    private String listcell_id = "com.owncloud.android:id/file_list_constraint_layout";
    private String listitemname_id = "com.owncloud.android:id/Filename";

    private HashMap<String, String> operationsMap = new HashMap<String, String>();

    public FileListPage() {
        super();
        //Filling up the operations to have the key-value with name and id
        operationsMap.put("Share", shareoption_id);
        operationsMap.put("Rename", renameoption_id);
        operationsMap.put("Move", moveoption_id);
        operationsMap.put("Copy", copyoption_id);
        operationsMap.put("Remove", removeoption_id);
        operationsMap.put("Set as available offline", avofflineoption_id);
    }

    public void waitToload(){
        //waitById(30, listfiles_id);
        waitByTextVisible(30, "Documents");
        takeScreenshot("OpenList/fileListLoaded");
    }

    public void createFolder(){
        Log.log(Level.FINE, "Starts: create folder");
        waitById(5, fab_id);
        driver.findElement(MobileBy.id(fab_id)).click();
        driver.findElement(MobileBy.id(createfolder_id)).click();
    }

    public void upload(){
        Log.log(Level.FINE, "Starts: upload");
        waitById(5, fab_id);
        driver.findElement(MobileBy.id(fab_id)).click();
        driver.findElement(MobileBy.id(uploadfab_id)).click();
        driver.findElement(MobileBy.id(uploadoption_id)).click();
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
        return !driver.findElements(By.id(toolbar_id)).isEmpty();
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
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().resourceId(\""+ closeselection_id +"\");")).click();
    }

    public boolean fileIsDownloaded(String fileName) {
        Log.log(Level.FINE, "Starts: Checking file downloaded: " + fileName);
        //Checking file is downloaded inside the device
        try {
            byte[] downloadedFile = driver.pullFile("/sdcard/owncloud/" +
                    LocProperties.getProperties().getProperty("userName1") +
                    "@" +
                    URLEncoder.encode(LocProperties.getProperties().getProperty("hostName"),
                            "UTF-8") + "/" + fileName);
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
        return (element.findElement(By.id(downloaded_id)).isDisplayed());
    }

    public boolean fileIsMarkedAsAvOffline(String itemName){
        //Wait the file to be downloaded
        waitById(15, syncfileption_id);
        MobileElement element = getElementFromFileList(itemName);
        takeScreenshot("FileAvOffline/File_"+itemName+"_AvOffline");
        return (element.findElement(By.id(avoffline_id)).isDisplayed());
    }

    private void selectOperationMenu(String operationName){
        Log.log(Level.FINE, "Starts: Select operation from the menu: " + operationName);
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"More options\");")).click();
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\""+ operationName +"\");")).click();
        takeScreenshot("SelectOperation/SelectOperation_"+operationName);
    }

    public void selectFileUpload(String itemName){
        Log.log(Level.FINE, "Starts: Select file to upload: "+ itemName);
        /*MobileElement hamburger = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"Show roots\");");
        actions.click(hamburger).perform();
        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().description(\"Show roots\");")).click();
        //MobileElement downloadOption = (MobileElement)
        //driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Show roots\");")).click();
        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Downloads\");")).click();
                //driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Downloads\");");
        //actions.click(downloadOption).perform();
        AndroidElement itemToUpload = (AndroidElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+ itemName +"\");");
        actions.click(itemToUpload).perform();
        driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+ itemName +"\");")).click();
        //Give some seconds to perform the action to return to the file list
        //waitByXpath(8, headertext_xpath);*/
    }

    public boolean displayedList(String path, ArrayList<OCFile> listServer){
        boolean found = true;
        parsePath(path); //moving to the folder
        Iterator iterator = listServer.iterator();
        while (iterator.hasNext()){
            OCFile ocfile = (OCFile) iterator.next();
            Log.log(Level.FINE, "Checking item in list: " + ocfile.getName());
            //Server returns the username as value. Here, we skip it
            if (ocfile.getName().equals(LocProperties.getProperties().getProperty("userName1"))) {
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
        List<MobileElement> elementsFileList = driver.findElement(MobileBy.id(listfiles_id))
                .findElements(MobileBy.id(listcell_id));
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
