package android;

import org.openqa.selenium.By;

public class ChromeCustomTab extends CommonPage{

    //ugly xpaths...
    private String username_xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
            "android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[1]/android.webkit.WebView/android.view.View[2]/" +
            "android.view.View/android.view.View[1]/android.widget.EditText";
    private String password_xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
            "android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[1]/android.webkit.WebView/android.view.View[2]" +
            "/android.view.View/android.view.View[2]/android.widget.EditText";
    private String acceptButton_xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout" +
            "/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[1]/android.webkit.WebView/android.view.View[2]" +
            "/android.view.View/android.view.View[2]/android.widget.Button";
    private String authorizeButton_xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout" +
            "/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[1]/android.webkit.WebView/android.view.View[2]/android.widget.Button";

    public ChromeCustomTab(){
    }

    public void enterCredentials(String username, String password){
        waitByXpath(5, username_xpath);
        driver.findElement(By.xpath(username_xpath)).sendKeys(username);
        driver.findElement(By.xpath(password_xpath)).sendKeys(password);
        driver.findElement(By.xpath(acceptButton_xpath)).click();
    }

    public void authorize(){
        waitByXpath(5, authorizeButton_xpath);
        driver.findElement(By.xpath(authorizeButton_xpath)).click();
    }

}