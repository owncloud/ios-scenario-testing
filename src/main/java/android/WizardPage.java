package android;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class WizardPage extends CommonPage {

    private String skip_id = "skip";

    public WizardPage(AndroidDriver driver){
        super(driver);
    }

    public void skip(){
        driver.findElement(MobileBy.id(skip_id)).click();
    }

}
