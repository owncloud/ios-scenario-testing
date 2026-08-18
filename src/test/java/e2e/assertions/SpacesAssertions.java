/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.assertions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import e2e.model.OCSpace;
import e2e.support.log.Log;
import e2e.world.World;

public class SpacesAssertions {

    private final World world;

    public SpacesAssertions(World world) {
        this.world = world;
    }

    public void shouldSeeFollowingSpaces(String sense, List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle") != null ? row.get("subtitle") : "";
            if (sense.isEmpty()) {
                assertTrue(world.spacesPage().isSpaceVisible(name, subtitle));
            } else if (sense.equals(" not")) {
                assertFalse(world.spacesPage().isSpaceVisible(name, subtitle));
            }
        }
    }

    public void spaceInDisabledList(List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle") != null ? row.get("subtitle") : "";
            assertTrue(world.spacesPage().isSpaceInDisabledList(name, subtitle));
        }
    }

    public void spacesCreatedInServer(Map<String, String> fields) throws IOException {
        String name = fields.get("name");
        String subtitle = fields.get("subtitle") != null ? fields.get("subtitle") : "";
        List<OCSpace> spaces = world.graphAPI().getMySpaces();
        boolean matches = true;
        for (OCSpace space : spaces) {
            Log.log(Level.FINE, "Space in server: " + space.getName() + " " + space.getDescription());
            Log.log(Level.FINE, "Space in scenario: " + name + " " + subtitle);
            if (!(space.getName().equals(name) && space.getDescription().equals(subtitle))) {
                matches = false;
                break;
            }
        }
        assertTrue(matches);
    }

    public void spacesDisabledInServer(Map<String, String> fields) throws IOException {
        String name = fields.get("name");
        String subtitle = fields.get("subtitle") != null ? fields.get("subtitle") : "";
        List<OCSpace> spaces = world.graphAPI().getMySpaces();
        boolean matches = true;
        for (OCSpace space : spaces) {
            Log.log(Level.FINE, "Space in server: " + space.getName() + " "
                    + space.getDescription() + " " + space.getStatus());
            Log.log(Level.FINE, "Space in scenario: " + name + " " + subtitle);
            Log.log(Level.FINE, String.valueOf(space.getName().equals(name)));
            Log.log(Level.FINE, String.valueOf(space.getDescription().equals(subtitle)));
            if ((!(space.getName().equals(name) || space.getDescription().equals(subtitle)))
                    && (space.getStatus().equals("deleted"))) {
                matches = false;
                break;
            }
        }
        assertTrue(matches);
    }

    public void isNotMemberOfSpace(String userName) {
        assertFalse(world.spacesPage().isMemberOfSpace(userName));
    }
}
