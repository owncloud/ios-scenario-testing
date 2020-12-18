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
    private String acceptCert_id = "ok";

    private final String serverURL = LocProperties.getProperties().getProperty("serverURL");
    private final String oauth2URL = LocProperties.getProperties().getProperty("OAuth2URL");
    private final String oidcURL = LocProperties.getProperties().getProperty("oidcURL");

    public LoginPage(){
        super();
    }

    public boolean notLoggedIn(){
        return driver.findElements(MobileBy.id(urltext_id)).size() > 0;
    }

    public void typeURL(String authMethod){
        Log.log(Level.FINE, "Starts: Type URL.");
        waitById(15, urltext_id);
        driver.findElement(MobileBy.id(urltext_id)).sendKeys(selectURL(authMethod));
        //driver.findElement(MobileBy.id(urltext_id)).sendKeys(serverURL);
        driver.findElement(MobileBy.id(embeddedbutton_id)).click();
        //Check how to improve this. Very ugly
        /*if (oidcURL.substring(0, 5).endsWith("s")) {
            Log.log(Level.FINE, "https server");
            driver.findElement(MobileBy.id(acceptCert_id)).click();
        }*/
    }

    public void typeCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: Type credentials: username: "
                + username + " - password: " + password);
        waitById(15, usernametext_id);
        driver.findElement(MobileBy.id(usernametext_id)).sendKeys(username);
        driver.findElement(MobileBy.id(passwordtext_id)).sendKeys(password);
    }

    public void submitLogin(){
        Log.log(Level.FINE, "Starts: Submit login");
        waitById(15, loginbutton_id);
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
                Log.log(Level.FINE, "URL: " + oauth2URL);
                return oauth2URL;
            case "OIDC":
                Log.log(Level.FINE, "URL: " + oidcURL);
                return oidcURL;
            default:
                Log.log(Level.WARNING, "No URL");
                return null;
        }
    }

    public void removeApp(){
        driver.removeApp(LocProperties.getProperties().getProperty("appPackage"));
    }
}