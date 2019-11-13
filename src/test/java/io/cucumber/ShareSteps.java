package io.cucumber;

import android.AppiumManager;
import android.FileListPage;
import android.LoginPage;
import android.PublicLinkPage;
import android.SearchShareePage;
import android.SharePage;
import android.WizardPage;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.ShareAPI;

import static org.junit.Assert.assertTrue;

public class ShareSteps {

    //Involved pages
    protected WizardPage wizardPage;
    protected LoginPage loginPage;
    protected SharePage sharePage;
    protected FileListPage fileListPage;
    protected SearchShareePage searchShareePage;
    protected PublicLinkPage publicLinkPage;

    //APIs to call
    protected ShareAPI shareAPI;

    //Appium driver
    protected AndroidDriver driver;

    private String shareId;

    @Before
    public void setup() throws MalformedURLException {
        AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();

        wizardPage = new WizardPage(driver);
        loginPage = new LoginPage(driver);
        sharePage = new SharePage(driver);
        fileListPage = new FileListPage(driver);
        searchShareePage = new SearchShareePage(driver);
        publicLinkPage = new PublicLinkPage(driver);

        shareAPI = new ShareAPI();
    }

    @Given("^I am logged$")
    public void i_am_logged() throws Throwable {
        wizardPage.skip();
        loginPage.typeURL();
        loginPage.typeCredentials("user1", "a");
        loginPage.allowPermissions();
    }

    @When("^I select (.+) to share with (.+)$")
    public void i_select_to_share_with(String itemName, String sharee) throws Throwable {
        fileListPage.shareAction(itemName);
        sharePage.addPrivateShare();
        searchShareePage.shareWithUser(sharee);
    }

    @When("^i select (.+) to create link with name (.+)$")
    public void i_select_to_link_with_name(String itemName, String name) throws Throwable {
        fileListPage.shareAction(itemName);
        sharePage.addPublicLink();
        publicLinkPage.createLink(name);
    }

    @Then("^(.+) is shared with (.+)$")
    public void is_shared_with(String itenName, String sharee) throws Throwable {
        assertTrue(sharePage.isItemInList(itenName));
        assertTrue(sharePage.isUserInList(sharee));
    }

    @Then("^(.+) has (.+) in the file list$")
    public void sees_in_file_list(String sharee, String itemPath) throws Throwable {
        shareId = shareAPI.getIdShare(itemPath);
        assertTrue(shareAPI.checkCorrectShared(shareId, itemPath, "0", sharee));
        assertTrue(shareAPI.checkReceivedShare(shareId, itemPath, "0", sharee));

    }

    @Then("^public link is created on (.+) with the name (.+)")
    public void public_link_created(String itemPath, String itemName) throws Throwable {
        assertTrue(sharePage.isPublicLinkNameInList(itemName));
        shareId = shareAPI.getIdShare(itemPath);
    }

    @After
    public void tearDown() throws IOException, SAXException, ParserConfigurationException, InterruptedException{
        // Link must be removed via API
        shareAPI.removeShare(shareId);

        driver.removeApp("com.owncloud.android");
        driver.quit();
    }
}
