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

    private AndroidDriver driver;
    private final String serverURL = "http://10.40.40.198:17000";

    @Before
    public void setup() throws MalformedURLException {
        AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();
    }

    @Given("^I am logged$")
    public void i_am_logged() throws Throwable {
        WizardPage wizardPage = new WizardPage(driver);
        wizardPage.skip();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeURL();
        loginPage.typeCredentials("user1", "a");
        loginPage.allowPermissions();
    }

    @When("^I select Documents to share$")
    public void i_select_documents_share() throws Throwable {
        FileListPage fileListPage = new FileListPage(driver);
        fileListPage.longPress();
        fileListPage.shareAction();
    }

    @Then("^I see the Share view$")
    public void documents_is_shared() throws Throwable {
        SharePage sharePage = new SharePage(driver);
        assertTrue(sharePage.isHeader());
    }

    @After
    public void tearDown(){
        driver.removeApp("com.owncloud.android");
        driver.quit();
    }
}
