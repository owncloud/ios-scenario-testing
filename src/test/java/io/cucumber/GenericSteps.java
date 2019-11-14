package io.cucumber;

import android.AppiumManager;
import android.LoginPage;
import android.WizardPage;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class GenericSteps {

    //Involved pages
    protected WizardPage wizardPage;
    protected LoginPage loginPage;

    //Appium driver
    protected AndroidDriver driver;

    private String user = "user1";
    private String password = "a";

    @Before
    public void setup() throws MalformedURLException {
        AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();

        wizardPage = new WizardPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Given("^I am logged$")
    public void i_am_logged() throws Throwable {
        wizardPage.skip();
        loginPage.typeURL();
        loginPage.typeCredentials(user, password);
        loginPage.allowPermissions();
    }
}
