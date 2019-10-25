package android;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class FileListPage {

    private AndroidDriver driver;
    private String headertext_xpath = "//*[@text='ownCloud']";

    public FileListPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean isHeader(){
        return driver.findElements(By.xpath(headertext_xpath)).size() > 0;
    }

}
