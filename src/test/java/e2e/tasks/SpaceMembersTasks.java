/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.tasks;

import java.util.Map;

import e2e.world.World;

public class SpaceMembersTasks {

    private final World world;

    public SpaceMembersTasks(World world) {
        this.world = world;
    }

    public void addMemberToSpace(String userName, Map<String, String> fields) {
        world.spaceMembersPage().addMember(userName);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "permission" -> world.spaceMembersPage().setPermission(entry.getValue());
                case "expirationDate" -> world.spaceMembersPage().setExpirationDate(entry.getValue());
            }
        }
        world.spaceMembersPage().shareWithMember();
    }

    public void editMemberInSpace(String userName, Map<String, String> fields) {
        world.spacesPage().openEditMember(userName);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "permission" -> world.spaceMembersPage().setPermission(entry.getValue());
                case "expirationDate" -> world.spaceMembersPage().editExpirationDate(entry.getValue());
            }
        }
        world.spaceMembersPage().saveChanges();
    }
}
