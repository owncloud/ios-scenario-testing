package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.LocProperties;
import utils.log.Log;

public class LoginPage extends CommonPage{

    @iOSXCUITFindBy(accessibility = "addServer")
    private List<MobileElement> addServer;

    @iOSXCUITFindBy(accessibility = "row-url-url")
    private List<MobileElement> urlServer;

    @iOSXCUITFindBy(accessibility = "continue-bar-button")
    private MobileElement continueOption;

    @iOSXCUITFindBy(accessibility = "approve-button")
    private MobileElement approveButton;

    @iOSXCUITFindBy(accessibility = "cancel-button")
    private MobileElement cancelButton;

    @iOSXCUITFindBy(accessibility = "row-credentials-username")
    private MobileElement usernameInput;

    @iOSXCUITFindBy(accessibility = "row-credentials-password")
    private MobileElement passwordInput;

    @iOSXCUITFindBy(accessibility = "server-bookmark-cell")
    private List<MobileElement> bookmarkCells;

    @iOSXCUITFindBy(accessibility = "server-bookmark-cell")
    private MobileElement bookmarkCell;


    private final String serverURL = LocProperties.getProperties().getProperty("serverBasicTest");
    private final String oauth2URL = LocProperties.getProperties().getProperty("serverOAuth2Test");
    private final String oidcURL = LocProperties.getProperties().getProperty("serverOIDCTest");
    private final String LDAPURL = LocProperties.getProperties().getProperty("serverLDAPTest");
    private final String red301URL = LocProperties.getProperties().getProperty("server301Test");
    private final String red302URL = LocProperties.getProperties().getProperty("server302Test");

    private final String server = System.getProperty("server");

    public LoginPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean notLoggedIn(){
        Log.log(Level.FINE, "Logged: " + addServer.size());
        return addServer.size() > 0;
    }

    public void typeURL(){
        Log.log(Level.FINE, "Starts: Type URL.");
        urlServer.get(0).sendKeys(server);
        continueOption.click();
        approveIssue();
        continueOption.click();
    }

    public void typeURL(String authMethod){
        Log.log(Level.FINE, "Starts: Type URL.");
        waitById(15, urlServer.get(0));
        urlServer.get(0).sendKeys(selectURL(authMethod));
        continueOption.click();
        approveIssue();
        continueOption.click();
    }

    public void approveIssue(){
        waitById(5, approveButton);
        approveButton.click();
    }

    public void cancelIssue(){
        cancelButton.click();
    }

    public void acceptPermissions(){
        driver.switchTo().alert().accept();
    }

    public void skipAddServer(){
        addServer.get(0).click();
    }

    public void typeCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: Type credentials: username: "
                + username + " - password: " + password);
        waitById(15, usernameInput);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void submitLogin(){
        Log.log(Level.FINE, "Starts: Submit login");
        continueOption.click();
    }

    public boolean isCredentialsError(){
        return driver.findElements(By.id("server-bookmark-cell")).size() == 0;
    }

    public boolean isBookmarkCreated(){
        return driver.findElements(By.id("server-bookmark-cell")).size() > 0;
    }

    public void selectBookmarkIndex(int index) {
        MobileElement firstServer =  (MobileElement) driver.findElements(By.id("server-bookmark-cell")).get(index);
        firstServer.click();
    }

    public void selectFirstBookmark() {
        bookmarkCell.click();
    }

    private String selectURL(String authMehod){
        switch (authMehod){
            case "basic auth":
                Log.log(Level.FINE, "URL: " + serverURL);
                return serverURL;
            case "OAuth2":
                Log.log(Level.FINE, "URL: " + oauth2URL);
                return oauth2URL;
            case "LDAP":
                Log.log(Level.FINE, "URL: " + LDAPURL);
                return LDAPURL;
            case "redirection 301":
                Log.log(Level.FINE, "URL: " + red301URL);
                return red301URL;
            case "redirection 302":
                Log.log(Level.FINE, "URL: " + red302URL);
                return red302URL;
            case "OIDC":
                Log.log(Level.FINE, "URL: " + oidcURL);
                return oidcURL;
            default:
                Log.log(Level.WARNING, "No URL");
                return null;
        }
    }

}