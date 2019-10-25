package android;

import org.openqa.selenium.By;

public class WizardPage extends AppiumManager {

    private String skip_id = "skip";

    public WizardPage(){}

    public void skip(){
        driver.findElement(By.id(skip_id)).click();
    }

}
