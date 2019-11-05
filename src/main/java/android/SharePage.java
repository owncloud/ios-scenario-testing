package android;

import org.openqa.selenium.By;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class SharePage extends CommonPage {

    private String addshareebutton_id = "addUserButton";
    private String searchSrctext_id = "search_src_text";
    private String addPublicLinkButton_id = "addPublicLinkButton";
    private String namepubliclink_id = "shareViaLinkNameValue";
    private String savebutton_id = "saveButton";

    public SharePage(AndroidDriver driver){
        super(driver);
    }

    public boolean isHeader() {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"Share\");").size() > 0;
    }

    public void shareWith (String sharee) throws  InterruptedException{
        driver.findElement(By.id(addshareebutton_id)).click();
        driver.findElement(By.id(searchSrctext_id)).sendKeys(sharee);
        //REDO: way to click in recipients' list
        Thread.sleep(2000);
        TouchAction a2 = new TouchAction(driver);
        a2.tap(PointOption.point(500, 470)).perform();
    }

    public boolean isUserInList(String username) {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+username+"\");").size() > 0;
    }

    public boolean isItemInList(String item) {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+item+"\");").size() > 0;
    }

    public void shareLink (String name) throws  InterruptedException{
        driver.findElement(By.id(addPublicLinkButton_id)).click();
        driver.findElement(By.id(namepubliclink_id)).clear();
        driver.findElement(By.id(namepubliclink_id)).sendKeys(name);
        driver.findElement(By.id(savebutton_id)).click();
    }

    public boolean isNameInList(String name) {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+name+"\");").size() > 0;
    }

    public void backListShares(){
        driver.navigate().back();
        driver.navigate().back();
    }

}
