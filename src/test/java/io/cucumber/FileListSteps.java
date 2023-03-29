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
import utils.entities.OCFile;
import utils.log.Log;

public class FileListSteps {

    private World world;

    public FileListSteps(World world) {
        this.world = world;
    }

    @ParameterType("item|file|folder")
    public String itemtype(String type){
        return type;
    }

    @ParameterType("make available offline|move|copy|delete|duplicate|share by link|edit link|rename|share|edit share|favorite|cut|unfavorite")
    public String operation(String operation){
        return operation;
    }

    @ParameterType("Favorites|Available Offline|Public Links|Shared with you")
    public String collection(String type){
        return type;
    }

    @Given("the following items have been created in the account")
    public void items_created_in_account(DataTable table) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String type = rows.get(0);
            String name = rows.get(1);
            if (!world.filesAPI.itemExist(name)) {
                if (type.equals("folder") || type.equals("item")) {
                    world.filesAPI.createFolder(name);
                } else if (type.equals("file")) {
                    world.filesAPI.pushFile(name);
                }
            }
        }
    }

    @Given("the folder {word} contains {int} files")
    public void folder_contains(String folderName, int files)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!world.filesAPI.itemExist(folderName)) {
            world.filesAPI.createFolder(folderName);
        }
        for (int i=0; i<files; i++){
            world.filesAPI.pushFile(folderName+"/file_"+i+".txt");
        }
    }

    @Given("item {word} has been set as favorite")
    public void item_favorited(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.filesAPI.setFavorite(itemName);
    }

    @Given("item {word} is visible")
    public void item_is_visible(String itemName){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.itemInScreen(itemName);
    }

    @When("Alice opens a private link pointing to {word} with scheme {word}")
    public void open_private_link(String filePath, String scheme)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        OCFile item = world.filesAPI.listItems(filePath).get(0);
        String privateLink = world.fileListPage.getPrivateLink(scheme, item.getPrivateLink());
        world.fileListPage.openPrivateLink(privateLink);
    }

    @When("Alice opens a private link pointing to non-existing item")
    public void open_fake_private_link()
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openFakePrivateLink();
    }

    @When("Alice selects the option Create Folder")
    public void create_folder() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.createFolder();
    }

    @When("Alice selects the option upload from photo gallery")
    public void upload_gallery() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.uploadFromGallery();
    }

    @When("Alice selects a photo")
    public void select_photo() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.selectPhotoGallery();
    }

    @When("Alice selects/sets to/as {operation} the {itemtype} {word} using the {word} menu")
    public void select_item_to_some_operation(String operation, String typeItem, String itemName, String menu)
            throws InterruptedException {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.refreshBySwipe();
        world.fileListPage.executeOperation(operation, itemName, typeItem, menu);
    }

    @When ("Alice selects {word} as target folder of the {word} operation")
    public void select_target_folder(String targetFolder, String operation) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.folderPickerPage.selectFolder(targetFolder, operation);
        world.folderPickerPage.accept(operation);
    }

    @When("Alice confirms the deletion")
    public void accept_deletion(){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.acceptDeletion();
    }

    @When("Alice sets {word} as new name")
    public void set_new_name(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.inputNamePage.setItemName(itemName);
    }

    @When("Alice closes the Actions menu")
    public void close_actions_menu() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.closeActions();
    }

    @When("Alice opens the Actions menu of {word}")
    public void open_actions_menu(String itemName) throws InterruptedException{
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.selectItemListActions(itemName);
    }

    @When("Alice browses into folder {word}")
    public void browses_into(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browse(itemName);
    }

    @When("Alice browses to root folder")
    public void browses_root() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browseRoot();
    }

    @When("Alice selects to paste into the folder")
    public void paste_item() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openThreeDotButton();
        world.fileListPage.pasteAction();
        world.fileListPage.browseRoot();
    }

    @When("Alice opens the {collection} collection of Quick Access")
    public void open_collection_quick_access(String collection) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openCollection(collection);
    }

    @Then("Alice should see {word} in the filelist")
    public void original_item_filelist(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemInList(itemName));
        assertTrue(world.filesAPI.itemExist(itemName));
    }

    @Then("Alice should not see {word} in the filelist anymore")
    public void item_not_in_list(String itemName) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browseRoot();
        assertTrue(world.fileListPage.isNotItemInList(itemName));
        assertFalse(world.filesAPI.itemExist(itemName));
    }

    @Then("Alice should see {word} in Quick Access")
    public void item_in_quickaccess(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemInScreen(itemName));
    }

    @Then("{itemtype} {word} is opened in the app")
    public void original_is_opened(String itemType, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.itemOpened(itemType, itemName));
    }

    @Then("Alice should see the photo in the filelist")
    public void photo_in_filelist()
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        ArrayList<OCFile> list = world.filesAPI.listItems("");
        String fileUploaded = world.fileListPage.photoUploaded(list);
        assertFalse(fileUploaded.isEmpty());
    }

    @Then("Alice should see {word} 2 in the filelist")
    public void original_item_filelist_string(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemInList(itemName + " 2"));
        assertTrue(world.filesAPI.itemExist(itemName+ " 2"));
    }

    @Then("Alice should see {word} inside the folder {word}")
    public void item_inside_folder(String itemName, String targetFolder) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browse(targetFolder);
        assertTrue(world.fileListPage.isItemInList(itemName));
        assertTrue(world.filesAPI.itemExist(targetFolder+"/"+itemName));
    }

    @Then("Alice should see an empty list of files")
    public void empty_list_files() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isListEmpty());
        //assertFalse(filesAPI.itemExist(itemName));
    }

    @Then("Alice should not see {word} in Quick Access")
    public void item_not_quickaccess(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.isItemInScreen(itemName));
    }

    @Then("Alice should see the item {word} as av.offline")
    public void item_as_avoffline(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.fileIsMarkedAsAvOffline(itemName));
    }

    @Then("Alice should not see the item {word} as av.offline")
    public void item_not_avoffline(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.fileIsMarkedAsAvOffline(itemName));
    }

    @Then("the list of files in {word} folder should match with the server")
    public void list_matches_server(String path)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.refreshBySwipe();
        ArrayList<OCFile> listServer = world.filesAPI.listItems(path);
        assertTrue(world.fileListPage.displayedList(path, listServer));
    }

    @Then("{itemtype} {word} should be set as favorite")
    public void item_is_now_favorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase() + ": " + itemName);
        assertTrue(world.fileListPage.itemIsFavorite(itemName));
        assertTrue(world.filesAPI.isFavorite(itemName));
    }

    @Then("{itemtype} {word} should be set as unfavorite")
    public void item_is_now_unfavorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase() + ": " + itemName);
        assertFalse(world.fileListPage.itemIsFavorite(itemName));
        assertFalse(world.filesAPI.isFavorite(itemName));
    }

    @Then("Alice should see a link resolution error")
    public void link_resolution_error() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.privateLinkFailed());
    }

    @Then("Alice should see a duplicated item error")
    public void folder_creation_error() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.inputNamePage.errorDuplicated());
    }

    @Then("{word} action should not be allowed")
    public void action_not_allowed(String action) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (action.equals("copy")){
            assertFalse(world.folderPickerPage.actionEnabled("Copy here"));
        } else if (action.equals("move")){
            assertFalse(world.folderPickerPage.actionEnabled("Move here"));
        }
    }
}
