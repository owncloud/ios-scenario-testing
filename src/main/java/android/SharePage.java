package android;

import io.appium.java_client.MobileBy;

public class SharePage extends CommonPage {

    private final String addshareebutton_id = "addUserButton";
    private final String addPublicLinkButton_id = "addPublicLinkButton";

    public SharePage(){
        super();
    }

    public boolean isHeader() {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"Share\");").isEmpty();
    }

    public void addPrivateShare(){
        driver.findElement(MobileBy.id(addshareebutton_id)).click();
    }

    public void addPublicLink(){
        driver.findElement(MobileBy.id(addPublicLinkButton_id)).click();
    }

    public boolean isUserInList(String username) {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+username+"\");").isEmpty();
    }

    public boolean isItemInList(String item) {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+item+"\");").isEmpty();
    }

    public boolean isPublicLinkNameInList(String name) {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+name+"\");").isEmpty();
    }

}
