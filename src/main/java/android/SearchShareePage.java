package android;

import org.openqa.selenium.By;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import utils.log.Log;

public class SearchShareePage extends CommonPage {

    private String searchSrctext_id = "com.owncloud.android:id/search_src_text";
    private String navigateup_xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]";

    public SearchShareePage()  {
        super();
    }

    public void shareWithUser (String sharee)
            throws InterruptedException {
        Log.log(Level.FINE, "Starts: Share with user: " + sharee);
        waitById(10,searchSrctext_id);
        driver.findElement(By.id(searchSrctext_id)).sendKeys(sharee);
        Log.log(Level.WARNING, "Needed better implementation - failure possible");
        //REDO: find another way to click in recipients' list
        Thread.sleep(1000);
        takeScreenshot("PrivateShare/SearchSharee_" + sharee);
        TouchAction selectSharee = new TouchAction(driver);
        selectSharee.tap(PointOption.point(500, 470)).perform();
        //Go back to Share Page
        backListShares();
    }

    private void backListShares() {
        Log.log(Level.FINE, "Starts: Back to the list of shares");
        driver.findElement(MobileBy.xpath(navigateup_xpath)).click();
    }
}