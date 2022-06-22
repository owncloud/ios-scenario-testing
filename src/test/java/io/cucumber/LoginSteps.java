package io.cucumber;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.logging.Level;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ios.LoginPage;
import ios.OAuth2Page;
import ios.OidcPage;
import utils.LocProperties;
import utils.api.AuthAPI;
import utils.log.Log;

public class LoginSteps {

    //Involved pages
    private LoginPage loginPage = new LoginPage();
    private AuthAPI authAPI = new AuthAPI();

    @ParameterType("basic auth|LDAP|redirection 301|redirection 302")
    public String authtype(String type){
        return type;
    }

    @Given("app has been launched for the first time")
    public void app_from_scratch(){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //In case it is installed, we remove to execute login tests
        loginPage.reinstallApp();
        loginPage.acceptPermissions();
    }

    @Given("user {word} is logged in")
    public void logged(String userName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!loginPage.loggedIn()) {
            Log.log(Level.FINE, "Not logged. Starting login process");
            String authMethod = authAPI.checkAuthMethod();
            String password = LocProperties.getProperties().getProperty("passw1");
            loginPage.skipAddServer();
            loginPage.typeURL();
            switch (authMethod) {
                case "Basic":
                    loginPage.typeCredentials(userName, password);
                    loginPage.submitLogin();
                    loginPage.selectFirstBookmark();
                    break;
                case "Bearer":
                    loginPage.submitLogin();
                    loginPage.submitBrowser();
                    OAuth2Page oauth2Page = new OAuth2Page();
                    oauth2Page.enterCredentials(userName,password);
                    oauth2Page.authorize();
                    loginPage.selectFirstBookmark();
                    break;
                case "OIDC":
                    loginPage.submitLogin();
                    OidcPage oidcPage = new OidcPage();
                    oidcPage.enterCredentials(userName, password);
                    oidcPage.authorize();
                    loginPage.selectFirstBookmark();
                    break;
                default:
                    break;
            }
        } else { //Selecting first account
            loginPage.selectFirstBookmark();
        }
    }

    @Given("server with {authtype} is available")
    public void server_available(String authMethod) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        loginPage.acceptPermissions();
        loginPage.skipAddServer();
        loginPage.typeURL(authMethod);
    }

    @When("user logins as {word} with password {word} as {authtype} credentials")
    public void login_with_password_auth_method(String username, String password,
                                                String authMethod) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
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
                OidcPage oidcPage = new OidcPage();
                oidcPage.enterCredentials(username, password);
                oidcPage.authorize();
                break;
            default:
                break;
        }
    }

    @Then("user accepts the redirection")
    public void accept_redirection() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        loginPage.approveIssue();
    }

    @Then("user should be correctly logged")
    public void correctly_logged() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
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

    @Then("user should see an error")
    public void credential_error() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
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
