package android;

import io.appium.java_client.MobileBy;

public class FolderPickerPage extends CommonPage {

    private String chooseButton_id = "com.owncloud.android:id/folder_picker_btn_choose";
    private String cancelButton_id = "com.owncloud.android:id/folder_picker_btn_cancel";

    public FolderPickerPage() {
        super();
    }

    public void selectFolder(String targetFolder){
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().text(\""+ targetFolder +"\");")).click();
    }

    public void accept(){
        driver.findElement(MobileBy.id(chooseButton_id)).click();
    }

    public void cancel(){
        driver.findElement(MobileBy.id(cancelButton_id)).click();
    }
}
