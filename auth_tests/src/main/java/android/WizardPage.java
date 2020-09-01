package android;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import utils.log.Log;

public class WizardPage extends CommonPage {

    private String skip_id = "skip";

    public WizardPage(){
        super();
    }

    public void skip(){
        Log.log(Level.FINE, "Starts: Skipping welcome wizard");
        waitById(60, skip_id);
        takeScreenshot("Wizard/Wizard");
        driver.findElement(MobileBy.id(skip_id)).click();
    }
}
