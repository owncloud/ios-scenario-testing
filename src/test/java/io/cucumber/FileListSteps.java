package io.cucumber;

import android.AppiumManager;
import android.FileListPage;
import android.InputNamePage;
import android.LoginPage;
import android.WizardPage;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.FilesAPI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileListSteps {

    //Involved pages
    protected WizardPage wizardPage;
    protected LoginPage loginPage;
    protected FileListPage fileListPage;
    protected InputNamePage inputNamePage;

    //APIs to call
    protected FilesAPI filesAPI;

    //Appium driver
    protected AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();

        wizardPage = new WizardPage(driver);
        loginPage = new LoginPage(driver);
        fileListPage = new FileListPage(driver);
        inputNamePage = new InputNamePage(driver);

        filesAPI = new FilesAPI();
    }

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

    @After
    public void tearDown() {
        driver.removeApp("com.owncloud.android");
        driver.quit();
    }
}
