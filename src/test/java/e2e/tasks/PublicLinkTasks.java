/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.tasks;

import java.util.Map;

import e2e.world.World;

public class PublicLinkTasks {

    private final World world;

    public PublicLinkTasks(World world) {
        this.world = world;
    }

    public void createLinkWithFields(Map<String, String> fields) {
        world.sharePage().createLink();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "password" -> world.publicLinkPage().setPassword(entry.getValue());
                case "password-auto" -> world.publicLinkPage().setPasswordAuto();
                case "permissions" -> world.publicLinkPage().setPermission(entry.getValue());
                case "expiration" -> world.publicLinkPage().setExpiration(entry.getValue());
                case "name" -> world.publicLinkPage().setName(entry.getValue());
            }
        }
        world.publicLinkPage().submitLink();
    }

    public void editLinkWithFields(Map<String, String> fields) {
        world.sharePage().openPublicLink();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "permissions" -> world.publicLinkPage().setPermission(entry.getValue());
                case "password" -> world.publicLinkPage().setPassword(entry.getValue());
                case "expiration" -> world.publicLinkPage().setExpiration(entry.getValue());
            }
        }
        world.publicLinkPage().saveChanges();
    }

    public void deleteLink() {
        world.sharePage().openPublicLink();
        world.publicLinkPage().deleteLink();
    }
}
