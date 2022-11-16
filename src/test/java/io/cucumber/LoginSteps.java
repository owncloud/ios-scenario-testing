package io.cucumber;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import ios.LoginPage;
import utils.LocProperties;
import utils.log.Log;

public class LoginSteps {

    //Involved pages
    private LoginPage loginPage = new LoginPage();

    @Given("user {word} is logged in")
    public void logged(String userName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!loginPage.loggedIn()) {
            String password = LocProperties.getProperties().getProperty("passw1");
            loginPage.skipAddServer();
            loginPage.typeURL();
            loginPage.typeCredentials(userName, password);
            loginPage.submitLogin();
            loginPage.selectFirstBookmark();
        } else { //Selecting first account
            loginPage.selectFirstBookmark();
        }
    }
}
