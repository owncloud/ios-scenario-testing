package io.cucumber;

import static org.junit.Assert.assertEquals;
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
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListSteps {

    private World world;

    public FileListSteps(World world) {
        this.world = world;
    }
    protected String user = LocProperties.getProperties().getProperty("userNameDefault");

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

    @Given("the following items have been created in {word} account")
    public void items_created_in_account(String userName, DataTable table) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String type = rows.get(0);
            String itemName = rows.get(1);
            if (!world.getFilesAPI().itemExist(itemName, userName)) {
                if (type.equals("folder") || type.equals("item")) {
                    world.getFilesAPI().createFolder(itemName, userName);
                } else if (type.equals("file")) {
                    world.getFilesAPI().pushFile(itemName, userName);
                }
            }
        }
    }

    @Given("the folder {word} contains {int} files")
    public void folder_contains(String folderName, int files)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!world.getFilesAPI().itemExist(folderName, user)) {
            world.getFilesAPI().createFolder(folderName, user);
        }
        for (int i=0; i<files; i++){
            world.getFilesAPI().pushFile(folderName+"/file_"+i+".txt", user);
        }
    }

    @Given("item {word} has been set as favorite")
    public void item_favorited(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFilesAPI().setFavorite(itemName);
    }

    @When("Alice opens a private link pointing to {word} with scheme {word}")
    public void open_private_link(String filePath, String scheme)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        OCFile item = world.getFilesAPI().listItems(filePath).get(0);
        String privateLink = world.getFileListPage().getPrivateLink(scheme, item.getPrivateLink());
        world.getFileListPage().openPrivateLink(privateLink);
    }

    @When("Alice opens a private link pointing to shared {word} with scheme {word}")
    public void open_private_link_shared(String fileName, String scheme)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        ArrayList<OCFile> listShared = world.getFilesAPI().listShared();
        OCFile item = null;
        for (OCFile ocFile: listShared){
            if (ocFile.getName().equals(fileName)){
                item = ocFile;
            }
        }
        String privateLink = world.getFileListPage().getPrivateLink(scheme, item.getPrivateLink());
        world.getFileListPage().openPrivateLink(privateLink);
    }

    @When("Alice opens a private link pointing to non-existing item")
    public void open_fake_private_link() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().openFakePrivateLink();
    }

    @When("Alice selects the option Create Folder")
    public void create_folder() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().createFolder();
    }

    @When("Alice selects the option upload from photo gallery")
    public void upload_gallery() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().uploadFromGallery();
    }

    @When("Alice selects {int} photo")
    public void select_photo(int items) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getUploadsPage().selectPhotoGallery(items);
    }

    @When("Alice selects/sets to/as {operation} the {itemtype} {word} using the {word} menu")
    public void select_item_to_some_operation(String operation, String typeItem, String itemName, String menu) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().refreshBySwipe();
        world.getFileListPage().executeOperation(operation, itemName, typeItem, menu);
    }

    @When ("Alice selects {word} as target folder of the {word} operation")
    public void select_target_folder(String targetFolder, String operation) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFolderPickerPage().selectFolder(targetFolder, operation);
        world.getFolderPickerPage().accept(operation);
    }

    @When("Alice confirms the deletion")
    public void accept_deletion(){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().acceptDeletion();
    }

    @When("Alice sets {word} as new name")
    public void set_new_name(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getInputNamePage().setItemName(itemName);
    }

    @When("Alice closes the {word} menu")
    public void close_actions_menu(String menu) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (menu.equals("Actions")) { //Contextual menu closes itself. Nothing to do in that case
            world.getFileListPage().closeActions();
        }
    }

    @When("Alice opens the Actions menu of {word}")
    public void open_actions_menu(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().selectItemListActions(itemName);
    }

    @When("Alice browses into folder {word}")
    public void browses_into(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().browse(itemName);
    }

    @When("Alice browses to root folder")
    public void browses_root() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().browseRoot();
    }

    @When("Alice selects to paste into the folder")
    public void paste_item() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().openThreeDotButton();
        world.getFileListPage().pasteAction();
        world.getFileListPage().browseRoot();
    }

    @When("Alice opens the {collection} collection of Quick Access")
    public void open_collection_quick_access(String collection) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().openCollection(collection);
    }

    @When("Alice creates new folder {word} in the folder picker to {word} inside")
    public void user_creates_folder_picker(String targetFolder, String operation) {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFolderPickerPage().selectSpace(operation);
        world.getFolderPickerPage().createFolder();
        world.getInputNamePage().setItemName(targetFolder);
        world.getFolderPickerPage().selectFolder(targetFolder);
        world.getFolderPickerPage().accept(operation);
    }

    @Then("Alice should see {word} in the filelist")
    public void original_item_filelist(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().isItemInList(itemName));
        assertTrue(world.getFilesAPI().itemExist(itemName, user));
    }

    @Then("Alice should not see {word} in the filelist anymore")
    public void item_not_in_list(String itemName) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().browseRoot();
        assertTrue(world.getFileListPage().isNotItemInList(itemName));
        assertFalse(world.getFilesAPI().itemExist(itemName, user));
    }

    @Then("Alice should see {word} in Quick Access")
    public void item_in_quickaccess(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().isItemInScreen(itemName));
    }

    @Then("{itemtype} {word} is opened in the app")
    public void original_is_opened(String itemType, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().itemOpened(itemType, itemName));
    }

    @Then("Alice should see {int} photo in the filelist")
    public void photo_in_filelist(int photos)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        ArrayList<OCFile> list = world.getFilesAPI().listItems("");
        int photosUploaded = world.getUploadsPage().photoUploaded(list);
        assertEquals(photosUploaded, photos);
    }

    @Then("Alice should see {word} 2 in the filelist")
    public void original_item_filelist_string(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().isItemInList(itemName + " 2"));
        assertTrue(world.getFilesAPI().itemExist(itemName+ " 2", user));
    }

    @Then("Alice should see {word} inside the folder {word}")
    public void item_inside_folder(String itemName, String targetFolder) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().browse(targetFolder);
        assertTrue(world.getFileListPage().isItemInList(itemName));
        assertTrue(world.getFilesAPI().itemExist(targetFolder+"/"+itemName, user));
    }

    @Then("Alice should see an empty list of files")
    public void empty_list_files() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().isListEmpty());
    }

    @Then("Alice should not see {word} in Quick Access")
    public void item_not_quickaccess(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getFileListPage().isItemInScreen(itemName));
    }

    @Then("Alice should see the item {word} as av.offline")
    public void item_as_avoffline(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().isMarkedAsAvOffline(itemName));
        close_actions_menu("Actions");
    }

    @Then("Alice should not see the item {word} as av.offline")
    public void item_not_avoffline(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getFileListPage().isMarkedAsAvOffline(itemName));
    }

    @Then("Av. offline option is not available for item {word}")
    public void avoffline_not_available(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getFileListPage().isAvOfflineAvailable(itemName));
    }

    @Then("the list of files in {word} folder should match with the server")
    public void list_matches_server(String path)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().refreshBySwipe();
        ArrayList<OCFile> listServer = world.getFilesAPI().listItems(path);
        assertTrue(world.getFileListPage().displayedList(path, listServer));
    }

    @Then("{itemtype} {word} should be set as favorite")
    public void item_is_now_favorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase() + ": " + itemName);
        assertTrue(world.getFileListPage().itemIsFavorite(itemName));
        assertTrue(world.getFilesAPI().isFavorite(itemName));
    }

    @Then("{itemtype} {word} should be set as unfavorite")
    public void item_is_now_unfavorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase() + ": " + itemName);
        assertFalse(world.getFileListPage().itemIsFavorite(itemName));
        assertFalse(world.getFilesAPI().isFavorite(itemName));
    }

    @Then("Alice should see a link resolution error")
    public void link_resolution_error() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().privateLinkFailed());
    }

    @Then("Alice should see a duplicated item error")
    public void folder_creation_error() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getInputNamePage().errorDuplicated());
    }

    @Then("{word} action should not be allowed")
    public void action_not_allowed(String action) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (action.equals("copy")){
            assertFalse(world.getFolderPickerPage().actionEnabled("Copy here"));
        } else if (action.equals("move")){
            assertFalse(world.getFolderPickerPage().actionEnabled("Move here"));
        }
    }

    @Then("{word} folder is not an active option")
    public void folder_greyed_out(String folderName) {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFolderPickerPage().isItemEnabled(folderName));
    }

    @Then("Alice should see the following error")
    public void error_displayed(DataTable table) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        String error = listItems.get(0).get(0);
        Log.log(Level.FINE, "Error message to check: " + error);
        assertTrue(world.getFileListPage().isItemInScreen(error));
    }
}
