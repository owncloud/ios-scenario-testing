package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class InputNamePage extends CommonPage {

    @iOSXCUITFindBy(id="name-text-field")
    private MobileElement newName;

    @iOSXCUITFindBy(id="done-button")
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
