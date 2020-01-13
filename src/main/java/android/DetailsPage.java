package android;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class DetailsPage extends CommonPage {

    private String itemName_id = "com.owncloud.android:id/fdFilename";
    private String itemType_id = "com.owncloud.android:id/fdType";
    private String itemSize_id = "com.owncloud.android:id/fdSize";
    private String downloading_id = "com.owncloud.android:id/fdProgressText";
    private String photoview_id = "com.owncloud.android:id/photo_view";

    public DetailsPage(){
        super();
    }

    public String getName(){
        return driver.findElement(By.id(itemName_id)).getText();
    }

    public String getType(){
        return driver.findElement(By.id(itemType_id)).getText();
    }

    public String getSize(){
        return driver.findElement(By.id(itemSize_id)).getText();
    }

    public void backListFiles() {
        MobileElement back = (MobileElement)
                driver.findElement(MobileBy.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"));
        actions.click(back).perform();
    }

    public void closeOpenIn(){
        driver.navigate().back();
    }

    public boolean itemPreviewed(){
        return driver.findElement(By.id(photoview_id)).isDisplayed();
    }

    public void waitFinishedDownload(int seconds){
        waitByIdInvisible(seconds, downloading_id);
    }

}