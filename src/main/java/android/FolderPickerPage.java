package android;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class FolderPickerPage extends CommonPage {

    private String documentstext_description = "LinearLayout-";
    private String chooseButton_id = "folder_picker_btn_choose";
    private String cancelButton_id = "folder_picker_btn_cancel";

    public FolderPickerPage() {
        super();
    }

    public void selectFolder(String targetFolder){
        MobileElement folder = (MobileElement)
                driver.findElementByAndroidUIAutomator("new UiSelector().description(\"" + documentstext_description + targetFolder + "\");");
        actions.click(folder).perform();
    }

    public void accept(){
        driver.findElement(MobileBy.id(chooseButton_id)).click();
    }

    public void cancel(){
        driver.findElement(MobileBy.id(cancelButton_id)).click();
    }
}
