/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.assertions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import e2e.model.OCSpaceMember;
import e2e.support.date.DateUtils;
import e2e.support.log.Log;
import e2e.world.World;

public class SpaceMembersAssertions {

    private final World world;

    public SpaceMembersAssertions(World world) {
        this.world = world;
    }

    public void shouldBeMemberOfSpace(String userName, String spaceName, Map<String, String> fields) throws IOException {
        OCSpaceMember member = world.graphAPI().getMemberOfSpace(spaceName, userName);
        Log.log(Level.FINE, "Member from backend: " + member.getDisplayName() +
                " " + member.getPermission() +
                " " + member.getExpirationDate());
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "permission" -> {
                    assertTrue(world.spaceMembersPage().isUserMember(userName, entry.getValue()));
                    assertTrue(member.getPermission().contains(entry.getValue()));
                }
                case "expirationDate" -> {
                    assertTrue(world.spaceMembersPage().isExpirationDateCorrect(userName, entry.getValue()));
                    Log.log(Level.FINE, "Remote date: " + member.getExpirationDate());
                    if (entry.getValue() != null) {
                        String dateLocal = DateUtils.displayedDate(entry.getValue());
                        String dateRemote = DateUtils.convertDate(member.getExpirationDate().substring(0, 10));
                        Log.log(Level.FINE, "Days: " + entry.getValue());
                        Log.log(Level.FINE, "Date in server: " + dateRemote);
                        Log.log(Level.FINE, "Date in local: " + dateLocal);
                        assertEquals(dateLocal, dateRemote);
                    } else {
                        assertEquals(null, member.getExpirationDate());
                    }
                }
            }
        }
    }
}
