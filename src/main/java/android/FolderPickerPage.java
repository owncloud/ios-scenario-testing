package android;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import utils.log.Log;

public class FolderPickerPage extends CommonPage {

    private String chooseButton_id = "com.owncloud.android:id/folder_picker_btn_choose";
    private String cancelButton_id = "com.owncloud.android:id/folder_picker_btn_cancel";

    public FolderPickerPage() {
        super();
    }

    public void selectFolder(String targetFolder){
        Log.log(Level.FINE, "Start: Select folder from picker: " + targetFolder);
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\""+ targetFolder +"\");")).click();
    }

    public void accept(){
        Log.log(Level.FINE, "Start: Accept selection picker");
        driver.findElement(MobileBy.id(chooseButton_id)).click();
    }

    public void cancel(){
        Log.log(Level.FINE, "Start: Cancel selection picker");
        driver.findElement(MobileBy.id(cancelButton_id)).click();
    }
}
