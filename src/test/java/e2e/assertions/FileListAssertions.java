/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.assertions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import e2e.model.OCFile;
import e2e.support.log.Log;
import e2e.world.World;
import utils.LocProperties;

public class FileListAssertions {

    private final World world;
    private final String user = LocProperties.getProperties().getProperty("userNameDefault");

    public FileListAssertions(World world) {
        this.world = world;
    }

    public void itemInFilelist(String itemName) throws IOException {
        assertTrue(world.fileListPage().isItemInList(itemName));
        assertTrue(world.filesAPI().itemExist(itemName, user));
    }

    public void itemNotInFilelist(String itemName) throws IOException {
        world.fileListPage().browseRoot();
        assertTrue(world.fileListPage().isNotItemInList(itemName));
        assertFalse(world.filesAPI().itemExist(itemName, user));
    }

    public void itemInQuickAccess(String sense, String itemName) {
        if (sense.isEmpty()) {
            assertTrue(world.fileListPage().isItemInList(itemName));
        } else if (sense.equals("not")) {
            assertFalse(world.fileListPage().isItemInScreen(itemName));
        }
    }

    public void itemInSidebar(String sense, String itemName) {
        if (sense.isEmpty()) {
            assertTrue(world.fileListPage().isItemInSidebar(itemName));
        } else if (sense.equals("not")) {
            assertFalse(world.fileListPage().isItemInSidebar(itemName));
        }
    }

    public void itemIsOpened(String itemType, String itemName) {
        assertTrue(world.fileListPage().isItemOpened(itemType, itemName));
    }

    public void photosInFilelist(int photos)
            throws IOException, ParserConfigurationException, SAXException {
        assertTrue(world.uploadsPage().photoDisplayed(photos));
        ArrayList<OCFile> list = world.filesAPI().listItems("", "Alice");
        assertTrue(world.uploadsPage().photoUploaded(list, photos));
    }

    public void itemInFilelistByString(String itemName) throws IOException {
        assertTrue(world.fileListPage().isItemInList(itemName));
        assertTrue(world.filesAPI().itemExist(itemName, user));
    }

    public void itemInsideFolder(String itemName, String targetFolder) throws IOException {
        world.fileListPage().browse(targetFolder);
        assertTrue(world.fileListPage().isItemInList(itemName));
        assertTrue(world.filesAPI().itemExist(targetFolder + "/" + itemName, user));
    }

    public void emptyListFiles() {
        assertTrue(world.fileListPage().isListEmpty());
    }

    public void itemNotInQuickAccess(String itemName) {
        assertFalse(world.fileListPage().isItemInScreen(itemName));
    }

    public void itemAsAvOffline(String sense, String itemName) {
        if (sense.isEmpty()) {
            assertTrue(world.fileListPage().isMarkedAsAvOffline(itemName));
            world.fileListPage().closeActions();
        } else if (sense.equals(" not")) {
            assertFalse(world.fileListPage().isMarkedAsAvOffline(itemName));
        }
    }

    public void avOfflineNotAvailable(String itemName) {
        assertFalse(world.fileListPage().isAvOfflineAvailable(itemName));
    }

    public void listMatchesServer(String path)
            throws IOException, ParserConfigurationException, SAXException {
        world.fileListPage().refreshBySwipe();
        ArrayList<OCFile> listServer = world.filesAPI().listItems(path, "Alice");
        assertTrue(world.fileListPage().displayedList(path, listServer));
    }

    public void itemIsFavorite(String itemName)
            throws IOException, ParserConfigurationException, SAXException {
        assertTrue(world.fileListPage().itemIsFavorite(itemName));
        assertTrue(world.filesAPI().isFavorite(itemName));
    }

    public void itemIsUnfavorite(String itemName)
            throws IOException, ParserConfigurationException, SAXException {
        assertFalse(world.fileListPage().itemIsFavorite(itemName));
        assertFalse(world.filesAPI().isFavorite(itemName));
    }

    public void linkResolutionError() {
        assertTrue(world.fileListPage().privateLinkFailed());
    }

    public void duplicatedItemError() {
        assertTrue(world.inputNamePage().errorDuplicated());
    }

    public void actionNotAllowed(String action) {
        if (action.equals("copy")) {
            assertFalse(world.folderPickerPage().actionEnabled("Copy here"));
        } else if (action.equals("move")) {
            assertFalse(world.folderPickerPage().actionEnabled("Move here"));
        }
    }

    public void folderGreyedOut(String folderName) {
        assertTrue(world.folderPickerPage().isItemEnabled(folderName));
    }

    public void errorDisplayed(String error) {
        Log.log(Level.FINE, "Error message to check: " + error);
        assertTrue(world.fileListPage().isTextInScreen(error));
    }

    public void userSeesBrowser() {
        assertTrue(world.shortcutPage().isBrowserVisible());
    }

    public void userSeesFileAndContent(String itemName, String content) {
        assertTrue(world.previewPage().isTextFilePreviewed(itemName) &&
                world.previewPage().isTextPreviewed(content));
    }

    public void fileShouldBeOpenedAndPreviewed(String type, String itemName) {
        switch (type) {
            case "file" -> assertTrue(world.previewPage().isTextFilePreviewed(itemName));
            case "image" -> assertTrue(world.previewPage().isImagePreviewed(itemName));
            case "pdf" -> assertTrue(world.previewPage().isPdfPreviewed(itemName));
        }
    }

    public void menuOptionsExternalApplication() {
        assertTrue(world.fileListPage().isExternalApp());
    }

    public void menuOptionsExternalApplicationNotAvailable() {
        assertFalse(world.fileListPage().isOpenInVisible());
    }

    public void noMatchesServerSideSearch() {
        assertTrue(world.fileListPage().areNotMatches());
    }
}
