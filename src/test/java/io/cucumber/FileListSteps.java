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
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListSteps {

    private World world;

    public FileListSteps(World world) {
        this.world = world;
    }

    protected String user = LocProperties.getProperties().getProperty("userNameDefault");

    @ParameterType("item|file|folder|shortcut|option")
    public String itemtype(String type) {
        return type;
    }

    @ParameterType("make available offline|move|copy|delete|duplicate|share by link|edit link|rename|" +
            "share|edit share|favorite|cut|unfavorite|add to the sidebar|remove from the sidebar|open in")
    public String operation(String operation) {
        return operation;
    }

    @ParameterType("Favorites|Available Offline|Public Links|Shared with you|Shared with me")
    public String collection(String type) {
        return type;
    }

    @ParameterType("Quick Access|filelist|shared with me|search")
    public String typeOfList(String type) {
        return type;
    }

    @ParameterType("web|file")
    public String shortcutType(String type) {
        return type;
    }

    @ParameterType("file|pdf|image|audio|video")
    public String fileType(String type) {
        return type;
    }


    @Given("the following items have been created in {word} account")
    public void items_created_in_account(String userName, DataTable table) throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String type = rows.get(0);
            String itemName = rows.get(1);
            if (!world.filesAPI.itemExist(itemName, userName)) {
                switch (type) {
                    case ("folder"):
                    case ("item"): {
                        world.filesAPI.createFolder(itemName, userName);
                        break;
                    }
                    case ("file"): {
                        world.filesAPI.pushFile(itemName, userName);
                        break;
                    }
                    case ("shortcut"): {
                        world.filesAPI.pushFileByMime(itemName, "text/uri-list");
                        break;
                    }
                    case ("image"): {
                        world.filesAPI.pushFileByMime(itemName, "image/jpg");
                        break;
                    }
                    case ("pdf"): {
                        world.filesAPI.pushFileByMime(itemName, "application/pdf");
                        break;
                    }
                    case ("audio"): {
                        world.filesAPI.pushFileByMime(itemName, "audio/mpeg3");
                        break;
                    }
                    case ("video"): {
                        world.filesAPI.pushFileByMime(itemName, "video/mp4");
                        break;
                    }
                }
            }
        }
        world.fileListPage.refreshBySwipe();
    }

    @Given("the folder {word} contains {int} files")
    public void folder_contains(String folderName, int files)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!world.filesAPI.itemExist(folderName, user)) {
            world.filesAPI.createFolder(folderName, user);
        }
        for (int i = 0; i < files; i++) {
            world.filesAPI.pushFile(folderName + "/file_" + i + ".txt", user);
        }
    }

    @Given("item {word} has been set as favorite")
    public void item_favorited(String itemName)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.filesAPI.setFavorite(itemName);
    }

    @When("Alice opens the {itemtype} {word}")
    public void open_item_list(String itemType, String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.refreshBySwipe();
        world.fileListPage.openItemInList(itemName);
    }

    @When("Alice opens the action menu of {itemtype} {word}")
    public void open_actions_menu(String itemType, String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openCard(itemName);
    }

    @When("Alice opens a private link pointing to {word} with scheme {word}")
    public void open_private_link(String filePath, String scheme)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        OCFile item = world.filesAPI.listItems(filePath, "Alice").get(0);
        String privateLink = world.fileListPage.getPrivateLink(scheme, item.getPrivateLink());
        world.fileListPage.openPrivateLink(privateLink);
    }

    @When("Alice opens a private link pointing to shared {word} with scheme {word}")
    public void open_private_link_shared(String fileName, String scheme)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        ArrayList<OCFile> listShared = world.filesAPI.listShared();
        OCFile item = null;
        for (OCFile ocFile : listShared) {
            if (ocFile.getName().equals(fileName)) {
                item = ocFile;
            }
        }
        String privateLink = world.fileListPage.getPrivateLink(scheme, item.getPrivateLink());
        world.fileListPage.openPrivateLink(privateLink);
    }

    @When("Alice opens a private link pointing to non-existing item")
    public void open_fake_private_link() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openFakePrivateLink();
    }

    @When("Alice selects the option Create Folder")
    public void create_folder() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.createFolder();
    }

    @When("Alice selects the option Create Shortcut")
    public void create_shortcut() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.createShortcut();
    }

    @When("Alice selects the option upload from photo gallery")
    public void upload_gallery() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.uploadFromGallery();
    }

    @When("Alice selects {int} photo")
    public void select_photo(int items) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.uploadsPage.selectPhotoGallery(items);
    }

    @When("Alice selects/sets to/as {operation} the {itemtype} {word} using the {word} menu")
    public void select_item_to_some_operation(String operation, String typeItem, String itemName, String menu) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.refreshBySwipe();
        world.fileListPage.executeOperation(operation, itemName, typeItem, menu);
    }

    @When("Alice selects {word} as target folder of the {word} operation")
    public void select_target_folder(String targetFolder, String operation) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.folderPickerPage.selectFolder(targetFolder, operation);
        world.folderPickerPage.accept(operation);
    }

    @When("Alice confirms the deletion")
    public void accept_deletion() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.acceptDeletion();
    }

    @When("Alice sets {word} as new name")
    public void set_new_name(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.inputNamePage.setItemName(itemName);
    }

    @When("Alice closes the {word} menu")
    public void close_actions_menu(String menu) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (menu.equals("Actions")) { //Contextual menu closes itself. Nothing to do in that case
            world.fileListPage.closeActions();
        }
    }

    @When("Alice opens the Actions menu of {word}")
    public void open_actions_menu(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.selectItemListActions(itemName);
    }

    @When("Alice browses into folder {word}")
    public void browses_into(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browse(itemName);
    }

    @When("Alice browses to root folder")
    public void browses_root() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browseRoot();
    }

    @When("Alice selects to paste into the folder")
    public void paste_item() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openThreeDotButton();
        world.fileListPage.pasteAction();
        world.fileListPage.browseRoot();
    }

    @When("Alice opens the sidebar")
    public void open_sidebar() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openSidebar();
    }

    @When("Alice opens the {collection} collection of Quick Access")
    public void open_collection_quick_access(String collection) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openCollection(collection);
    }

    @When("Alice opens the {itemtype} {typeOfList} in sidebar")
    public void open_item_in_sidebar_type(String itemType, String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openItemSidebar(itemName);
    }

    @When("Alice opens the folder {word} in sidebar")
    public void open_item_in_sidebar_word(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openItemSidebar(itemName);
    }

    @When("Alice selects the following Quick Access")
    public void select_option_quick_access(DataTable table) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        String type = listItems.get(0).get(0);
        //For any stupid reason, it's a blank just after the name
        world.fileListPage.openQuickAccessOption(type + " ");
    }

    @When("Alice creates new folder {word} in the folder picker to {word} inside")
    public void user_creates_folder_picker(String targetFolder, String operation) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.folderPickerPage.selectSpace(operation);
        world.folderPickerPage.createFolder();
        world.inputNamePage.setItemName(targetFolder);
        world.folderPickerPage.selectFolder(targetFolder);
        world.folderPickerPage.accept(operation);
    }

    @When("Alice creates a {shortcutType} shortcut with the following fields")
    public void user_creates_shortcut_url(String shortcutType, DataTable table){
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String target = rows.get(0);
            String shortcutName = rows.get(1);
            if (shortcutType.equals("web")) {
                world.shortcutPage.createShortcutWeb(target, shortcutName);
            } else {
                world.shortcutPage.createShortcutFile(target, shortcutName);
            }
        }
    }

    @When("Alice opens the link")
    public void user_opens_link(){
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.openShortcutLink();
    }

    @Then("Alice should see {word} in the filelist")
    public void original_item_filelist(String itemName)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemInList(itemName));
        assertTrue(world.filesAPI.itemExist(itemName, user));
    }

    @Then("Alice should not see {word} in the filelist anymore")
    public void item_not_in_list(String itemName) throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browseRoot();
        assertTrue(world.fileListPage.isNotItemInList(itemName));
        assertFalse(world.filesAPI.itemExist(itemName, user));
    }

    @Then("Alice should see {word} in {typeOfList}")
    public void item_in_quickaccess(String itemName, String itemSidebar) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemInList(itemName));
    }

    @Then("Alice should not see {word} in {typeOfList}")
    public void item_not_in_quickaccess(String itemName, String typeView) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.isItemInScreen(itemName));
    }

    @Then("Alice should see {word} in sidebar")
    public void item_in_sidebar(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemInSidebar(itemName));
    }

    @Then("Alice should not see {word} in sidebar")
    public void item_not_in_sidebar(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.isItemInSidebar(itemName));
    }

    @Then("{itemtype} {word} is opened in the app")
    public void original_is_opened(String itemType, String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemOpened(itemType, itemName));
    }

    @Then("Alice should see {int} photo in the filelist")
    public void photo_in_filelist(int photos)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Checks that pictures are displayed in the app
        assertTrue(world.uploadsPage.photoDisplayed(photos));
        ArrayList<OCFile> list = world.filesAPI.listItems("", "Alice");
        //Checks that pictures are uploaded to the server
        assertTrue(world.uploadsPage.photoUploaded(list, photos));
    }

    @Then("Alice should see {string} in the filelist")
    public void original_item_filelist_string(String itemName)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isItemInList(itemName));
        assertTrue(world.filesAPI.itemExist(itemName, user));
    }

    @Then("Alice should see {word} inside the folder {word}")
    public void item_inside_folder(String itemName, String targetFolder) throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.browse(targetFolder);
        assertTrue(world.fileListPage.isItemInList(itemName));
        assertTrue(world.filesAPI.itemExist(targetFolder + "/" + itemName, user));
    }

    @Then("Alice should see an empty list of files")
    public void empty_list_files() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isListEmpty());
    }

    @Then("Alice should not see {word} in Quick Access")
    public void item_not_quickaccess(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.isItemInScreen(itemName));
    }

    @Then("Alice should see the item {word} as av.offline")
    public void item_as_avoffline(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isMarkedAsAvOffline(itemName));
        close_actions_menu("Actions");
    }

    @Then("Alice should not see the item {word} as av.offline")
    public void item_not_avoffline(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.isMarkedAsAvOffline(itemName));
    }

    @Then("Av. offline option is not available for item {word}")
    public void avoffline_not_available(String itemName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.isAvOfflineAvailable(itemName));
    }

    @Then("the list of files in {word} folder should match with the server")
    public void list_matches_server(String path)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.fileListPage.refreshBySwipe();
        ArrayList<OCFile> listServer = world.filesAPI.listItems(path, "Alice");
        assertTrue(world.fileListPage.displayedList(path, listServer));
    }

    @Then("{itemtype} {word} should be set as favorite")
    public void item_is_now_favorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object() {
                }.getClass().getEnclosingMethod().getName().toUpperCase() + ": " + itemName);
        assertTrue(world.fileListPage.itemIsFavorite(itemName));
        assertTrue(world.filesAPI.isFavorite(itemName));
    }

    @Then("{itemtype} {word} should be set as unfavorite")
    public void item_is_now_unfavorite(String itemType, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object() {
                }.getClass().getEnclosingMethod().getName().toUpperCase() + ": " + itemName);
        assertFalse(world.fileListPage.itemIsFavorite(itemName));
        assertFalse(world.filesAPI.isFavorite(itemName));
    }

    @Then("Alice should see a link resolution error")
    public void link_resolution_error() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.privateLinkFailed());
    }

    @Then("Alice should see a duplicated item error")
    public void folder_creation_error() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.inputNamePage.errorDuplicated());
    }

    @Then("{word} action should not be allowed")
    public void action_not_allowed(String action) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (action.equals("copy")) {
            assertFalse(world.folderPickerPage.actionEnabled("Copy here"));
        } else if (action.equals("move")) {
            assertFalse(world.folderPickerPage.actionEnabled("Move here"));
        }
    }

    @Then("{word} folder is not an active option")
    public void folder_greyed_out(String folderName) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.folderPickerPage.isItemEnabled(folderName));
    }

    @Then("Alice should see the following error")
    public void error_displayed(DataTable table) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        String error = listItems.get(0).get(0);
        Log.log(Level.FINE, "Error message to check: " + error);
        assertTrue(world.fileListPage.isItemInScreen(error));
    }

    @Then("Alice should see the browser")
    public void user_sees_browser() {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.shortcutPage.isBrowserVisible());
    }

    @Then("Alice should see the file {word} with {word}")
    public void user_sees_file_and_content(String itemName, String content) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.previewPage.isTextFilePreviewed(itemName) &&
                world.previewPage.isTextPreviewed(content));
    }

    @Then("the {fileType} {word} should be opened and previewed")
    public void file_should_be_opened_and_previewed(String type, String itemName) {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        switch (type) {
            case ("file"): {
                assertTrue(world.previewPage.isTextFilePreviewed(itemName));
                break;
            }
            case ("image"): {
                assertTrue(world.previewPage.isImagePreviewed(itemName));
                break;
            }
            case ("pdf"): {
                assertTrue(world.previewPage.isPdfPreviewed(itemName));
                break;
            }
        }
    }

    @Then("Alice should see the menu with the options to open the file in an external application")
    public void menuOptionsExternalApplication() {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.fileListPage.isExternalApp());
    }

    @Then("The Open In option is not available")
    public void menuOptionsExternalApplicationNotAvailable() {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.fileListPage.isOpenInVisible());
    }
}
