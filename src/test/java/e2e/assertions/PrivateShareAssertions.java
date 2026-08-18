/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.assertions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.logging.Level;

import e2e.model.OCShare;
import e2e.support.shares.ShareUtils;
import e2e.support.log.Log;
import e2e.world.World;

public class PrivateShareAssertions {

    private final World world;

    public PrivateShareAssertions(World world) {
        this.world = world;
    }

    public void userHasAccessToItem(String type, String shareeName, String itemName) throws Throwable {
        if (type.equals("user")) {
            assertTrue(world.shareAPI().isSharedWithMe(itemName, shareeName, false));
        } else if (type.equals("group")) {
            assertTrue(world.shareAPI().isSharedWithMe(itemName, shareeName, true));
        }
    }

    public void userDoesNotHaveAccessToItem(String userName, String itemName) throws Throwable {
        assertFalse(world.shareAPI().isSharedWithMe(itemName, userName, false));
    }

    public void shareCreatedWithFields(String itemName, Map<String, String> fields) throws Throwable {
        String sharee = "";
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "group" -> {
                    Log.log(Level.FINE, "Checking group: " + entry.getValue());
                    sharee = entry.getValue();
                    assertTrue(world.sharePage().isItemInListPrivateShares(entry.getValue()));
                    assertTrue(world.sharePage().isGroup());
                }
                case "sharee" -> {
                    Log.log(Level.FINE, "Checking sharee: " + entry.getValue());
                    sharee = entry.getValue();
                    assertTrue(world.sharePage().isItemInListPrivateShares(entry.getValue()));
                    assertFalse(world.sharePage().isGroup());
                }
                case "permissions" -> {
                    Log.log(Level.FINE, "Checking permissions: " + entry.getValue());
                    assertTrue(world.sharePage().isSharePermissionCorrect(entry.getValue()));
                }
            }
        }
        world.sharePage().openPrivateShare(sharee);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "sharee", "group" -> {
                    Log.log(Level.FINE, "Checking sharee/group: " + entry.getValue());
                    assertTrue(world.privateSharePage().isNameCorrect(entry.getValue()));
                }
                case "permissions" -> {
                    Log.log(Level.FINE, "Checking permission: " + entry.getValue());
                    assertTrue(world.privateSharePage().isPermissionEnabled(entry.getValue()));
                }
                case "expiration" -> {
                    Log.log(Level.FINE, "Checking expiration: " + entry.getValue());
                    assertTrue(world.privateSharePage().isExpirationCorrect(entry.getValue()));
                }
            }
        }
        OCShare share = world.shareAPI().getShare(itemName);
        assertTrue(ShareUtils.checkCorrectShare(share, fields));
    }

    public void shareIsDeleted(String sharee) {
        assertFalse(world.sharePage().isItemInListPrivateShares(sharee));
    }
}
