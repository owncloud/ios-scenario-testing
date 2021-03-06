package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ios.FileListPage;
import ios.FolderPickerPage;
import ios.InputNamePage;
import utils.api.FilesAPI;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListSteps {

    //Involved pages
    protected FileListPage fileListPage = new FileListPage();
    protected InputNamePage inputNamePage = new InputNamePage();
    protected FolderPickerPage folderPickerPage = new FolderPickerPage();

    //APIs to call
    protected FilesAPI filesAPI = new FilesAPI();

    @ParameterType("item|file|folder")
    public String itemtype(String type){
        return type;
    }

    @ParameterType("make available offline|move|copy|delete|duplicate|share by link|edit link|rename|share|edit share|favorite|cut|unfavorite")
    public String operation(String operation){
        return operation;
    }

    @Given("the following items have been created in the account")
    public void items_created_in_account(DataTable table) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String type = rows.get(0);
            String name = rows.get(1);
            Log.log(Level.FINE, type + " " + name);
            if (!filesAPI.itemExist(name)) {
                if (type.equals("folder") || type.equals("item")) {
                    filesAPI.createFolder(name);
                } else if (type.equals("file")) {
                    filesAPI.pushFile(name);
                }
            }
        }
    }

    @Given("the folder {word} contains {int} files")
    public void folder_contains(String folderName, int files)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!filesAPI.itemExist(folderName)) {
            filesAPI.createFolder(folderName);
        }
        for (int i=0; i<files; i++){
            filesAPI.pushFile(folderName+"/file_"+i+".txt");
        }
    }

    @Given("item {word} has been set as favorite")
    public void item_favorited(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        filesAPI.setFavorite(itemName);
    }

    @When("Alice opens a private link pointing to {word} with scheme {word}")
    public void open_private_link(String filePath, String scheme)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        OCFile item = filesAPI.listItems(filePath).get(0);
        String privateLink = fileListPage.getPrivateLink(scheme, item.getPrivateLink());
        fileListPage.openPrivateLink(privateLink);
    }

    @When("Alice opens a private link pointing to non-existing item")
    public void open_fake_private_link()
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.openFakePrivateLink();
    }

    @When("Alice selects the option Create Folder")
    public void create_folder() throws InterruptedException{
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.createFolder();
    }

    @When("Alice selects the option upload from photo gallery")
    public void upload_gallery() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.uploadFromGallery();
    }

    @When("Alice selects a photo")
    public void select_photo() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.selectPhotoGallery();
    }

    @When("Alice selects/sets to/as {operation} the {itemtype} {word} using the {word} menu")
    public void select_item_to_some_operation(String operation, String typeItem, String itemName, String menu)
            throws InterruptedException {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.refreshBySwipe();
        fileListPage.executeOperation(operation, itemName, typeItem, menu);
    }

    @When ("Alice selects {word} as target folder of the {word} operation")
    public void select_target_folder(String targetFolder, String operation) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        folderPickerPage.selectFolder(targetFolder);
        folderPickerPage.accept(operation);
    }

    @When("Alice confirms the deletion")
    public void accept_deletion(){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.acceptDeletion();
    }

    @When("Alice sets {word} as new name")
    public void set_new_name(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        inputNamePage.setItemName(itemName);
    }

    @When("Alice closes the Actions menu")
    public void close_actions_menu() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.closeActions();
    }

    @When("Alice opens the Actions menu of {word}")
    public void open_actions_menu(String itemName) throws InterruptedException{
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.selectItemListActions(itemName);
    }

    @When("Alice browses into folder {word}")
    public void browses_into(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.browse(itemName);
    }

    @When("Alice browses to root folder")
    public void browses_root() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.browseRoot();
    }

    @When("Alice selects to paste into the folder")
    public void paste_item() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.openThreeDotButton();
        fileListPage.pasteAction();
        fileListPage.browseRoot();
    }

    @Then("Alice should see {word} in the filelist")
    public void original_item_filelist(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(fileListPage.isItemInList(itemName));
        assertTrue(filesAPI.itemExist(itemName));
    }

    @Then("{itemtype} {word} is opened in the app")
    public void original_is_opened(String itemType, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(fileListPage.itemOpened(itemType, itemName));
    }

    @Then("Alice should see the photo in the filelist")
    public void photo_in_filelist()
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        ArrayList<OCFile> list = filesAPI.listItems("");
        String fileUploaded = fileListPage.photoUploaded(list);
        assertFalse(fileUploaded.isEmpty());
    }

    @Then("Alice should see {word} 2 in the filelist")
    public void original_item_filelist_string(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(fileListPage.isItemInList(itemName + " 2"));
        assertTrue(filesAPI.itemExist(itemName+ " 2"));
    }

    @Then("Alice should see {word} inside the folder {word}")
    public void item_inside_folder(String itemName, String targetFolder) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.browse(targetFolder);
        fileListPage.isItemInList(itemName);
        assertTrue(filesAPI.itemExist(targetFolder+"/"+itemName));
        fileListPage.browseRoot();
    }

    @Then("Alice should not see {word} in the filelist anymore")
    public void item_not_in_list(String itemName) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(fileListPage.isItemInList(itemName));
        assertFalse(filesAPI.itemExist(itemName));
    }

    @Then("Alice should see the item {word} as av.offline")
    public void item_as_avoffline(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(fileListPage.fileIsMarkedAsAvOffline(itemName));
    }

    @Then("Alice should not see the item {word} as av.offline")
    public void item_not_avoffline(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(fileListPage.fileIsMarkedAsAvOffline(itemName));
    }

    @Then("the list of files in {word} folder should match with the server")
    public void list_matches_server(String path)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + path);
        ArrayList<OCFile> listServer = filesAPI.listItems(path);
        assertTrue(fileListPage.displayedList(path, listServer));
    }

    @Then("{itemtype} {word} should be set as favorite")
    public void item_is_now_favorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        assertTrue(fileListPage.itemIsFavorite(itemName));
        assertTrue(filesAPI.isFavorite(itemName));
    }

    @Then("{itemtype} {word} should be set as unfavorite")
    public void item_is_now_unfavorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        assertFalse(fileListPage.itemIsFavorite(itemName));
        assertFalse(filesAPI.isFavorite(itemName));
    }

    @Then("Alice should see a link resolution error")
    public void link_resolution_error()
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(fileListPage.privateLinkFailed());
    }

    @Then("Alice should see a duplicated item error")
    public void folder_creation_error()
            throws Throwable{
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(inputNamePage.errorDuplicated());
    }

    @Then("{word} action should not be allowed")
    public void action_not_allowed(String action)
            throws Throwable{
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
            if (action.equals("copy")){
                assertFalse(folderPickerPage.actionEnabled("Copy here"));
            } else if (action.equals("move")){
                assertFalse(folderPickerPage.actionEnabled("Move here"));
            }
    }
}
