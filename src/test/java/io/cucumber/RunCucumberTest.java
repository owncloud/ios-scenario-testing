package io.cucumber;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.logging.Level;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import ios.AppiumManager;
import utils.log.Log;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})

public class RunCucumberTest {

    // Before the whole execution
    @BeforeClass
    public static void beforeclass(){
        Log.init();
        Log.log(Level.FINE, "START EXECUTION\n");
        //Accepting required system permissions
        AppiumManager.getManager().getDriver().switchTo().alert().accept();
    }

    // After the whole execution
    @AfterClass
    public static void afterclass(){
        //remove the oC app
        AppiumManager.getManager().getDriver().removeApp("com.owncloud.ios-app");
        AppiumManager.getManager().getDriver().quit();
        Log.log(Level.FINE, "END EXECUTION\n");
    }
}
