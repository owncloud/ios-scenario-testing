package android;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import utils.log.Log;

public class InputNamePage extends CommonPage {

    private final String itemnametext_id = "user_input";
    private final String acceptbutton_id = "android:id/button1";

    public InputNamePage(){
        super();
    }

    public void setItemName(String itemName){
        Log.log(Level.FINE, "Start: Set name to item: " + itemName);
        driver.findElement(MobileBy.id(itemnametext_id)).clear();
        driver.findElement(MobileBy.id(itemnametext_id)).sendKeys(itemName);
        takeScreenshot("SetName/SetItemName_"+itemName);
        driver.findElement(MobileBy.id(acceptbutton_id)).click();
    }
}
