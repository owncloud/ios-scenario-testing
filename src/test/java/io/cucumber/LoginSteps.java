package io.cucumber;

import android.ChromeCustomTabPage;
import android.FileListPage;
import android.KopanoPage;
import android.LoginPage;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.LocProperties;
import utils.api.CommonAPI;
import utils.log.Log;
import utils.parser.CapabilityJSONHandler;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    //Involved pages
    private LoginPage loginPage = new LoginPage();
    private CommonAPI commonAPI = new CommonAPI();
    private FileListPage fileListPage = new FileListPage();

    /*@Given("^app is launched for the first time$")
    public void first_launch()
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        //In case it is installed, we remove to execute login tests
        loginPage.reinstallApp();
    }*/

    @Given("^user is logged$")
    public void i_am_logged()
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        if (loginPage.notLoggedIn()) {
            String authMethod = commonAPI.checkAuthMethod();
            String username = LocProperties.getProperties().getProperty("userName1");
            String password = LocProperties.getProperties().getProperty("passw1");
            loginPage.typeURL();
            switch (authMethod) {
                case "Basic":
                    loginPage.typeCredentials(username, password);
                    loginPage.submitLogin();
                    break;
                case "Bearer":
                    loginPage.submitLogin();
                    ChromeCustomTabPage chromeCustomTabPage = new ChromeCustomTabPage();
                    chromeCustomTabPage.enterCredentials(username,password);
                    chromeCustomTabPage.authorize();
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
        //Fill capabilities object
        String capabilityJSON = commonAPI.getCapabilities("ocs/v2.php/cloud/capabilities?format=json");
        CapabilityJSONHandler JSONparser = new CapabilityJSONHandler(capabilityJSON);
        JSONparser.parsePublicLink();
    }

    @When("^server with (.+) is available$")
    public void server_available(String authMethod) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + " with " + authMethod);
        loginPage.typeURL(authMethod);
    }

    @When("^user logins as (.+) with password (.+) as (.+) credentials$")
    public void login_with_password_auth_method(String username, String password,
                                                String authMethod) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object() {}.getClass().getEnclosingMethod().getName()
                + ": " + username + " - " + password + " - " + authMethod);
        switch (authMethod) {
            case "basic auth":
            case "LDAP":
                loginPage.typeCredentials(username, password);
                loginPage.submitLogin();
                break;
            case "OAuth2":
                loginPage.submitLogin();
                ChromeCustomTabPage chromeCustomTabPage = new ChromeCustomTabPage();
                chromeCustomTabPage.enterCredentials(username, password);
                chromeCustomTabPage.authorize();
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

    @Then("^user is correctly logged$")
    public void i_can_see_the_main_page() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        try {
            assertTrue(fileListPage.isBookmarkCreated());
            // In case the assertion fails, we have to remove the app to keep executing other tests
            // After catching the error, it must be thrown again to return the correct test result.
            // Otherwise, the test will never fail
        } catch (AssertionError e) {
            loginPage.removeApp();
            throw e;
        }
        loginPage.removeApp();
    }

    /*@Then("^user sees an error message$")
    public void i_see_an_error_message() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        try {
            assertTrue(loginPage.isCredentialsErrorMessage());
            // In case the assertion fails, we have to remove the app to keep executing other tests
            // After catching the error, it must be thrown again to return the correct test result.
            // Otherwise, the test will never fail
        } catch (AssertionError e) {
            loginPage.removeApp();
            throw e;
        }
        loginPage.removeApp();
    }*/
}
