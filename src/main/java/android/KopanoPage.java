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
    private String password_xpath = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[2]/android.view.View[2]/android.widget.EditText";
    private String acceptButton_xpath = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.app.Dialog/android.view." +
            "View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[3]/android.view.View[3]/android.widget.Button";
    private String authorize_xpath = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.app.Dialog/android." +
            "view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view." +
            "View[6]/android.view.View[2]/android.widget.Button";


    public KopanoPage(){
    }

    public void enterCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: enter OIDC credentials");
        Log.log(Level.FINE, "Android 10");
        waitByXpath(15, username_xpath);
        driver.findElement(By.xpath(username_xpath)).sendKeys(username);
        driver.findElement(By.xpath(password_xpath)).sendKeys(password);
        driver.findElement(By.xpath(acceptButton_xpath)).click();
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OIDC");
        waitByXpath(15, authorize_xpath);
        driver.findElement(By.xpath(authorize_xpath)).click();
    }
}
