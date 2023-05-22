package io.cucumber;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import utils.LocProperties;
import utils.log.Log;

public class LoginSteps {

    private World world;

    public LoginSteps(World world) {
        this.world = world;
    }

    @Given("user {word} is logged in")
    public void logged(String userName)
            throws Throwable {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!world.loginPage.loggedIn()) {
            String password = LocProperties.getProperties().getProperty("passw1");
            world.loginPage.addAccount();
            world.loginPage.typeURL();
            world.loginPage.typeCredentials(userName, password);
            world.loginPage.submitLogin();
            world.loginPage.selectDrive();
        } else {
            world.loginPage.selectFirstBookmark();
        }
    }
}
