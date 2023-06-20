package ios;

import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class LoginPage extends CommonPage{

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Add account\"]")
    private MobileElement addAccountButton;

    @iOSXCUITFindBy(accessibility = "row-url-url")
    private List<MobileElement> urlServer;

    @iOSXCUITFindBy(accessibility = "continue-bar-button")
    private MobileElement continueOption;

    @iOSXCUITFindBy(accessibility = "approve-button")
    private MobileElement approveButton;

    @iOSXCUITFindBy(accessibility = "cancel-button")
    private MobileElement cancelButton;

    @iOSXCUITFindBy(accessibility = "row-credentials-username")
    private MobileElement usernameInput;

    @iOSXCUITFindBy(accessibility = "row-credentials-password")
    private MobileElement passwordInput;

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
        continueOption.click();
        approveIssue();
        continueOption.click();
    }

    public void approveIssue(){
        approveButton.click();
    }

    public void addAccount(){
        addAccountButton.click();
    }

    public void typeCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: Type credentials: username: "
                + username + " - password: " + password);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void submitLogin(){
        Log.log(Level.FINE, "Starts: Submit login");
        continueOption.click();
    }

    public void selectDrive() {
        //assuming OIDC == oCIS. Bad, but works ftm
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