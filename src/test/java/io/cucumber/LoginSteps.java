package io.cucumber;

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
    }

    @When("^user logins as (.+) with password (.+)$")
    public void i_login_as_string_with_password_string(String username, String password) {
        loginPage.typeURL();
        loginPage.typeCredentials(username, password);
    }

    @When("^user logins as (.+) with incorrect password (.+)$")
    public void i_login_as_string_with_incorrect_password_string(String username, String password) {
        loginPage.typeURL();
        loginPage.typeCredentials(username, password);
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
