package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class FolderPickerPage extends CommonPage {

    @iOSXCUITFindBy(id="Cancel")
    private MobileElement cancelButton;

    @iOSXCUITFindBy(id="client.folder-create")
    private MobileElement createFolder;

    private String xpath_move = "//XCUIElementTypeButton[@name=\"Move here\"]";
    private String xpath_copy = "//XCUIElementTypeButton[@name=\"Copy here\"]";
    private String xpath_picker = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeOther";

    public FolderPickerPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void selectFolder(String targetFolder){
        Log.log(Level.FINE, "Start: Select folder from picker: " + targetFolder);
        waitById(10, createFolder);
        driver.findElement(By.xpath(xpath_picker))
                .findElement(By.xpath("//XCUIElementTypeCell[@name=\""+targetFolder+"\"]")).click();
    }

    public void createFolder(){
        Log.log(Level.FINE, "Start: Create folder");
        createFolder.click();
    }

    public void accept(String operation){
        Log.log(Level.FINE, "Start: Accept selection picker");
        if(operation.equals("move")){
            driver.findElement(By.xpath(xpath_move)).click();
        } else if(operation.equals("copy")) {
            driver.findElement(By.xpath(xpath_copy)).click();
        }
    }

    public void cancel(){
        Log.log(Level.FINE, "Start: Cancel selection picker");
        cancelButton.click();
    }
}
