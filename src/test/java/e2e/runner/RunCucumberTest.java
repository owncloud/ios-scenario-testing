package e2e.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.logging.Level;

import e2e.pages.AppiumManager;
import e2e.support.log.Log;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = "e2e",
        features = "src/test/resources/features"
)

public class RunCucumberTest {

    // Before the whole execution
    @BeforeClass
    public static void beforeclass() {
        Log.init();
        Log.log(Level.FINE, "START EXECUTION\n");
    }

    // After the whole execution
    @AfterClass
    public static void afterclass() {
        //remove the oC app
        AppiumManager.getManager().getDriver().removeApp("com.owncloud.ios-app");
        AppiumManager.getManager().getDriver().quit();
        Log.log(Level.FINE, "END EXECUTION\n");
    }
}
