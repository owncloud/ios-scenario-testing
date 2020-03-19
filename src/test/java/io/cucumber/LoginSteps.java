package io.cucumber;

import android.ChromeCustomTab;
import android.FileListPage;
import android.LoginPage;
import android.WizardPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    //Involved pages
    private WizardPage wizardPage = new WizardPage();
    private LoginPage loginPage = new LoginPage();
    private FileListPage fileListPage = new FileListPage();

    @Given("^user is a valid user$")
    public void i_am_a_valid_user() throws Throwable {
        wizardPage.skip();
        //Check user is created and enabled in BE
    }

    @When("^server with (.+) is available$")
    public void server_available(String authMethod) {
        loginPage.typeURL(authMethod);
    }

    @When("^user logins as (.+) with password (.+) as (.+) credentials$")
    public void login_with_password_auth_method(String username, String password, String authMethod) {
        ChromeCustomTab chromeCustomTabPage = new ChromeCustomTab();
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
    public void i_can_see_the_main_page() throws Throwable {
        assertTrue(fileListPage.isHeader());
    }

    @Then("^user sees an error message$")
    public void i_see_an_error_message() throws Throwable {
        assertTrue(loginPage.isCredentialsErrorMessage());
    }
}
