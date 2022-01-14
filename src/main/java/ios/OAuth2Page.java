package ios;

import org.openqa.selenium.By;

import java.util.logging.Level;

import utils.log.Log;

public class OAuth2Page extends CommonPage {

    private String username_xpath = "//XCUIElementTypeTextField[@name=\"Username or email\"]";
    private String password_xpath = "//XCUIElementTypeSecureTextField[@name=\"Password\"]";
    private String loginbutton_xpath = "//XCUIElementTypeButton[@name=\"Login\"]";
    private String authorize_xpath = "//XCUIElementTypeButton[@name=\"Authorize\"]";
    private String switch_xpath = "//XCUIElementTypeButton[@name=\"Switch users to continue\"]";

    public OAuth2Page(){
        super();
    }

    public void enterCredentials(String username, String password){
        if (!driver.findElements(By.xpath(switch_xpath)).isEmpty()) {
            switchUser();
        }
        Log.log(Level.FINE, "Starts: enter OAuth2 credentials");
        findXpath(username_xpath).sendKeys(username);
        findXpath(password_xpath).sendKeys(password);
        findXpath(loginbutton_xpath).click();
    }

    public void switchUser(){
        Log.log(Level.FINE, "Starts: Switch user");
        findXpath(switch_xpath).click();
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OAuth2");
        findXpath(authorize_xpath).click();
    }
}