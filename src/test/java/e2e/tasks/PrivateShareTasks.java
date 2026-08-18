/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.tasks;

import java.util.Map;

import e2e.world.World;

public class PrivateShareTasks {

    private final World world;

    public PrivateShareTasks(World world) {
        this.world = world;
    }

    public void inviteShareeWithFields(Map<String, String> fields) {
        world.sharePage().invite();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "sharee", "group" -> world.privateSharePage().searchSharee(entry.getValue());
                case "permissions" -> world.privateSharePage().setPermissions(entry.getValue());
                case "expiration" -> world.privateSharePage().setExpiration(entry.getValue());
            }
        }
        world.privateSharePage().savePermissions();
    }

    public void editShareWithFields(Map<String, String> fields) {
        String sharee = fields.get("sharee");
        world.sharePage().openPrivateShare(sharee);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "permissions" -> world.privateSharePage().setPermissions(entry.getValue());
                case "expiration" -> world.privateSharePage().setExpiration(entry.getValue());
            }
        }
        world.privateSharePage().saveChanges();
    }

    public void deleteShare(String sharee) {
        world.sharePage().openPrivateShare(sharee);
        world.privateSharePage().deletePrivateShare();
    }
}
