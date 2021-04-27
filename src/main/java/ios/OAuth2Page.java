package ios;

import java.util.logging.Level;

import utils.log.Log;

public class OAuth2Page extends CommonPage {

    private long deviceVersion;

    public OAuth2Page(){
        super();
    }

    public void enterCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: enter OAuth2 credentials");
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OAuth2");
    }
}