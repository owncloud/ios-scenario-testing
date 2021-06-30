package io.cucumber;

import ios.AppiumManager;

import net.serenitybdd.cucumber.CucumberWithSerenity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.logging.Level;

import io.cucumber.junit.CucumberOptions;
import utils.log.Log;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin = {"pretty"})

public class RunCucumberTest {

    // Before the whole execution
    @BeforeClass
    public static void beforeclass(){
        Log.init();
        Log.log(Level.FINE, "START EXECUTION\n");
    }

    // After the whole execution
    @AfterClass
    public static void afterclass(){
        //remove the oC app
        AppiumManager.getManager().getDriver().quit();
        Log.log(Level.FINE, "END EXECUTION\n");
    }
}
