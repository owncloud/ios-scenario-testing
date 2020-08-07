package io.cucumber;

import android.AppiumManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.logging.Level;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utils.log.Log;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty",
        "json:target/cucumber-reports/Cucumber.json",
        "html:target/cucumber-reports"})

public class RunCucumberTest {

    @BeforeClass
    public static void beforeclass(){
        Log.init();
        Log.log(Level.FINE, "START EXECUTION\n");
    }

    @AfterClass
    public static void afterclass(){
        //remove the oC app
        AppiumManager.getManager().getDriver().removeApp("com.owncloud.android");
        //remove Appium Settings
        AppiumManager.getManager().getDriver().removeApp("io.appium.settings");
        AppiumManager.getManager().cleanFolder(); //remove the oC folder from device
        AppiumManager.getManager().getDriver().quit();
        Log.log(Level.FINE, "END EXECUTION\n");
    }
}
