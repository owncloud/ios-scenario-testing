package android;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class CreateFolderPage extends CommonPage {
    
    private final String foldernametext_id = "user_input";
    private final String acceptbutton_id = "android:id/button1";

    public CreateFolderPage(AndroidDriver driver){
        super(driver);
    }

    public void setFolderName(String name) throws InterruptedException{
        driver.findElement(By.id(foldernametext_id)).sendKeys(name);
        driver.findElement(By.id(acceptbutton_id)).click();
    }

}
