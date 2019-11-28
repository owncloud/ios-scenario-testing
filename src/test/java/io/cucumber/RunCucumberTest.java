package io.cucumber;

import android.AppiumManager;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTest {

    @AfterClass
    public static void afterclass(){
        AppiumManager.getManager().getDriver().quit();
    }
}
