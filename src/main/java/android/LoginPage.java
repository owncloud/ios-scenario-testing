package android;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import utils.LocProperties;
import utils.log.Log;

public class LoginPage extends CommonPage{

    private String urltext_id = "hostUrlInput";
    private String embeddedbutton_id = "embeddedCheckServerButton";
    private String usernametext_id = "account_username";
    private String passwordtext_id = "account_password";
    private String loginbutton_id = "loginButton";
    private String errorcredentialstext_xpath = "//*[@text='Wrong username or password']";

    private final String serverURL = LocProperties.getProperties().getProperty("serverURL");
    private final String oauth2URL = LocProperties.getProperties().getProperty("OAuth2URL");

    public LoginPage(){
        super();
    }

    public void typeURL(String authMethod){
        Log.log(Level.FINE, "Starts: Type URL. Auth method: " + authMethod);
        waitById(5, urltext_id);
        driver.findElement(MobileBy.id(urltext_id)).sendKeys(selectURL(authMethod));
        driver.findElement(MobileBy.id(embeddedbutton_id)).click();
    }

    public void typeCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: Type credentials: username: "
                + username + " - password: " + password);
        waitById(5, usernametext_id);
        driver.findElement(MobileBy.id(usernametext_id)).sendKeys(username);
        driver.findElement(MobileBy.id(passwordtext_id)).sendKeys(password);
        submitLogin();
    }

    public void submitLogin(){
        Log.log(Level.FINE, "Starts: Submit login");
        waitById(5, loginbutton_id);
        driver.findElement(MobileBy.id(loginbutton_id)).click();
    }

    public boolean isCredentialsErrorMessage(){
        return driver.findElements(MobileBy.xpath(errorcredentialstext_xpath)).size() > 0;
    }

    private String selectURL(String authMehod){
        switch (authMehod){
            case "basic auth":
                Log.log(Level.FINE, "URL: " + serverURL);
                return serverURL;
            case "OAuth2":
                Log.log(Level.FINE, "URL: " + serverURL);
                return oauth2URL;
            default:
                Log.log(Level.WARNING, "No URL");
                return null;
        }
    }
}