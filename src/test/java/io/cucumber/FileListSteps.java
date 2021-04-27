package io.cucumber;

import android.DetailsPage;
import android.FileListPage;
import android.FolderPickerPage;
import android.InputNamePage;
import android.RemoveDialogPage;

import net.thucydides.core.steps.StepEventBus;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.FilesAPI;
import utils.entities.OCFile;
import utils.log.Log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileListSteps {

    //Involved pages
    protected FileListPage fileListPage = new FileListPage();
    protected InputNamePage inputNamePage = new InputNamePage();
    protected FolderPickerPage folderPickerPage = new FolderPickerPage();
    //protected RemoveDialogPage removeDialogPage = new RemoveDialogPage();
    //protected DetailsPage detailsPage = new DetailsPage();

    //APIs to call
    protected FilesAPI filesAPI = new FilesAPI();

    @Given("^the following items have been created in the account$")
    public void item_exists(DataTable table) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        List<String> listItems = (List<String>) table.asList();
        Iterator iterator = listItems.iterator();
        while(iterator.hasNext()) {
            String itemName = (String)iterator.next();
            if (!filesAPI.itemExist(itemName)) {
                filesAPI.createFolder(itemName);
            }
        }
    }

    @When("^user selects the option Create Folder$")
    public void i_select_create_folder() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.createFolder();
    }

    @When("^user selects to (.+) the (file|folder|item) (.+) using the (.+) menu$")
    public void i_select_item_to_some_operation(String operation, String typeItem, String itemName, String menu) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.executeOperation(operation, itemName, typeItem, menu);
    }

    @When ("^user selects (.+) as target folder of the (.+)$")
    public void i_select_target_folder(String targetFolder, String operation) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        folderPickerPage.selectFolder(targetFolder);
        folderPickerPage.accept(operation);
    }

    @When("^user confirms the deletion$")
    public void i_accept_the_deletion(){
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        fileListPage.acceptDeletion();
    }

    @When("^user sets (.+) as new name$")
    public void i_set_new_name(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        inputNamePage.setItemName(itemName);
    }

    /*@Then("^user should see (.+) in the filelist$")
    public void i_see_the_item(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(fileListPage.isItemInList(itemName));
        filesAPI.removeItem(itemName);
    }*/

    @Then("^user should see (.+) in the filelist$")
    public void i_see_original_the_item(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.waitToload();
        assertTrue(fileListPage.isItemInList(itemName));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("^user should see (.+) inside the folder (.+)$")
    public void i_see_item_in_folder(String itemName, String targetFolder) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.browse(targetFolder);
        fileListPage.isItemInList(itemName);
        assertTrue(filesAPI.itemExist(targetFolder+"/"+itemName));
        filesAPI.removeItem(targetFolder+"/"+itemName);
    }

    @Then("^user should not see (.+) in the filelist anymore$")
    public void i_do_not_see_the_item(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertFalse(fileListPage.isItemInList(itemName));
        assertFalse(filesAPI.itemExist(itemName));
    }

    @Then("^user should see the item (.+) as av.offline$")
    public void item_marked_as_avOffline(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(fileListPage.fileIsMarkedAsAvOffline(itemName));
    }

    /*@Then("^user sees the detailed information: (.+), (.+), and (.+)$")
    public void preview_in_screen(String itemName, String type, String size) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName()  + ": " + itemName);
        detailsPage.removeShareSheet();
        assertEquals(detailsPage.getName(), itemName);
        assertEquals(detailsPage.getSize(), size);
        assertEquals(detailsPage.getType(), type);
        detailsPage.backListFiles();
    }

    @Then("^the item (.+) is marked as downloaded$")
    public void item_marked_as_downloaded(String itemName) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        assertTrue(fileListPage.fileIsMarkedAsDownloaded(itemName));
    }

    @Then("^the item (.+) is opened and previewed$")
    public void item_opened_previewed(String itemName) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        assertTrue(detailsPage.itemPreviewed());
    }

    @Then("^the list of files in (.+) folder matches with the server$")
    public void list_matches_server(String path) throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + path);
        fileListPage.waitToload();
        ArrayList<OCFile> listServer = filesAPI.listItems(path);
        assertTrue(fileListPage.displayedList(path, listServer));
    }*/
}
