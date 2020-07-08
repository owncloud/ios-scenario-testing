package android;

import org.openqa.selenium.By;

import java.util.logging.Level;

import utils.log.Log;

public class KopanoPage extends CommonPage {

    private String username_xpath = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[1]/android.view.View[2]/android.widget.EditText";

    //private String username_xpath = "//android.webkit.WebView[@content-desc=\"Kopano Sign in\"]/android.app.Dialog/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[3]/android.view.View[1]/android.view.View[2]/android.widget.EditText";


    private String password_xpath = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[2]/android.view.View[2]/android.widget.EditText";

    //private String password_xpath = "//android.webkit.WebView[@content-desc=\"Kopano Sign in\"]/android.app.Dialog/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[3]/android.view.View[2]/android.view.View[2]/android.widget.EditText";


    private String acceptButton_xpath = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[3]/android.widget.Button";

    //accesibility id = NEXT
    //private String acceptButton_xpath = "//android.widget.Button[@content-desc=\"NEXT \"]";


    private String authorize_xpath = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.app.Dialog/android." +
            "view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[6]/android.view.View[2]/android.widget.Button";

    //accesibility id = ALLOW / CANCEL
    //private String authorize_xpath = "//android.widget.Button[@content-desc=\"ALLOW \"]";

    private String username_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[1]/android.view.View[2]/android.widget.EditText";

    private String password_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[2]/android.view.View[2]/android.widget.EditText";

    private String acceptButton_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[3]/android.widget.Button";

    private String authorize_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.app.Dialog/android." +
            "view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[6]/android.view.View[2]/android.widget.Button";

    private long deviceVersion;

    public KopanoPage(){
        deviceVersion = (long) driver.getCapabilities().getCapability("deviceApiLevel");
    }

    public void enterCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: enter OIDC credentials");
        if (deviceVersion >= 29) {
            Log.log(Level.FINE, "Android 10");
            if (!driver.findElementsByXPath(username_xpath_10).isEmpty()) {
                Log.log(Level.FINE, "Entering credentials10");
                driver.findElement(By.xpath(username_xpath_10)).sendKeys(username);
                driver.findElement(By.xpath(password_xpath_10)).sendKeys(password);
                driver.findElement(By.xpath(acceptButton_xpath_10)).click();
            }
        } else {
            Log.log(Level.FINE, "Android < 10");
            if (!driver.findElementsByXPath(username_xpath).isEmpty()) {
                Log.log(Level.FINE, "Entering credentials");
                driver.findElement(By.xpath(username_xpath)).sendKeys(username);
                driver.findElement(By.xpath(password_xpath)).sendKeys(password);
                driver.findElement(By.xpath(acceptButton_xpath)).click();
            }
        }
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OIDC");
        if (deviceVersion >= 29) {
            Log.log(Level.FINE, "Android 10");
            driver.findElement(By.xpath(authorize_xpath_10)).click();
        } else {
            Log.log(Level.FINE, "Android < 10");
            driver.findElement(By.xpath(authorize_xpath)).click();
        }
    }
}
