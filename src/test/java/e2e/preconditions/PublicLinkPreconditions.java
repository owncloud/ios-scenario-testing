/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.preconditions;

import java.io.IOException;

import e2e.world.World;

public class PublicLinkPreconditions {

    private final World world;

    public PublicLinkPreconditions(World world) {
        this.world = world;
    }

    public void itemSharedByLink(String itemName) throws IOException {
        world.shareAPI().createShare("Alice", itemName, "", "3", "1", "", "aa55AA..", 0);
    }
}
