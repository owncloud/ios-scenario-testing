package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class WizardPage extends CommonPage {

    @AndroidFindBy(id="com.owncloud.android:id/skip")
    private MobileElement skip;

    public WizardPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void skip(){
        Log.log(Level.FINE, "Starts: Skipping welcome wizard");
        waitById(60, skip);
        takeScreenshot("Wizard/Wizard");
        skip.click();
    }
}
