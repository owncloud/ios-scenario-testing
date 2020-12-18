/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import android.AppiumManager;

import java.util.logging.Level;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.LocProperties;
import utils.log.Log;

public class Hooks {

    @Before
    public void setup(Scenario scenario){
        Log.log(Level.FINE, "START SCENARIO EXECUTION: " + scenario.getName());
        AppiumManager.getManager().getDriver().launchApp();
    }

    @After
    public void tearDown(Scenario scenario){
        Log.log(Level.FINE, "END SCENARIO EXECUTION: " + scenario.getName() + "\n\n");
        //AppiumManager.getManager().getDriver().removeApp(LocProperties.getProperties().getProperty("appPackage"));
    }
}
