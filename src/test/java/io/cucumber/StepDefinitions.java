package io.cucumber;

import android.AppiumManager;
import android.FileListPage;
import android.LoginPage;
import android.WizardPage;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {

    private AndroidDriver driver;
    private final String serverURL = "http://10.40.40.198:17000";

    @Before
    public void setup() throws MalformedURLException{

        /*File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath,"src/test/resources");
        File app = new File(appDir,"owncloud.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability ("platformName", "Android");
        capabilities.setCapability ("deviceName", "test");
        capabilities.setCapability ("app", app.getAbsolutePath());
        capabilities.setCapability ("appPackage", "com.owncloud.android");
        capabilities.setCapability ("appActivity", ".ui.activity.FileDisplayActivity");

        driver = new AndroidDriver (new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);*/

        AppiumManager manager = new AppiumManager();
        manager.init();
        driver = manager.getDriver();

    }

    @Given("^I am a valid user$")
    public void i_am_a_valid_user() throws Throwable {
        //driver.findElement(By.id("skip")).click();
        WizardPage wizardPage = new WizardPage(driver);
        wizardPage.skip();

    }

    @When("^I login as (.+) with password (.+)$")
    public void i_login_as_string_with_password_string(String username, String password) throws Throwable {
        /*driver.findElement(By.id("hostUrlInput")).sendKeys(serverURL);
        driver.findElement(By.id("embeddedCheckServerButton")).click();
        driver.findElement(By.id("account_username")).sendKeys(username);
        driver.findElement(By.id("account_password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.xpath("//*[@text='ALLOW']")).click();*/
        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeURL();
        loginPage.typeCredentials(username, password);
        loginPage.allowPermissions();
    }

    @When("^I login as (.+) with incorrect password (.+)$")
    public void i_login_as_string_with_incorrect_password_string(String username, String password) throws Throwable {
        /*driver.findElement(By.id("hostUrlInput")).sendKeys(serverURL);
        driver.findElement(By.id("embeddedCheckServerButton")).click();
        driver.findElement(By.id("account_username")).sendKeys(username);
        driver.findElement(By.id("account_password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();*/
        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeURL();
        loginPage.typeCredentials(username, password);
    }

    @Then("^I can see the main page$")
    public void i_can_see_the_main_page() {
        //assertTrue(driver.findElements(By.xpath("//*[@text='ownCloud']")).size() > 0);
        FileListPage fileListPage = new FileListPage(driver);
        assertTrue(fileListPage.isHeader());
    }

    @Then("^I see an error message$")
    public void i_see_an_error_message() {
        //assertTrue(driver.findElements(By.xpath("//*[@text='Wrong username or password']")).size() > 0 );
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isCredentialsErrorMessage());
    }

    @After
    public void tearDown(){
        driver.removeApp("com.owncloud.android");
        driver.quit();
    }
}
