package io.cucumber;

import android.AppiumManager;
import android.FileListPage;
import android.LoginPage;
import android.PublicLinkPage;
import android.SearchShareePage;
import android.SharePage;
import android.WizardPage;

import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.LocProperties;
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

    private String shareId = null;

    @Given("^user1 is logged$")
    public void i_am_logged() {
        wizardPage.skip();
        loginPage.typeURL();
        loginPage.typeCredentials(LocProperties.getProperties().getProperty("userName1"),
                LocProperties.getProperties().getProperty("passw1"));
    }

    @When("^user selects (.+) to share with (.+)$")
    public void i_select_to_share_with(String itemName, String sharee) throws Throwable {
        fileListPage.executeOperation("Share", itemName);
        sharePage.addPrivateShare();
        searchShareePage.shareWithUser(sharee);
    }

    @When("^user selects (.+) to create link with name (.+)$")
    public void i_select_to_link_with_name(String itemName, String name) throws Throwable {
        fileListPage.executeOperation("Share", itemName);
        sharePage.addPublicLink();
        publicLinkPage.createLink(name);
    }

    @Then("^(.+) is shared with (.+)$")
    public void is_shared_with(String itemName, String sharee) throws Throwable {
        shareId = shareAPI.getIdShare(itemName);
        assertTrue(sharePage.isItemInList(itemName));
        assertTrue(sharePage.isUserInList(sharee));
        assertTrue(shareAPI.checkCorrectShared(shareId, "0", sharee));
    }

    @Then("^(.+) has (.+) in the file list$")
    public void sees_in_file_list(String sharee, String item) throws Throwable {
        shareId = shareAPI.getIdShare(item);
        assertTrue(shareAPI.checkReceivedShare(shareId, "0", sharee));
        //Remove share
        shareAPI.removeShare(shareId);
    }
    
    @Then("^public link is created on (.+) with the name (.+)")
    public void public_link_created(String itemName, String linkName) throws Throwable {
        assertTrue(sharePage.isPublicLinkNameInList(linkName));
        shareId = shareAPI.getIdShare(itemName);
        // Link must be removed via API;
        shareAPI.removeShare(shareId);
    }

}
