/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.tasks;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import e2e.model.OCFile;
import e2e.world.World;

public class FileListTasks {

    private final World world;

    public FileListTasks(World world) {
        this.world = world;
    }

    public void openItemInList(String itemName) {
        world.fileListPage().refreshBySwipe();
        world.fileListPage().openItemInList(itemName);
    }

    public void openCardMenu(String itemName) {
        world.fileListPage().openCard(itemName);
    }

    public void openPrivateLink(String filePath, String scheme)
            throws IOException, ParserConfigurationException, SAXException {
        OCFile item = world.filesAPI().listItems(filePath, "Alice").get(0);
        String privateLink = world.fileListPage().getPrivateLink(scheme, item.getPrivateLink());
        world.fileListPage().openPrivateLink(privateLink);
    }

    public void openPrivateLinkShared(String fileName, String scheme)
            throws IOException, ParserConfigurationException, SAXException {
        ArrayList<OCFile> listShared = world.filesAPI().listShared();
        OCFile item = listShared.stream()
                .filter(f -> f.getName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("File not found in shared list: " + fileName));
        String privateLink = world.fileListPage().getPrivateLink(scheme, item.getPrivateLink());
        world.fileListPage().openPrivateLink(privateLink);
    }

    public void openFakePrivateLink() {
        world.fileListPage().openFakePrivateLink();
    }

    public void createFolder() {
        world.fileListPage().createFolder();
    }

    public void createShortcut() {
        world.fileListPage().createShortcut();
    }

    public void uploadFromGallery() {
        world.fileListPage().uploadFromGallery();
    }

    public void selectPhoto(int items) {
        world.uploadsPage().selectPhotoGallery(items);
    }

    public void selectItemToOperation(String operation, String typeItem, String itemName, String menu) {
        world.fileListPage().refreshBySwipe();
        world.fileListPage().executeOperation(operation, itemName, typeItem, menu);
    }

    public void selectTargetFolder(String targetFolder, String operation) {
        world.folderPickerPage().selectFolder(targetFolder, operation);
        world.folderPickerPage().accept(operation);
    }

    public void acceptDeletion() {
        world.fileListPage().acceptDeletion();
    }

    public void setNewName(String itemName) {
        world.inputNamePage().setItemName(itemName);
    }

    public void closeActionsMenu(String menu) {
        if (menu.equals("Actions")) {
            world.fileListPage().closeActions();
        }
    }

    public void openActionsMenu(String itemName) {
        world.fileListPage().selectItemListActions(itemName);
    }

    public void browsesInto(String itemName) {
        world.fileListPage().browse(itemName);
    }

    public void browsesRoot() {
        world.fileListPage().browseRoot();
    }

    public void pasteItem() {
        world.fileListPage().openThreeDotButton();
        world.fileListPage().pasteAction();
        world.fileListPage().browseRoot();
    }

    public void openSidebar() {
        world.fileListPage().openSidebar();
    }

    public void openCollectionQuickAccess(String collection) {
        world.fileListPage().openCollection(collection);
    }

    public void openItemInSidebar(String itemName) {
        world.fileListPage().openItemSidebar(itemName);
    }

    public void openQuickAccessOption(String type) {
        world.fileListPage().openQuickAccessOption(type + " ");
    }

    public void createFolderInFolderPicker(String targetFolder, String operation) {
        world.folderPickerPage().selectSpace(operation);
        world.folderPickerPage().createFolder();
        world.inputNamePage().setItemName(targetFolder);
        world.folderPickerPage().selectFolder(targetFolder);
        world.folderPickerPage().accept(operation);
    }

    public void createShortcutWithFields(String shortcutType, String targetFile, String shortcutName) {
        if (shortcutType.equals("web")) {
            world.shortcutPage().createShortcutWeb(targetFile, shortcutName);
        } else {
            world.shortcutPage().createShortcutFile(targetFile, shortcutName);
        }
    }

    public void openLink() {
        world.fileListPage().openShortcutLink();
    }

    public void serverSideSearch(String searchType, String pattern) {
        world.fileListPage().serverSideSearch(searchType, pattern);
    }
}
