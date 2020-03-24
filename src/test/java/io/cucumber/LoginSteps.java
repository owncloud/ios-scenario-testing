package io.cucumber;

import android.ChromeCustomTabPage;
import android.FileListPage;
import android.LoginPage;
import android.WizardPage;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.LocProperties;
import utils.log.Log;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    //Involved pages
    private WizardPage wizardPage = new WizardPage();
    private LoginPage loginPage = new LoginPage();
    private FileListPage fileListPage = new FileListPage();

    @Given("^user1 is logged$")
    public void i_am_logged() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        wizardPage.skip();
        loginPage.typeURL("basic auth");
        loginPage.typeCredentials(LocProperties.getProperties().getProperty("userName1"),
                LocProperties.getProperties().getProperty("passw1"));
    }

    @Given("^user is a valid user$")
    public void i_am_a_valid_user() throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        wizardPage.skip();
        //Check user is created and enabled in BE
    }

    @When("^server with (.+) is available$")
    public void server_available(String authMethod) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + authMethod);
        loginPage.typeURL(authMethod);
    }

    @When("^user logins as (.+) with password (.+) as (.+) credentials$")
    public void login_with_password_auth_method(String username, String password, String authMethod) {
            Log.log(Level.FINE, "----STEP----: " +
                    new Object() {
                    }.getClass().getEnclosingMethod().getName()
                    + ": " + username + "-" + password + "-" + authMethod);
            ChromeCustomTabPage chromeCustomTabPage = new ChromeCustomTabPage();
            switch (authMethod) {
                case "basic auth":
                    loginPage.typeCredentials(username, password);
                    break;
                case "OAuth2":
                    loginPage.submitLogin();
                    chromeCustomTabPage.enterCredentials(username, password);
                    chromeCustomTabPage.authorize();
                    break;
                default:
                    break;
            }
    }

    @Then("^user can see the main page$")
    public void i_can_see_the_main_page() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        assertTrue(fileListPage.isHeader());
    }

    @Then("^user sees an error message$")
    public void i_see_an_error_message() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        assertTrue(loginPage.isCredentialsErrorMessage());
    }
}
