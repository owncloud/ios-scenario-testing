/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.tasks;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import e2e.world.World;

public class SpacesTasks {

    private final World world;

    public SpacesTasks(World world) {
        this.world = world;
    }

    public void selectSpacesView() {
        world.fileListPage().openSpacesList();
    }

    public void createSpace(Map<String, String> fields) {
        String name = fields.get("name");
        String subtitle = fields.get("subtitle") != null ? fields.get("subtitle") : "";
        world.spacesPage().createSpace(name, subtitle);
    }

    public void editSpace(Map<String, String> fields) {
        String name = fields.get("name");
        String subtitle = fields.get("subtitle") != null ? fields.get("subtitle") : "";
        world.spacesPage().editSpace(name, subtitle);
    }

    public void disableSpaces(List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle") != null ? row.get("subtitle") : "";
            world.spacesPage().disableSpace(name, subtitle);
        }
    }

    public void enableSpaces(List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            world.spacesPage().enableSpace(row.get("name"));
        }
    }

    public void showHideDisabledSpaces(String action) {
        switch (action) {
            case "shows" -> world.spacesPage().showDisabledSpaces();
            case "hides" -> world.spacesPage().hideDisabledSpaces();
        }
    }

    public void disableSpaceInServer(List<List<String>> listItems) throws IOException {
        for (List<String> rows : listItems) {
            world.graphAPI().disableSpace(rows.get(0), rows.get(1));
        }
    }

    public void openMembersMenu() {
        world.spacesPage().openMembers();
    }

    public void removeMemberFromSpace(String userName) {
        world.spacesPage().openEditMember(userName);
        world.spaceMembersPage().removeMember();
    }
}
