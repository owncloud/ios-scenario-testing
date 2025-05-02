package io.cucumber;

import java.util.logging.Level;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import utils.log.StepLogger;

public class SharesSteps {

    private World world;

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
            return 0; //share, first level
        } else {
            return 1; //reshare
        }
    }

    @Given("{word} has {sharelevel} {itemtype} {word} with {usertype} {word} with {word} permissions")
    public void item_already_shared(String sharingUser, int sharelevel, String type, String itemName,
                                    String userType, String recipientUser, String permissions) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.shareAPI.createShare(sharingUser, itemName, recipientUser, "0",
                world.sharePage.translatePermissionsToInt(permissions), "", "", sharelevel);
    }
}
