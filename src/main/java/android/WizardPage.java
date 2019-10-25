package android;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class WizardPage {

    private String skip_id = "skip";
    private AndroidDriver driver;

    public WizardPage(AndroidDriver driver){
        this.driver = driver;
    }

    public void skip(){
        driver.findElement(By.id(skip_id)).click();
    }

}
