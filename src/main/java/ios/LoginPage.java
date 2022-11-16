package ios;

import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class LoginPage extends CommonPage{

    @iOSXCUITFindBy(accessibility = "addServer")
    private MobileElement addServer;

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

    @iOSXCUITFindBy(accessibility = "access-files")
    private MobileElement bookmarkCell;

    @iOSXCUITFindBy(accessibility = "access-files")
    private List<MobileElement> bookmarkCells;

    @iOSXCUITFindBy(accessibility = "Personal")
    private MobileElement personal;

    @iOSXCUITFindBy(accessibility = "Continue")
    private MobileElement continueSafari;

    //For the regular tests
    private final String server = System.getProperty("server");

    public LoginPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean loggedIn(){
        Log.log(Level.FINE, "Logged: " + bookmarkCells.size());
        return bookmarkCells.size() > 0;
    }

    public void typeURL(){
        Log.log(Level.FINE, "Starts: Type URL.");
        urlServer.get(0).sendKeys(server);
        continueOption.click();
        approveIssue();
        continueOption.click();
    }

    public void approveIssue(){
        waitById(5, approveButton);
        approveButton.click();
    }

    public void cancelIssue(){
        cancelButton.click();
    }

    public void acceptPermissions(){
        driver.switchTo().alert().accept();
    }

    public void skipAddServer(){
        addServer.click();
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

    public void submitBrowser(){
        Log.log(Level.FINE, "Starts: Accept moving to browser");
        continueSafari.click();
    }

    public boolean isCredentialsError(){
        return bookmarkCells.size() == 0;
    }

    public boolean isBookmarkCreated(){
        return bookmarkCells.size() > 0;
    }

    public void selectBookmarkIndex(int index) {
        MobileElement firstServer = bookmarkCells.get(index);
        firstServer.click();
    }

    public void selectDrive() throws IOException {
        //assuming OIDC == oCIS. Bad, but works ftm
        if (authType.equals("OIDC")) {
            personal.click();
        }
    }

    public void selectFirstBookmark() throws IOException {
        bookmarkCell.click();
        selectDrive();
    }
}