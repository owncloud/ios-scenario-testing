package io.cucumber;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.StepEventBus;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ios.KopanoPage;
import ios.LoginPage;
import ios.OAuth2Page;
import utils.LocProperties;
import utils.api.CommonAPI;
import utils.log.Log;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    //Involved pages
    @Steps
    private LoginPage loginPage;

    private CommonAPI commonAPI = new CommonAPI();

    @Given("^app has been launched for the first time$")
    public void app_from_scratch(){
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        //In case it is installed, we remove to execute login tests
        loginPage.reinstallApp();
        loginPage.acceptPermissions();
    }

    @Given("^user (.+) is logged$")
    public void logged(String user)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        loginPage.acceptPermissions();
        if (loginPage.notLoggedIn()) {
            Log.log(Level.FINE, "Not logged. Starting login process");
            String authMethod = commonAPI.checkAuthMethod();
            String username = LocProperties.getProperties().getProperty("userName1");
            String password = LocProperties.getProperties().getProperty("passw1");
            loginPage.skipAddServer();
            loginPage.typeURL();
            switch (authMethod) {
                case "Basic":
                    loginPage.typeCredentials(username, password);
                    loginPage.submitLogin();
                    loginPage.selectBookmarkIndex(0);
                    break;
                case "Bearer":
                    loginPage.submitLogin();
                    OAuth2Page oauth2Page = new OAuth2Page();
                    oauth2Page.enterCredentials(username,password);
                    oauth2Page.authorize();
                    break;
                case "OIDC":
                    loginPage.submitLogin();
                    KopanoPage kopanoPage = new KopanoPage();
                    kopanoPage.enterCredentials(username, password);
                    kopanoPage.authorize();
                    break;
                default:
                    break;
            }
        }
    }

    @Given("^server with (.+) is available$")
    public void server_available(String authMethod) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        loginPage.acceptPermissions();
        loginPage.skipAddServer();
        loginPage.typeURL(authMethod);
    }

    @When("^user logins as (.+) with password (.+) as (.+) credentials$")
    public void login_with_password_auth_method(String username, String password,
                                                String authMethod) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        switch (authMethod) {
            case "basic auth":
            case "LDAP":
                loginPage.typeCredentials(username, password);
                loginPage.submitLogin();
                break;
            case "OAuth2":
                loginPage.submitLogin();
                OAuth2Page oauth2page = new OAuth2Page();
                oauth2page.enterCredentials(username, password);
                oauth2page.authorize();
                break;
            case "OIDC":
                loginPage.submitLogin();
                KopanoPage kopanoPage = new KopanoPage();
                kopanoPage.enterCredentials(username, password);
                kopanoPage.authorize();
                break;
            default:
                break;
        }
    }

    @Then("^user accepts the redirection$")
    public void accept_redirection() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        loginPage.approveIssue();
    }

    @Then("^user should be correctly logged$")
    public void correctly_logged() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        try {
            assertTrue(loginPage.isBookmarkCreated());
            // In case the assertion fails, we have to remove the app to keep executing other tests
            // After catching the error, it must be thrown again to return the correct test result.
            // Otherwise, the test will never fail
        } catch (AssertionError e) {
            loginPage.removeApp();
            throw e;
        }
        loginPage.removeApp();
    }

    @Then("^user should see an error$")
    public void credential_error() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        try {
            assertTrue(loginPage.isCredentialsError());
            // In case the assertion fails, we have to remove the app to keep executing other tests
            // After catching the error, it must be thrown again to return the correct test result.
            // Otherwise, the test will never fail
        } catch (AssertionError e) {
            loginPage.removeApp();
            throw e;
        }
        loginPage.removeApp();
    }
}
