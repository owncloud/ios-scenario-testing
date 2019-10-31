package android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.android.AndroidDriver;

public class SharePage {

    private AndroidDriver driver;
    private Actions actions;

    private String addshareebutton_id = "addUserButton";
    private String searchSrctext_id = "search_src_text";

    public SharePage(AndroidDriver driver){
        this.driver = driver;
        actions = new Actions(driver);
    }

    public boolean isHeader() {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"Share\");").size() > 0;
    }

    public void shareWith (String sharee) throws  InterruptedException{
        WebElement addSharee = driver.findElement(By.id(addshareebutton_id));
        actions.click(addSharee).perform();
        WebElement searchText = driver.findElement(By.id(searchSrctext_id));
        actions.sendKeys(searchText, "user2").perform();
        Thread.sleep(5000);
        driver.navigate().back();
        driver.navigate().back();
        Thread.sleep(5000);
        //WebElement shareess = driver.findElement(By.xpath("//*[@text='user2']"));

    }

    public boolean isUserInList(String username) {
        return driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+username+"\");").size() > 0;
    }

}
