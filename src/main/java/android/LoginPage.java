package android;

import io.appium.java_client.MobileBy;
import utils.LocProperties;

public class LoginPage extends CommonPage{

    private String urltext_id = "hostUrlInput";
    private String embeddedbutton_id = "embeddedCheckServerButton";
    private String usernametext_id = "account_username";
    private String passwordtext_id = "account_password";
    private String loginbutton_id = "loginButton";
    private String errorcredentialstext_xpath = "//*[@text='Wrong username or password']";

    private final String serverURL = LocProperties.getProperties().getProperty("serverURL");

    public LoginPage(){
        super();
    }

    public void typeURL(){
        waitById(5, urltext_id);
        driver.findElement(MobileBy.id(urltext_id)).sendKeys(serverURL);
        driver.findElement(MobileBy.id(embeddedbutton_id)).click();
    }

    public void typeCredentials(String username, String password){
        waitById(5, usernametext_id);
        driver.findElement(MobileBy.id(usernametext_id)).sendKeys(username);
        driver.findElement(MobileBy.id(passwordtext_id)).sendKeys(password);
        waitById(5, loginbutton_id);
        driver.findElement(MobileBy.id(loginbutton_id)).click();
    }

    public boolean isCredentialsErrorMessage(){
        return driver.findElements(MobileBy.xpath(errorcredentialstext_xpath)).size() > 0;
    }

}