/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import java.util.ArrayList;
import java.util.logging.Level;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import ios.AppiumManager;
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class Hooks {

    private final World world;
    private final String bundleId = LocProperties.getProperties().getProperty("appPackage");

    public Hooks (World world){
        this.world = world;
    }

    //Before every scenario
    @Before
    public void setup(Scenario scenario){
        Log.log(Level.FINE, "======= START SCENARIO EXECUTION: " + scenario.getName() + "=======");
        AppiumManager.getManager().getDriver().activateApp(bundleId);
    }

    //After every scenario
    @After
    public void tearDown(Scenario scenario) throws Throwable {
        cleanUp();
        Log.log(Level.FINE, "======= END SCENARIO EXECUTION: " + scenario.getName() + "========\n\n");
        AppiumManager.getManager().getDriver().terminateApp(bundleId);
    }

    private void cleanUp() throws Throwable {
        ArrayList<OCFile> filesRoot = world.getFilesAPI().listItems("");
        //To remove everything shared with Alice
        world.getShareAPI().removeAllShares("bob");
        for (OCFile iterator : filesRoot) {
            world.getFilesAPI().removeItem(iterator.getName());
        }
        world.getTrashbinAPI().emptyTrashbin();
        if (world.getAuthAPI().checkAuthMethod().equals("OIDC")){ //remove spaces
            world.getGraphAPI().removeSpacesOfUser();
        }
    }
}
