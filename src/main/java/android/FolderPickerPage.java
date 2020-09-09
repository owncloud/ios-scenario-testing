package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class FolderPickerPage extends CommonPage {

    @AndroidFindBy(id="com.owncloud.android:id/folder_picker_btn_choose")
    private MobileElement chooseButton;

    @AndroidFindBy(id="com.owncloud.android:id/folder_picker_btn_cancel")
    private MobileElement cancelButton;

    public FolderPickerPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void selectFolder(String targetFolder){
        Log.log(Level.FINE, "Start: Select folder from picker: " + targetFolder);
        waitByTextVisible(10, targetFolder);
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\""+ targetFolder +"\");")).click();
    }

    public void accept(){
        Log.log(Level.FINE, "Start: Accept selection picker");
        chooseButton.click();
    }

    public void cancel(){
        Log.log(Level.FINE, "Start: Cancel selection picker");
        cancelButton.click();
    }
}
