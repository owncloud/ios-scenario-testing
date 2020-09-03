package io.cucumber;

import android.ChromeCustomTabPage;
import android.KopanoPage;
import android.LoginPage;
import android.WizardPage;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import utils.LocProperties;
import utils.api.CommonAPI;
import utils.log.Log;

public class LoginSteps {

    //Involved pages
    private WizardPage wizardPage = new WizardPage();
    private LoginPage loginPage = new LoginPage();
    private CommonAPI commonAPI = new CommonAPI();

    @Given("^user1 is logged$")
    public void i_am_logged()
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        wizardPage.skip();
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
    }
}
