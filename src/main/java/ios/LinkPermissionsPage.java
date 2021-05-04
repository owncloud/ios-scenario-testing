package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.date.DateUtils;
import utils.log.Log;

public class LinkPermissionsPage extends CommonPage {

    @iOSXCUITFindBy(accessibility = "name-text-row")
    private MobileElement nameLink;

    @iOSXCUITFindBy(accessibility = "password-switch-row")
    private MobileElement passwordSwitch;

    @iOSXCUITFindBy(accessibility = "password-field-row")
    private MobileElement passwordText;

    @iOSXCUITFindBy(accessibility = "expire-row")
    private List<MobileElement> expirationSwitch;

    @iOSXCUITFindBy(accessibility = "expire-date-row")
    private MobileElement expirationDate;

    @iOSXCUITFindBy(accessibility="Create")
    private MobileElement createButton;

    @iOSXCUITFindBy(accessibility="Cancel")
    private MobileElement cancelButton;

    @iOSXCUITFindBy(id="Download / View")
    private MobileElement downloadViewOption;

    @iOSXCUITFindBy(id = "Download / View / Upload")
    private MobileElement downloadViewUploadOption;

    @iOSXCUITFindBy(id = "Upload only (File Drop)")
    private MobileElement uploadOnlyOption;

    @iOSXCUITFindBy(accessibility = "Delete")
    private MobileElement deleteLink;

    @iOSXCUITFindBy(id="Links")
    private MobileElement backToLinks;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[3]")
    private MobileElement downloadViewOptionCell;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[3]")
    private MobileElement downloadViewUploadOptionCell;

    @iOSXCUITFindBy(xpath="//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[4]")
    private MobileElement uploadOnlyOptionCell;

    //Non-static UI components. Must be matched in specific scenarios
    private String MonthPicker_xpath = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
            "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[8]/" +
            "XCUIElementTypePicker/XCUIElementTypePickerWheel[1]";
    private String DayPicker_xpath = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
            "/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[8]/XCUIElementTypePicker/XCUIElementTypePickerWheel[2]";
    private String YearPicker_xpath = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[8]/XCUIElementTypePicker/XCUIElementTypePickerWheel[3]";


    private String MonthPicker_xpath_file = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
            "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[5]/" +
            "XCUIElementTypePicker/XCUIElementTypePickerWheel[1]";
    private String DayPicker_xpath_file = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther" +
            "/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[5]/XCUIElementTypePicker/XCUIElementTypePickerWheel[2]";
    private String YearPicker_xpath_file = "//XCUIElementTypeApplication[@name=\"ownCloud\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[5]/XCUIElementTypePicker/XCUIElementTypePickerWheel[3]";

    public LinkPermissionsPage(){
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void addLinkName (String linkName) {
        Log.log(Level.FINE, "Starts: Add link name: " + linkName);
        nameLink.clear();
        nameLink.sendKeys(linkName);
    }

    public void addPassword (String itemName, String password) throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Add link password: " + password);
        passwordSwitch.click();
        passwordText.sendKeys(password);
    }

    public void setPermission (String permission) {
        Log.log(Level.FINE, "Starts: Set link permission: " + permission);
        switch (permission){
            case("1"):{
                downloadViewOption.click();
                break;
            }
            case("15"):{
                downloadViewUploadOption.click();
                break;
            }
            case("4"):{
                uploadOnlyOption.click();
                break;
            }
        }
    }

    public void setExpiration (String type, String days){
        Log.log(Level.FINE, "Starts: Set Expiration date in days: " + days);
        String dateToExpire = DateUtils.dateInDaysiOSFormat(days);
        String dateFragments[] = dateToExpire.split(" ");
        String month = dateFragments[1];
        String day = dateFragments[0];
        String year = dateFragments[2];
        Log.log(Level.FINE, "Month: " + month + " Day : " + day + " Year: " + year);
        expirationSwitch.get(0).click();
        expirationDate.click();
        switch (type) {
            default:
            case "folder":
                driver.findElement(By.xpath(MonthPicker_xpath)).sendKeys(month);
                driver.findElement(By.xpath(DayPicker_xpath)).sendKeys(day);
                driver.findElement(By.xpath(YearPicker_xpath)).sendKeys(year);
            break;
            case "file":
                driver.findElement(By.xpath(MonthPicker_xpath_file)).sendKeys(month);
                driver.findElement(By.xpath(DayPicker_xpath_file)).sendKeys(day);
                driver.findElement(By.xpath(YearPicker_xpath_file)).sendKeys(year);
            break;
        }
    }

    public boolean isPasswordEnabled(String itemName) {
        boolean switchEnabled = passwordSwitch.isEnabled();
        boolean passVisible = passwordText.isDisplayed();
        return switchEnabled && passVisible;
    }

    public boolean checkPermissions(String permissions){
        Log.log(Level.FINE, "Starts: Check permissions: " + permissions);
        switch (permissions){
            case("1"):{
                if (parseIntBool(downloadViewOptionCell.getAttribute("selected")) == true ){
                    Log.log(Level.FINE, "Download / View is selected");
                    return true;
                }
            }
            case("15"):{
                if (parseIntBool(downloadViewUploadOptionCell.getAttribute("selected")) == true ){
                    Log.log(Level.FINE, "Download / View / Upload is selected");
                    return true;
                }
            }
            case("4"):{
                if (parseIntBool(uploadOnlyOptionCell.getAttribute("selected")) == true ){
                    Log.log(Level.FINE, "Upload only is selected");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkExpiration(String days){
        Log.log(Level.FINE, "Starts: Check expiration in days: " + days);
        boolean switchEnabled = parseIntBool(expirationSwitch.get(0).getAttribute("enabled"));
        String date = DateUtils.dateInDaysiOSFormat(days);
        String dateFragments[] = date.split(" ");
        String month = dateFragments[1];
        String day = dateFragments[0];
        String year = dateFragments[2];
        String displayedDate = month + " " + day + ", " + year;
        Log.log(Level.FINE, "Date to check in the screen: " + displayedDate);
        boolean dateCorrect = !driver.findElements(By.id(displayedDate)).isEmpty();
        return switchEnabled && dateCorrect;
    }

    public void close(){
        Log.log(Level.FINE, "Starts: Cancel public link view");
        takeScreenshot("PublicShare/ItemStatusBeforeClosing");
        cancelButton.click();
    }

    public void submitLink() {
        Log.log(Level.FINE, "Starts: Submit public link");
        takeScreenshot("PublicShare/Submit");
        createButton.click();
    }

    public void deleteLink(){
        deleteLink.click();
        takeScreenshot("PublicShare/Deletion");
    }

    public void backToLinksList() {
        Log.log(Level.FINE, "Starts: Back to links list");
        backToLinks.click();
    }

    private boolean parseIntBool(String s){
        return Boolean.parseBoolean(s);
    }
}
