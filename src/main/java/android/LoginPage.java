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
    private String acceptCert_id = "ok";

    private final String serverURL = LocProperties.getProperties().getProperty("serverURL");

    public LoginPage(){
        super();
    }

    public boolean notLoggedIn(){
        return driver.findElements(MobileBy.id(urltext_id)).size() > 0;
    }

    public void typeURL(){
        Log.log(Level.FINE, "Starts: Type URL.");
        waitById(15, urltext_id);
        driver.findElement(MobileBy.id(urltext_id)).sendKeys(serverURL);
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
}