package io.cucumber;

import android.FileListPage;
import android.FolderPickerPage;
import android.InputNamePage;
import android.RemoveDialogPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.FilesAPI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileListSteps {

    //Involved pages
    protected FileListPage fileListPage = new FileListPage();
    protected InputNamePage inputNamePage = new InputNamePage();
    protected FolderPickerPage folderPickerPage = new FolderPickerPage();
    protected RemoveDialogPage removeDialogPage = new RemoveDialogPage();

    //APIs to call
    protected FilesAPI filesAPI = new FilesAPI();

    @When("I select the option Create Folder")
    public void i_select_create_folder() {
        fileListPage.createFolder();
    }

    @When("I select the folder (.+) to (.+)")
    public void i_select_folder_to_some_operation(String itemName, String operation) {
        filesAPI.createFolder(itemName);
        switch (operation){
            case "rename":
                fileListPage.renameAction(itemName);
                break;
            case "delete":
                fileListPage.deleteAction(itemName);
                break;
            case "move":
                fileListPage.moveAction(itemName);
                break;
            case "copy":
                fileListPage.copyAction(itemName);
                break;
        }
    }

    @When ("I select (.+) as target folder")
    public void i_select_target_folder(String targetFolder) {
        folderPickerPage.selectFolder(targetFolder);
        folderPickerPage.accept();
    }

    @When("I accept the deletion")
    public void i_accept_the_deletion(){
        removeDialogPage.removeAll();
    }

    @When("I set (.+) as name")
    public void i_set_new_name(String itemName) {
        inputNamePage.setItemName(itemName);
    }

    @Then("I see (.+) in my file list$")
    public void i_see_the_item(String itemName) {
        //Get the last token of the item path
        assertTrue(fileListPage.isItemInList(itemName.substring(itemName.lastIndexOf('/')+1)));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("I do not see (.+) in my file list")
    public void i_do_not_see_the_item(String itemName) {
        assertFalse(fileListPage.isItemInList(itemName));
        assertFalse(filesAPI.itemExist(itemName));
    }

    @Then("I see (.+) inside the folder (.+)")
    public void i_see_item_in_folder(String itemName, String targetFolder) {
        fileListPage.browse(targetFolder);
        i_see_the_item(targetFolder+"/"+itemName);
    }

    @Then("I see (.+) in my file list as original")
    public void i_see_original_the_item(String itemName) {
        //Copy keeps the selection mode. To improve.
        fileListPage.closeSelectionMode();
        assertTrue(fileListPage.isItemInList(itemName.substring(itemName.lastIndexOf('/')+1)));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
    }

}
