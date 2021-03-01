package android;

import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class DetailsPage extends CommonPage {

    @AndroidFindBy(id="com.owncloud.android:id/fdFilename")
    private MobileElement itemName;

    @AndroidFindBy(id="com.owncloud.android:id/fdType")
    private MobileElement itemType;

    @AndroidFindBy(id="com.owncloud.android:id/fdSize")
    private MobileElement itemSize;

    @AndroidFindBy(id="com.owncloud.android:id/fdProgressText")
    private MobileElement downloading;

    @AndroidFindBy(id="com.owncloud.android:id/text_preview")
    private MobileElement textPreview;

    @AndroidFindBy(id="toolbar")
    private List<MobileElement> toolbar;

    @AndroidFindBy(xpath="//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
    private MobileElement navigateUp;

    public DetailsPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getName(){
        return itemName.getText();
    }

    public String getType(){
        return itemType.getText();
    }

    public String getSize(){
        return itemSize.getText();
    }

    public void backListFiles() {
        Log.log(Level.FINE, "Start: Back to the list of files");
        navigateUp.click();
    }

    public void closeOpenIn(){
        Log.log(Level.FINE, "Start: Close Open In");
        //driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public boolean itemPreviewed(){
        return textPreview.isDisplayed();
    }

    public void waitFinishedDownload(int seconds){
        //waitByIdInvisible(seconds, downloading);
    }

    public void removeShareSheet(){
        if (toolbar.isEmpty()){
            driver.navigate().back();
        }
    }
}