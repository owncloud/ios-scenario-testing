package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class LoginPage extends CommonPage{

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Start setup\"]")
    private MobileElement startSetup;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@name=\"Server URL\"]")
    private List<MobileElement> urlServer;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Proceed\"]")
    private MobileElement proceed;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Back\"]")
    private MobileElement back;

    @iOSXCUITFindBy(accessibility = "Approve")
    private List<MobileElement> approveButton;

    @iOSXCUITFindBy(accessibility = "Cancel")
    private MobileElement cancelButton;

    @iOSXCUITFindBy(accessibility = "Server Username")
    private MobileElement usernameInput;

    @iOSXCUITFindBy(accessibility = "Server Password")
    private MobileElement passwordInput;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Login\"]")
    private MobileElement login;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Done\"]")
    private MobileElement done;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Personal\"]")
    private MobileElement personal;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Files\"]")
    private MobileElement files;

    //For the regular tests
    private final String server = System.getProperty("server");

    public LoginPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean loggedIn(){
        if (!findListId("Alice").isEmpty()) {
            Log.log(Level.FINE, "Logged IN");
            return true;
        } else {
            Log.log(Level.FINE, "Logged OUT");
            return false;
        }
    }

    public void typeURL(){
        Log.log(Level.FINE, "Starts: Type URL.");
        urlServer.get(0).sendKeys(server);
        proceed.click();
        if (approveButton.size() > 0) {
            approveIssue();
        }
    }

    public void approveIssue(){
        approveButton.get(0).click();
    }

    public void addAccount(){
        startSetup.click();
    }

    public void typeCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: Type credentials: username: "
                + username + " - password: " + password);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void submitLogin(){
        Log.log(Level.FINE, "Starts: Submit login");
        login.click();
        done.click();
    }

    public void selectDrive() {
        //assuming OIDC == oCIS. Bad, but works ftm
        selectFirstBookmark();
        if (authType.equals("OIDC")) {
            personal.click();
        } else {
            files.click();
        }
    }

    public void selectFirstBookmark() {
        findId("Alice").click();
    }
}