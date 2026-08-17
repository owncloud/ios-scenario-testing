/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.hooks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import e2e.pages.AppiumManager;
import e2e.world.World;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.LocProperties;
import e2e.model.OCFile;
import e2e.support.log.Log;

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
        Log.log(Level.FINE, "-------------------------------");
        Log.log(Level.FINE, "STARTS: CLEAN UP AFTER SCENARIO");
        Log.log(Level.FINE, "-------------------------------");
        //First, remove leftovers in root folder for every user
        ArrayList<String> userNames = new ArrayList<>(Arrays.asList("Alice", "Bob"));
        for (String userToClean: userNames) {
            ArrayList<OCFile> filesRoot = world.filesAPI().listItems("", userToClean);
            for (OCFile iterator : filesRoot) {
                world.filesAPI().removeItem(iterator.getName(), userToClean);
            }
            //Empty trashbin
            world.trashbinAPI().emptyTrashbin(userToClean);
        }
        if (System.getProperty("backend").equals("oCIS")){ //remove spaces
            world.graphAPI().removeSpacesOfUser();
        }
        Log.log(Level.FINE, "CLEAN UP ENDS");
    }
}
