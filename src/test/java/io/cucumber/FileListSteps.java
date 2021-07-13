package io.cucumber;

import ios.FileListPage;
import ios.FolderPickerPage;
import ios.InputNamePage;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.StepEventBus;

import java.io.IOException;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileListSteps {

    //Involved pages
    @Steps
    protected FileListPage fileListPage;

    @Steps
    protected InputNamePage inputNamePage;

    @Steps
    protected FolderPickerPage folderPickerPage;

    //APIs to call
    protected FilesAPI filesAPI = new FilesAPI();

    @Given("^the following items have been created in the account$")
    public void items_created(DataTable table) throws Throwable {
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

    @Given("^the (item|folder|file) (.+) has been created in the account$")
    public void items_created_2(String type, String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        if (!filesAPI.itemExist(itemName)) {
            if (type.equals("folder") || type.equals("item")) {
                filesAPI.createFolder(itemName);
            } else if (type.equals("file"))
                filesAPI.pushFile(itemName);
            }
    }

    @Given("^the folder (.+) contains (.+) files$")
    public void folder_contains(String folderName, int files) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        if (!filesAPI.itemExist(folderName)) {
            filesAPI.createFolder(folderName);
        }
        for (int i=0; i<files; i++){
            filesAPI.pushFile(folderName+"/file_"+i+".txt");
        }
    }

    @When("^(?:.*?) selects the option Create Folder$")
    public void create_folder() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.createFolder();
    }

    @When("^(?:.*?) selects to (.+) the (file|folder|item) (.+) using the (.+) menu$")
    public void select_item_to_some_operation(String operation, String typeItem, String itemName, String menu) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.executeOperation(operation, itemName, typeItem, menu);
    }

    @When ("^(?:.*?) selects (.+) as target folder of the (.+) operation$")
    public void select_target_folder(String targetFolder, String operation) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        folderPickerPage.selectFolder(targetFolder);
        folderPickerPage.accept(operation);
    }

    @When("^(?:.*?) confirms the deletion$")
    public void accept_deletion(){
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        fileListPage.acceptDeletion();
    }

    @When("^(?:.*?) sets (.+) as new name$")
    public void set_new_name(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        inputNamePage.setItemName(itemName);
    }

    @Then("^(?:.*?) should see (.+) in the filelist$")
    public void original_item_filelist(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(fileListPage.isItemInList(itemName));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("^(?:.*?) should see (.+) inside the folder (.+)$")
    public void item_inside_folder(String itemName, String targetFolder) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.browse(targetFolder);
        fileListPage.isItemInList(itemName);
        assertTrue(filesAPI.itemExist(targetFolder+"/"+itemName));
        filesAPI.removeItem(targetFolder+"/"+itemName);
        fileListPage.browseRoot();
    }

    @Then("^(?:.*?) should not see (.+) in the filelist anymore$")
    public void item_not_in_list(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertFalse(fileListPage.isItemInList(itemName));
        assertFalse(filesAPI.itemExist(itemName));
    }

    @Then("^(?:.*?) should see the item (.+) as av.offline$")
    public void item_as_avoffline(String itemName)
            throws IOException {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(fileListPage.fileIsMarkedAsAvOffline(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("^the list of files in (.+) folder should match with the server$")
    public void list_matches_server(String path) throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + path);
        ArrayList<OCFile> listServer = filesAPI.listItems(path);
        assertTrue(fileListPage.displayedList(path, listServer));
    }
}
