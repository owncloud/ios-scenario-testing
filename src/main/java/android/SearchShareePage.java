package android;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public class SearchShareePage extends CommonPage {

    private String searchSrctext_id = "search_src_text";

    public SearchShareePage()  {
        super();
    }

    public void shareWithUser (String sharee) throws  InterruptedException{
        waitById(10,searchSrctext_id);
        driver.findElement(By.id(searchSrctext_id)).sendKeys(sharee);
        //REDO: find another way to click in recipients' list
        Thread.sleep(1000);
        TouchAction selectSharee = new TouchAction(driver);
        selectSharee.tap(PointOption.point(500, 470)).perform();
        //Go back to Share Page
        backListShares();
    }

    private void backListShares() throws InterruptedException{
        //driver.hideKeyboard();
        //By setting only once, it does not work... check why
        //driver.navigate().back();
        //driver.navigate().back();
        MobileElement back = (MobileElement)
            driver.findElement(MobileBy.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"));
        actions.click(back).perform();
    }

}