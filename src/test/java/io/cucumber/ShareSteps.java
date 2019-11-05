package io.cucumber;

import android.AppiumManager;
import android.FileListPage;
import android.LoginPage;
import android.SharePage;
import android.WizardPage;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class ShareSteps {

    //Involved pages
    private WizardPage wizardPage;
    private LoginPage loginPage;
    private SharePage sharePage;
    private FileListPage fileListPage;

    private AndroidDriver driver;
    private final String serverURL = "http://10.40.40.198:17000";

    @Before
    public void setup() throws MalformedURLException {
        AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();

        wizardPage = new WizardPage(driver);
        loginPage = new LoginPage(driver);
        sharePage = new SharePage(driver);
        fileListPage = new FileListPage(driver);
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
        sharePage.shareWith(sharee);
    }

    @When("^i select (.+) to create link with name (.+)$")
    public void i_select_to_link_with_name(String itemName, String name) throws Throwable {
        fileListPage.shareAction(itemName);
        sharePage.shareLink(name);
    }

    @Then("^(.+) is shared with (.+)$")
    public void is_shared_with(String itenName, String sharee) throws Throwable {
        sharePage.backListShares();
        assertTrue(sharePage.isItemInList(itenName));
        assertTrue(sharePage.isUserInList(sharee));
    }

    @Then("^(.+) sees (.+) in the file list$")
    public void sees_in_file_list(String sharee, String item) throws Throwable {
        //API check
    }

    @Then("^public link is created with the name (.+)")
    public void public_link_created(String name) throws Throwable {
        assertTrue(sharePage.isNameInList(name));
    }

    @After
    public void tearDown(){
        driver.removeApp("com.owncloud.android");
        driver.quit();
    }
}
