/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.steps;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import e2e.support.log.StepLogger;
import e2e.world.World;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FileListSteps {

    private final World world;

    public FileListSteps(World world) {
        this.world = world;
    }

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

    @ParameterType("Quick Access|filelist|shared with me|search|shared with others|shared by link")
    public String typeOfList(String type) {
        return type;
    }

    @ParameterType("web|file")
    public String shortcutType(String type) {
        return type;
    }

    @ParameterType("file|pdf|image|audio|video|txt")
    public String fileType(String type) {
        return type;
    }

    @ParameterType("content|name and contents")
    public String search(String type) {
        return type;
    }

    @ParameterType("(?: not)?")
    public String typePosNeg(String type) {
        return type == null ? "" : type;
    }

    @Given("the following items have been created in {word} account")
    public void items_created_in_account(String userName, DataTable table) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.fileListPreconditions().itemsCreatedInAccount(userName, rows);
    }

    @Given("the folder {word} contains {int} files")
    public void folder_contains(String folderName, int files) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListPreconditions().folderContains(folderName, files);
    }

    @Given("item {word} has been set as favorite")
    public void item_favorited(String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListPreconditions().itemFavorited(itemName);
    }

    @When("Alice opens the {itemtype} {word}")
    public void open_item_list(String itemType, String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openItemInList(itemName);
    }

    @When("Alice opens the action menu of {itemtype} {word}")
    public void open_actions_menu(String itemType, String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openCardMenu(itemName);
    }

    @When("Alice opens a private link pointing to {word} with scheme {word}")
    public void open_private_link(String filePath, String scheme) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openPrivateLink(filePath, scheme);
    }

    @When("Alice opens a private link pointing to shared {word} with scheme {word}")
    public void open_private_link_shared(String fileName, String scheme) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openPrivateLinkShared(fileName, scheme);
    }

    @When("Alice opens a private link pointing to non-existing item")
    public void open_fake_private_link() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openFakePrivateLink();
    }

    @When("Alice selects the option Create Folder")
    public void create_folder() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().createFolder();
    }

    @When("Alice selects the option Create Shortcut")
    public void create_shortcut() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().createShortcut();
    }

    @When("Alice selects the option upload from photo gallery")
    public void upload_gallery() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().uploadFromGallery();
    }

    @When("Alice selects {int} photo")
    public void select_photo(int items) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().selectPhoto(items);
    }

    @When("Alice selects/sets to/as {operation} the {itemtype} {word} using the {word} menu")
    public void select_item_to_some_operation(String operation, String typeItem, String itemName, String menu) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().selectItemToOperation(operation, typeItem, itemName, menu);
    }

    @When("Alice selects {word} as target folder of the {word} operation")
    public void select_target_folder(String targetFolder, String operation) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().selectTargetFolder(targetFolder, operation);
    }

    @When("Alice confirms the deletion")
    public void accept_deletion() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().acceptDeletion();
    }

    @When("Alice sets {word} as new name")
    public void set_new_name(String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().setNewName(itemName);
    }

    @When("Alice closes the {word} menu")
    public void close_actions_menu(String menu) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().closeActionsMenu(menu);
    }

    @When("Alice opens the Actions menu of {word}")
    public void open_actions_menu(String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openActionsMenu(itemName);
    }

    @When("Alice browses into folder {word}")
    public void browses_into(String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().browsesInto(itemName);
    }

    @When("Alice browses to root folder")
    public void browses_root() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().browsesRoot();
    }

    @When("Alice selects to paste into the folder")
    public void paste_item() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().pasteItem();
    }

    @When("Alice opens the sidebar")
    public void open_sidebar() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openSidebar();
    }

    @When("Alice opens the {collection} collection of Quick Access")
    public void open_collection_quick_access(String collection) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openCollectionQuickAccess(collection);
    }

    @When("Alice opens the {itemtype} {typeOfList} in sidebar")
    public void open_item_in_sidebar_type(String itemType, String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openItemInSidebar(itemName);
    }

    @When("Alice opens the folder {word} in sidebar")
    public void open_item_in_sidebar_word(String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openItemInSidebar(itemName);
    }

    @When("Alice selects the following Quick Access")
    public void select_option_quick_access(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        String type = table.asLists().get(0).get(0);
        world.fileListTasks().openQuickAccessOption(type);
    }

    @When("Alice creates new folder {word} in the folder picker to {word} inside")
    public void user_creates_folder_picker(String targetFolder, String operation) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().createFolderInFolderPicker(targetFolder, operation);
    }

    @When("Alice creates a {shortcutType} shortcut with the following fields")
    public void user_creates_shortcut_url(String shortcutType, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.fileListTasks().createShortcutWithFields(shortcutType, fields.get("targetFile"), fields.get("shortcutName"));
    }

    @When("Alice opens the link")
    public void user_opens_link() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().openLink();
    }

    @When("Alice search by {search} in the server files containing {word}")
    public void server_side_search(String searchType, String pattern) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListTasks().serverSideSearch(searchType, pattern);
    }

    @Then("Alice should see {word} in the filelist")
    public void original_item_filelist(String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemInFilelist(itemName);
    }

    @Then("Alice should not see {word} in the filelist anymore")
    public void item_not_in_list(String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemNotInFilelist(itemName);
    }

    @Then("Alice should{typePosNeg} see {word} in {typeOfList}")
    public void item_in_quickaccess(String sense, String itemName, String itemSidebar) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemInQuickAccess(sense, itemName);
    }

    @Then("Alice should{typePosNeg} see {word} in sidebar")
    public void item_in_sidebar(String sense, String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemInSidebar(sense, itemName);
    }

    @Then("{itemtype} {word} is opened in the app")
    public void original_is_opened(String itemType, String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemIsOpened(itemType, itemName);
    }

    @Then("Alice should see {int} photo in the filelist")
    public void photo_in_filelist(int photos) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().photosInFilelist(photos);
    }

    @Then("Alice should see {string} in the filelist")
    public void original_item_filelist_string(String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemInFilelistByString(itemName);
    }

    @Then("Alice should see {word} inside the folder {word}")
    public void item_inside_folder(String itemName, String targetFolder) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemInsideFolder(itemName, targetFolder);
    }

    @Then("Alice should see an empty list of files")
    public void empty_list_files() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().emptyListFiles();
    }

    @Then("Alice should not see {word} in Quick Access")
    public void item_not_quickaccess(String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemNotInQuickAccess(itemName);
    }

    @Then("Alice should{typePosNeg} see the item {word} as av.offline")
    public void item_as_avoffline(String sense, String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemAsAvOffline(sense, itemName);
    }

    @Then("Av. offline option is not available for item {word}")
    public void avoffline_not_available(String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().avOfflineNotAvailable(itemName);
    }

    @Then("the list of files in {word} folder should match with the server")
    public void list_matches_server(String path) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().listMatchesServer(path);
    }

    @Then("{itemtype} {word} should be set as favorite")
    public void item_is_now_favorite(String itemType, String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemIsFavorite(itemName);
    }

    @Then("{itemtype} {word} should be set as unfavorite")
    public void item_is_now_unfavorite(String itemType, String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().itemIsUnfavorite(itemName);
    }

    @Then("Alice should see a link resolution error")
    public void link_resolution_error() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().linkResolutionError();
    }

    @Then("Alice should see a duplicated item error")
    public void folder_creation_error() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().duplicatedItemError();
    }

    @Then("{word} action should not be allowed")
    public void action_not_allowed(String action) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().actionNotAllowed(action);
    }

    @Then("{word} folder is not an active option")
    public void folder_greyed_out(String folderName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().folderGreyedOut(folderName);
    }

    @Then("Alice should see the following error")
    public void error_displayed(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        String error = table.asLists().get(0).get(0);
        world.fileListAssertions().errorDisplayed(error);
    }

    @Then("Alice should see the browser")
    public void user_sees_browser() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().userSeesBrowser();
    }

    @Then("Alice should see the file {word} with {word}")
    public void user_sees_file_and_content(String itemName, String content) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().userSeesFileAndContent(itemName, content);
    }

    @Then("the {fileType} {word} should be opened and previewed")
    public void file_should_be_opened_and_previewed(String type, String itemName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().fileShouldBeOpenedAndPreviewed(type, itemName);
    }

    @Then("Alice should see the menu with the options to open the file in an external application")
    public void menuOptionsExternalApplication() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().menuOptionsExternalApplication();
    }

    @Then("The Open In option is not available")
    public void menuOptionsExternalApplicationNotAvailable() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().menuOptionsExternalApplicationNotAvailable();
    }

    @Then("Alice should see no matches")
    public void no_matches_server_side_search() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().noMatchesServerSideSearch();
    }
}
