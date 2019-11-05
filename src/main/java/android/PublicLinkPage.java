package android;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class PublicLinkPage extends CommonPage {

    private String namepubliclink_id = "shareViaLinkNameValue";
    private String savebutton_id = "saveButton";

    public PublicLinkPage(AndroidDriver driver){
        super(driver);
    }

    public void createLink (String name) throws InterruptedException {
        driver.findElement(By.id(namepubliclink_id)).clear();
        driver.findElement(By.id(namepubliclink_id)).sendKeys(name);
        driver.findElement(By.id(savebutton_id)).click();
    }
}
