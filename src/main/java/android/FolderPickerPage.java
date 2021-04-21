package android;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class FolderPickerPage extends CommonPage {

    @iOSXCUITFindBy(id="Cancel")
    private MobileElement cancelButton;

    public FolderPickerPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void selectFolder(String targetFolder){
        Log.log(Level.FINE, "Start: Select folder from picker: " + targetFolder);
        driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\""+ targetFolder + " Actions" +"\"]")).click();
    }

    public void accept(String operation){
        Log.log(Level.FINE, "Start: Accept selection picker");
        if(operation.equals("move")){
            driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Move here\"]")).click();
        } else if(operation.equals("copy")) {
            driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Copy here\"]")).click();
        }
    }

    public void cancel(){
        Log.log(Level.FINE, "Start: Cancel selection picker");
        cancelButton.click();
    }
}
