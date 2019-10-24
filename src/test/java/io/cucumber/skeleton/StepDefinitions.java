package io.cucumber.skeleton;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath,"src/test/resources");
        File app = new File(appDir,"owncloud.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability ("platformName", "Android");
        capabilities.setCapability ("deviceName", "test");
        capabilities.setCapability ("app", app.getAbsolutePath());
        capabilities.setCapability ("appPackage", "com.owncloud.android");
        capabilities.setCapability ("appActivity", ".ui.activity.FileDisplayActivity");

        driver = new AndroidDriver (new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @Given("^I am a valid user$")
    public void i_am_a_valid_user() throws Throwable {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.id("skip")).click();

    }

    @When("^I login as (.+) with password (.+)$")
    public void i_login_as_string_with_password_string(String username, String password) throws Throwable {
        driver.findElement(By.id("hostUrlInput")).sendKeys(serverURL);
        driver.findElement(By.id("embeddedCheckServerButton")).click();
        driver.findElement(By.id("account_username")).sendKeys(username);
        driver.findElement(By.id("account_password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.xpath("//*[@text='ALLOW']")).click();
    }

    @When("^I login as (.+) with incorrect password (.+)$")
    public void i_login_as_string_with_incorrect_password_string(String username, String password) throws Throwable {
        driver.findElement(By.id("hostUrlInput")).sendKeys(serverURL);
        driver.findElement(By.id("embeddedCheckServerButton")).click();
        driver.findElement(By.id("account_username")).sendKeys(username);
        driver.findElement(By.id("account_password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }

    @Then("^I can see the main page$")
    public void i_can_see_the_main_page() {
        assertTrue(driver.findElements(By.xpath("//*[@text='ownCloud']")).size() > 0);
    }

    @Then("^I see an error message$")
    public void i_see_an_error_message() {
        assertTrue(driver.findElements(By.xpath("//*[@text='Wrong username or password']")).size() > 0 );
    }

    @After
    public void tearDown(){
        driver.removeApp("com.owncloud.android");
        driver.quit();
    }
}
