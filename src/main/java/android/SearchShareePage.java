package android;

import org.openqa.selenium.By;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class SearchShareePage extends CommonPage {

    private String searchSrctext_id = "search_src_text";

    public SearchShareePage(AndroidDriver driver){
        super(driver);
    }

    public void shareWithUser (String sharee) throws  InterruptedException{
        driver.findElement(By.id(searchSrctext_id)).sendKeys(sharee);
        //REDO: find another way to click in recipients' list
        Thread.sleep(2000);
        TouchAction selectSharee = new TouchAction(driver);
        selectSharee.tap(PointOption.point(500, 470)).perform();
        //Go back to Share Page
        backListShares();
    }

    public void backListShares(){
        driver.hideKeyboard();
        driver.navigate().back();
        //driver.navigate().back();
    }

}
