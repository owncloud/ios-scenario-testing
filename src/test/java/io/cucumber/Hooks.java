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
import utils.api.FilesAPI;
import utils.api.TrashbinAPI;
import utils.entities.OCFile;
import utils.log.Log;

public class Hooks {

    private World world;

    public Hooks (World world){
        this.world = world;
    }

    //Before every scenario
    @Before
    public void setup(Scenario scenario){
        Log.log(Level.FINE, "START SCENARIO EXECUTION: " + scenario.getName());
        AppiumManager.getManager().getDriver().launchApp();
    }

    //After every scenario
    @After
    public void tearDown(Scenario scenario) throws Throwable {
        cleanUp();
        Log.log(Level.FINE, "END SCENARIO EXECUTION: " + scenario.getName() + "\n\n");
        AppiumManager.getManager().getDriver().terminateApp("com.owncloud.ios-app");
    }

    private void cleanUp() throws Throwable {
        FilesAPI filesAPI = new FilesAPI();
        TrashbinAPI trashbinAPI = new TrashbinAPI();
        ArrayList<OCFile> filesRoot = filesAPI.listItems("");
        for (OCFile iterator: filesRoot){
            filesAPI.removeItem(iterator.getName());
        }
        trashbinAPI.emptyTrashbin();
        if (world.getAuthAPI().checkAuthMethod().equals("OIDC")){ //remove spaces
            world.getGraphAPI().removeSpacesOfUser();
        }
    }
}
