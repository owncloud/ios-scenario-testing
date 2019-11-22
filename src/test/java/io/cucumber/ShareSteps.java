package io.cucumber;

import android.AppiumManager;
import android.FileListPage;
import android.LoginPage;
import android.PublicLinkPage;
import android.SearchShareePage;
import android.SharePage;
import android.WizardPage;

import org.junit.AfterClass;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.ShareAPI;

import static org.junit.Assert.assertTrue;

public class ShareSteps {

    //Involved pages
    protected WizardPage wizardPage = new WizardPage();
    protected LoginPage loginPage = new LoginPage();
    protected SharePage sharePage = new SharePage();
    protected FileListPage fileListPage = new FileListPage();
    protected SearchShareePage searchShareePage = new SearchShareePage();
    protected PublicLinkPage publicLinkPage = new PublicLinkPage();

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();

    protected WebDriverWait wait = new WebDriverWait(AppiumManager.getManager().getDriver(), 5);

    //Appium driver
    //protected AndroidDriver driver;

    private String shareId;

    @Before
    public void setup() throws MalformedURLException {
        /*AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();*/

        System.out.println("BEFORE");
        AppiumManager.getManager().getDriver().launchApp();

        /*wizardPage = new WizardPage();
        loginPage = new LoginPage();
        sharePage = new SharePage();
        fileListPage = new FileListPage();
        searchShareePage = new SearchShareePage();
        publicLinkPage = new PublicLinkPage();*/

        //shareAPI = new ShareAPI();
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
    public void sees_in_file_list(String sharee, String item) throws Throwable {
        shareId = shareAPI.getIdShare(item);
        assertTrue(shareAPI.checkCorrectShared(shareId, item, "0", sharee));

    }
    
    @Then("^public link is created with the name (.+)")
    public void public_link_created(String name) throws Throwable {
        assertTrue(sharePage.isPublicLinkNameInList(name));
        shareId = shareAPI.getIdShare(name);
    }

    @After
    public void tearDown() throws IOException, SAXException, ParserConfigurationException, InterruptedException{
        // Link must be removed via API
        System.out.println("AFTER SHARE STEPS");
        shareAPI.removeShare(shareId);

        AppiumManager.getManager().getDriver().removeApp("com.owncloud.android");
        //AppiumManager.getManager().getDriver().close();
    }

    @AfterClass
    public static void afterclass() throws MalformedURLException{
        System.out.println("AFTER class");
        AppiumManager.getManager().getDriver().quit();
    }

}
