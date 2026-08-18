/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.preconditions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import e2e.world.World;

public class SpacesPreconditions {

    private final World world;

    public SpacesPreconditions(World world) {
        this.world = world;
    }

    public void spacesHaveBeenCreated(String userName, List<Map<String, String>> rows) throws IOException {
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle") != null ? row.get("subtitle") : "";
            world.graphAPI().createSpace(name, subtitle, userName);
        }
    }

    public void spacesHaveBeenDisabled(List<Map<String, String>> rows) throws IOException {
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle") != null ? row.get("subtitle") : "";
            world.graphAPI().disableSpace(name, subtitle);
        }
    }

    public void usersMembersOfSpace(String spaceName, List<Map<String, String>> rows) throws IOException {
        for (Map<String, String> row : rows) {
            String userName = row.get("user");
            String permission = row.get("permission");
            String expDate = row.get("expirationDate");
            String expirationDate = (expDate == null) ? "" : expDate.trim();
            world.graphAPI().addMemberToSpace(spaceName, userName, permission, expirationDate);
        }
    }
}
