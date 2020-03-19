package android;

import org.openqa.selenium.By;

public class ChromeCustomTab extends CommonPage{

    //ugly xpaths...
    //Android 10
    private String username_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
            "android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View[2]/" +
            "android.view.View/android.view.View[1]/android.widget.EditText";
    private String password_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
            "android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View[2]" +
            "/android.view.View/android.view.View[2]/android.widget.EditText";
    private String acceptButton_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout" +
            "/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View[2]" +
            "/android.view.View/android.view.View[2]/android.widget.Button";
    private String authorizeButton_xpath_10 = "hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout" +
            "/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]" +
            "/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View[2]/android.widget.Button";

    //Android older than 10
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

    private long deviceVersion;

    public ChromeCustomTab(){
        deviceVersion = (long) driver.getCapabilities().getCapability("deviceApiLevel");
    }

    public void enterCredentials(String username, String password){
        if (deviceVersion >= 29) {
            waitByXpath(5, username_xpath_10);
            driver.findElement(By.xpath(username_xpath_10)).sendKeys(username);
            driver.findElement(By.xpath(password_xpath_10)).sendKeys(password);
            driver.findElement(By.xpath(acceptButton_xpath_10)).click();
        } else {
            waitByXpath(5, username_xpath);
            driver.findElement(By.xpath(username_xpath)).sendKeys(username);
            driver.findElement(By.xpath(password_xpath)).sendKeys(password);
            driver.findElement(By.xpath(acceptButton_xpath)).click();
        }
    }

    public void authorize(){
        if (deviceVersion >= 29) {
            waitByXpath(5, authorizeButton_xpath_10);
            driver.findElement(By.xpath(authorizeButton_xpath_10)).click();
        } else {
            waitByXpath(5, authorizeButton_xpath);
            driver.findElement(By.xpath(authorizeButton_xpath)).click();
        }
    }
}