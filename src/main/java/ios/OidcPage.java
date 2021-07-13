package ios;

import java.util.logging.Level;

import utils.log.Log;

public class OidcPage extends CommonPage {

    public OidcPage(){
        super();
    }

    public void enterCredentials(String userName, String password){
        Log.log(Level.FINE, "Starts: enter OIDC credentials");
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OIDC");
    }
}
