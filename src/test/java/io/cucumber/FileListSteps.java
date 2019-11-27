package io.cucumber;

import android.FileListPage;
import android.InputNamePage;
import android.LoginPage;
import android.WizardPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.FilesAPI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileListSteps {

    //Involved pages
    protected WizardPage wizardPage = new WizardPage();
    protected LoginPage loginPage = new LoginPage();
    protected FileListPage fileListPage = new FileListPage();
    protected InputNamePage inputNamePage = new InputNamePage();

    //APIs to call
    protected FilesAPI filesAPI = new FilesAPI();

    @When("I select the option Create Folder")
    public void i_select_create_folder() throws Throwable {
        fileListPage.createFolder();
    }

    @When("I select the item (.+) to rename")
    public void i_select_item_to_rename(String itemName) throws Throwable {
        filesAPI.createFolder(itemName);
        fileListPage.renameAction(itemName);
    }

    @When("^I set (.+) as name")
    public void i_set_new_name(String itemName) throws Throwable {
        inputNamePage.setItemName(itemName);
    }

    @Then("^I see (.+) in my file list$")
    public void i_see_the_item(String itemName) throws Throwable {
        assertTrue(fileListPage.isItemInList(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("^I do not see (.+) in my file list$")
    public void i_do_not_see_the_item(String itemName) throws Throwable {
        assertFalse(fileListPage.isItemInList(itemName));
    }
}
