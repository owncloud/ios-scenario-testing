package ios;

import java.util.logging.Level;

import utils.log.Log;

public class OidcPage extends CommonPage {

    private final String continue_id = "Continue";
    private final String showDetails_id = "Show Details";
    private final String visitThisWebsite_id = "visit this website";
    private final String visitWebsite_id = "Visit Website";
    private final String chooseAccount_xpath = "//XCUIElementTypeStaticText[@name=\"Choose an account\"]";
    private final String userOtherAccount_id = "? Use another account";
    private final String username_xpath = "//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[2]/XCUIElementTypeTextField";
    private final String password_xpath = "//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[3]/XCUIElementTypeSecureTextField";
    private final String loginButton_id = "Log in";
    private final String allow_id = "Allow";

    public OidcPage(){
        super();
    }

    public void enterCredentials(String userName, String password){
        Log.log(Level.FINE, "Starts: enter OIDC credentials");

        findId(continue_id).click();
        //To handle warning about opening a non-secure connection
        if (!findListId(showDetails_id).isEmpty()){
            findId(showDetails_id).click();
            findId(visitThisWebsite_id).click();
            findId(visitWebsite_id).click();
        }
        if (!findListXpath(chooseAccount_xpath).isEmpty()){
            findId(userOtherAccount_id).click();
        }
        Log.log(Level.FINE, "Credentials: " + userName + " " + password);
        //To dismiss soft keyboard if it is displayed
        if (!findListId("Done").isEmpty()) {
            Log.log(Level.FINE, "Closing keyboard");
            findId("Done").click();
        }
        findXpath(username_xpath).sendKeys(userName);
        findXpath(password_xpath).sendKeys(password);
        findId(loginButton_id).click();
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OIDC");
        findId(allow_id).click();
    }
}
