package android;

import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class LoginPage extends CommonPage{

    @AndroidFindBy(id="hostUrlInput")
    private List<MobileElement> urlServer;

    @AndroidFindBy(id="embeddedCheckServerButton")
    private MobileElement checkServerButton;

    @AndroidFindBy(id="account_username")
    private MobileElement userNameText;

    @AndroidFindBy(id="account_password")
    private MobileElement passwordText;

    @AndroidFindBy(id="loginButton")
    private MobileElement loginButton;

    @AndroidFindBy(id="ok")
    private MobileElement acceptCertificate;

    private final String serverURL = System.getProperty("server");

    public LoginPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean notLoggedIn(){
        return !urlServer.isEmpty();
    }

    public void typeURL(){
        Log.log(Level.FINE, "Starts: Type URL.");
        waitById(15, urlServer.get(0));
        urlServer.get(0).sendKeys(serverURL);
        checkServerButton.click();
        //Check how to improve this. Very ugly
        /*if (oidcURL.substring(0, 5).endsWith("s")) {
            Log.log(Level.FINE, "https server");
            driver.findElement(MobileBy.id(acceptCert_id)).click();
        }*/
    }

    public void typeCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: Type credentials: username: "
                + username + " - password: " + password);
        waitById(15, userNameText);
        userNameText.sendKeys(username);
        passwordText.sendKeys(password);
    }

    public void submitLogin(){
        Log.log(Level.FINE, "Starts: Submit login");
        waitById(15, loginButton);
        loginButton.click();
    }
}