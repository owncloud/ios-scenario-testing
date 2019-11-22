package android;

import io.appium.java_client.MobileBy;

public class PublicLinkPage extends CommonPage {

    private String namepubliclink_id = "shareViaLinkNameValue";
    private String savebutton_id = "saveButton";

    public PublicLinkPage(){
        super();
    }

    public void createLink (String name) throws InterruptedException {
        driver.findElement(MobileBy.id(namepubliclink_id)).clear();
        driver.findElement(MobileBy.id(namepubliclink_id)).sendKeys(name);
        driver.findElement(MobileBy.id(savebutton_id)).click();
    }
}
