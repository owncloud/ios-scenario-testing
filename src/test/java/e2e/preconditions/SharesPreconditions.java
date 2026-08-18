/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.preconditions;

import e2e.support.shares.ShareUtils;
import e2e.world.World;

public class SharesPreconditions {

    private final World world;

    public SharesPreconditions(World world) {
        this.world = world;
    }

    public void itemAlreadyShared(String sharingUser, int sharelevel, String itemName,
                                  String recipientUser, String permissions) throws Throwable {
        world.shareAPI().createShare(sharingUser, itemName, recipientUser, "0",
                ShareUtils.permissionToInt(permissions), "", "", sharelevel);
    }
}
