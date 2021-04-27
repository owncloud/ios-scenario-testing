package ios;

import java.util.logging.Level;

import utils.log.Log;

public class KopanoPage extends CommonPage {

    public KopanoPage(){
        super();
    }

    public void enterCredentials(String username, String password){
        Log.log(Level.FINE, "Starts: enter OIDC credentials");
    }

    public void authorize(){
        Log.log(Level.FINE, "Starts: Authorize OIDC");
    }
}
