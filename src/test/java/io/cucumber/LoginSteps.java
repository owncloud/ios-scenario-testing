package io.cucumber;

import android.AppiumManager;
import android.FileListPage;
import android.LoginPage;
import android.WizardPage;

import java.net.MalformedURLException;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    //Involved pages
    private WizardPage wizardPage = new WizardPage();
    private LoginPage loginPage = new LoginPage();
    private FileListPage fileListPage = new FileListPage();

    //private AndroidDriver driver;

    /*@Before
    public void setup() {
        AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();
        System.out.println("BEFORE LOGIN STEPS");

        AppiumManager.getManager().getDriver().launchApp();

        /*wizardPage = new WizardPage();
        loginPage = new LoginPage();
        fileListPage = new FileListPage();
    }*/

    @Given("^I am a valid user$")
    public void i_am_a_valid_user() throws Throwable {
        wizardPage.skip();
    }

    @When("^I login as (.+) with password (.+)$")
    public void i_login_as_string_with_password_string(String username, String password)
            throws Throwable {
        loginPage.typeURL();
        loginPage.typeCredentials(username, password);
        loginPage.allowPermissions();
    }

    @When("^I login as (.+) with incorrect password (.+)$")
    public void i_login_as_string_with_incorrect_password_string(String username, String password)
            throws Throwable {
        loginPage.typeURL();
        loginPage.typeCredentials(username, password);
    }

    @Then("^I can see the main page$")
    public void i_can_see_the_main_page() throws Throwable {
        assertTrue(fileListPage.isHeader());
    }

    @Then("^I see an error message$")
    public void i_see_an_error_message() throws Throwable {
        assertTrue(loginPage.isCredentialsErrorMessage());
    }

    @After
    public void tearDown() throws MalformedURLException{
        System.out.println("AFTER LOGIN STEPS");
        AppiumManager.getManager().getDriver().removeApp("com.owncloud.android");
        //AppiumManager.getManager().getDriver().close();
    }
}
