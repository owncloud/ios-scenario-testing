package android;

import io.appium.java_client.MobileBy;

public class InputNamePage extends CommonPage {

    private final String itemnametext_id = "user_input";
    private final String acceptbutton_id = "android:id/button1";

    public InputNamePage(){
        super();
    }

    public void setItemName(String name){
        driver.findElement(MobileBy.id(itemnametext_id)).clear();
        driver.findElement(MobileBy.id(itemnametext_id)).sendKeys(name);
        driver.findElement(MobileBy.id(acceptbutton_id)).click();
    }

}
