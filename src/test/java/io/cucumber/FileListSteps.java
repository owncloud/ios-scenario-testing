package io.cucumber;

import android.DetailsPage;
import android.FileListPage;
import android.FolderPickerPage;
import android.InputNamePage;
import android.RemoveDialogPage;

import java.util.ArrayList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.FilesAPI;
import utils.entities.OCFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileListSteps {

    //Involved pages
    protected FileListPage fileListPage = new FileListPage();
    protected InputNamePage inputNamePage = new InputNamePage();
    protected FolderPickerPage folderPickerPage = new FolderPickerPage();
    protected RemoveDialogPage removeDialogPage = new RemoveDialogPage();
    protected DetailsPage detailsPage = new DetailsPage();

    //APIs to call
    protected FilesAPI filesAPI = new FilesAPI();

    @Given("there is an item called (.+) in the account")
    public void item_in_account(String itemName){
        filesAPI.itemExist(itemName);
    }

    @Given("there is an item called (.+) in the folder Downloads of the device")
    public void push_file_to_device(String itemName){
        fileListPage.pushFile(itemName);
    }

    @When("user selects the option Create Folder")
    public void i_select_create_folder() {
        fileListPage.createFolder();
    }

    @When("user selects the item (.+) to (.+)")
    public void i_select_folder_to_some_operation(String itemName, String operation) {
        filesAPI.createFolder(itemName);
        switch (operation){
            case "rename":
                fileListPage.executeOperation("Rename", itemName);
                break;
            case "delete":
                fileListPage.executeOperation("Remove", itemName);
                break;
            case "move":
                fileListPage.executeOperation("Move", itemName);
                break;
            case "copy":
                fileListPage.executeOperation("Copy" ,itemName);
                break;
            case "download":
                fileListPage.downloadAction(itemName);
                detailsPage.waitFinishedDownload(10);
                break;
            case "av.offline":
                fileListPage.executeOperation("Set as available offline", itemName);
                break;
            case "upload":
                fileListPage.selectFileUpload(itemName);
                break;
        }
    }

    @When ("user selects (.+) as target folder")
    public void i_select_target_folder(String targetFolder) {
        folderPickerPage.selectFolder(targetFolder);
        folderPickerPage.accept();
    }

    @When("user selects the option upload")
    public void i_select_upload() {
        fileListPage.upload();
    }

    @When("user accepts the deletion")
    public void i_accept_the_deletion(){
        removeDialogPage.removeAll();
    }

    @When("user sets (.+) as name")
    public void i_set_new_name(String itemName) {
        inputNamePage.setItemName(itemName);
    }

    @Then("user sees (.+) in the file list$")
    public void i_see_the_item(String itemName) {
        //Get the last token of the item path
        assertTrue(fileListPage.isItemInList(itemName.substring(itemName.lastIndexOf('/')+1)));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("user does not see (.+) in the file list")
    public void i_do_not_see_the_item(String itemName) {
        assertFalse(fileListPage.isItemInList(itemName));
        assertFalse(filesAPI.itemExist(itemName));
    }

    @Then("user sees (.+) inside the folder (.+)")
    public void i_see_item_in_folder(String itemName, String targetFolder) {
        fileListPage.browse(targetFolder);
        i_see_the_item(targetFolder+"/"+itemName);
    }

    @Then("user sees (.+) in the file list as original")
    public void i_see_original_the_item(String itemName) {
        //Copy keeps the selection mode. To improve.
        fileListPage.closeSelectionMode();
        assertTrue(fileListPage.isItemInList(itemName.substring(itemName.lastIndexOf('/')+1)));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("the item (.+) is stored in the device")
    public void item_downloaded(String itemName) {
        assertTrue(fileListPage.fileIsDownloaded(itemName));
    }

    @Then("user sees the detailed information: (.+), (.+), and (.+)")
    public void preview_in_screen(String itemName, String type, String size) {
        detailsPage.closeOpenIn();
        assertEquals(detailsPage.getName(), itemName);
        assertEquals(detailsPage.getSize(), size);
        assertEquals(detailsPage.getType(), type);
        detailsPage.backListFiles();
    }

    @Then("the item (.+) is marked as downloaded")
    public void item_marked_as_downloaded(String itemName) {
        assertTrue(fileListPage.fileIsMarkedAsDownloaded(itemName));
    }

    @Then("user sees the item (.+) as av.offline")
    public void item_marked_as_avOffline(String itemName) {
        assertTrue(fileListPage.fileIsMarkedAsAvOffline(itemName));
    }

    @Then("the item is opened and previewed")
    public void item_opened_previewed(){
        assertTrue(detailsPage.itemPreviewed());
    }

    @Then("the list of files in (.+) folder matches with the server")
    public void list_matches_server(String path){
        ArrayList<OCFile> listServer = filesAPI.listItems(path);
        assertTrue(fileListPage.displayedList(path, listServer));
    }




}
