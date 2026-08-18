/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.steps;

import java.util.logging.Level;

import e2e.world.World;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import e2e.support.log.StepLogger;

public class SharesSteps {

    private final World world;

    public SharesSteps(World world) {
        this.world = world;
    }

    @ParameterType("user|group")
    public String usertype(String type) {
        return type;
    }

    @ParameterType("shared|reshared")
    public int sharelevel(String type) {
        if (type.equals("shared")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Given("{word} has {sharelevel} {itemtype} {word} with {usertype} {word} with {word} permissions")
    public void item_already_shared(String sharingUser, int sharelevel, String type, String itemName,
                                    String userType, String recipientUser, String permissions) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.sharesPreconditions().itemAlreadyShared(sharingUser, sharelevel, itemName, recipientUser, permissions);
    }
}
