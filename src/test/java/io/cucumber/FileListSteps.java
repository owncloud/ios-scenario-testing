package io.cucumber;

import android.AppiumManager;
import android.CreateFolderPage;
import android.FileListPage;
import android.LoginPage;
import android.WizardPage;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.ShareAPI;

public class FileListSteps {

    //Involved pages
    protected WizardPage wizardPage;
    protected LoginPage loginPage;
    protected FileListPage fileListPage;
    protected CreateFolderPage createFolderPage;

    //APIs to call
    protected ShareAPI shareAPI;

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
        createFolderPage = new CreateFolderPage(driver);

        shareAPI = new ShareAPI();
    }

    @When("I select the option Create Folder")
    public void i_select_create_folder() throws Throwable {
        fileListPage.createFolder();
    }

    @And("^I set (.+) as name of the new folder$")
    public void i_set_foldername(String folderName) throws Throwable {
        createFolderPage.setFolderName(folderName);
    }

    @Then("^I see (.+) in my file list$")
    public void i_see_the_item(String itemName) throws Throwable {
        fileListPage.isItemInList(itemName);
        shareAPI.removeFolder(itemName);
    }

    @After
    public void tearDown() {
        driver.removeApp("com.owncloud.android");
        driver.quit();
    }
}
