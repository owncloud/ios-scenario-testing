package ios;

import org.openqa.selenium.By;

import java.util.logging.Level;

import utils.log.Log;

public class OAuth2Page extends CommonPage {

    private String switch_xpath = "//XCUIElementTypeButton[@name=\"Switch users to continue\"]";

    public OAuth2Page(){
        super();
    }

    public void enterCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: enter OAuth2 credentials");
        driver.findElement(By.xpath("//XCUIElementTypeTextField[@name=\"Username or email\"]")).sendKeys(username);
        driver.findElement(By.xpath("//XCUIElementTypeSecureTextField[@name=\"Password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Login\"]")).click();
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OAuth2");
        driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Authorize\"]")).click();
    }
}