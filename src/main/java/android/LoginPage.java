package android;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class LoginPage {

    private String urltext_id = "hostUrlInput";
    private String embeddedbutton_id = "embeddedCheckServerButton";
    private String usernametext_id = "account_username";
    private String passwordtext_id = "account_password";
    private String loginbutton_id = "loginButton";
    private String errorcredentialstext_xpath = "//*[@text='Wrong username or password']";
    private String allowbutton_xpath = "//*[@text='Allow']";

    private final String serverURL = "http://10.40.40.198:29000";
    private AndroidDriver driver;

    public LoginPage(AndroidDriver driver){
        this.driver = driver;
    }

    public void typeURL(){
        driver.findElement(By.id(urltext_id)).sendKeys(serverURL);
        driver.findElement(By.id(embeddedbutton_id)).click();
    }

    public void typeCredentials(String username, String password){
        driver.findElement(By.id(usernametext_id)).sendKeys(username);
        driver.findElement(By.id(passwordtext_id)).sendKeys(password);
        driver.findElement(By.id(loginbutton_id)).click();
    }

    public void allowPermissions(){
        driver.findElement(By.xpath(allowbutton_xpath)).click();
    }

    public boolean isCredentialsErrorMessage(){
        return driver.findElements(By.xpath(errorcredentialstext_xpath)).size() > 0;
    }

}