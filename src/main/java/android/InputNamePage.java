package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class InputNamePage extends CommonPage {

    @AndroidFindBy(id="user_input")
    private MobileElement newName;

    @AndroidFindBy(id="android:id/button1")
    private MobileElement acceptButton;

    public InputNamePage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void setItemName(String itemName){
        Log.log(Level.FINE, "Start: Set name to item: " + itemName);
        newName.clear();
        newName.sendKeys(itemName);
        takeScreenshot("SetName/SetItemName_"+itemName);
        acceptButton.click();
    }
}
