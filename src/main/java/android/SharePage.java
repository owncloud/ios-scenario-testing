package android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

public class SharePage extends CommonPage {

    private String addshareebutton_id = "addUserButton";
    private String searchSrctext_id = "search_src_text";

    public SharePage(AndroidDriver driver){
        super(driver);
    }

    public boolean isHeader() {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"Share\");").size() > 0;
    }

    public void shareWith (String sharee) throws  InterruptedException{
        WebElement addSharee = driver.findElement(By.id(addshareebutton_id));
        actions.click(addSharee).perform();
        WebElement searchText = driver.findElement(By.id(searchSrctext_id));
        actions.sendKeys(searchText, "user2").perform();
        Thread.sleep(2000);
        driver.navigate().back();
        driver.navigate().back();

    }

    public boolean isUserInList(String username) {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+username+"\");").size() > 0;
    }

}
