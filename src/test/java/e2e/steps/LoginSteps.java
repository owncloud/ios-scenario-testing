/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.steps;

import java.util.logging.Level;

import e2e.world.World;
import e2e.support.log.StepLogger;
import io.cucumber.java.en.Given;

public class LoginSteps {

    private final World world;

    public LoginSteps(World world) {
        this.world = world;
    }

    @Given("user {word} is logged in")
    public void logged(String userName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.loginPreconditions().logged(userName);
    }
}
