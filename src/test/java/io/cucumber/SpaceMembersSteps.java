package io.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.date.DateUtils;
import utils.entities.OCSpaceMember;
import utils.log.Log;
import utils.log.StepLogger;

public class SpaceMembersSteps {

    private World world;

    public SpaceMembersSteps(World world) {
        this.world = world;
    }

    @When("Alice adds {word} to the space {word} with")
    public void add_member_space(String userName, String spaceName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        world.spaceMembersPage.addMember(userName);
        Map<String, String> fields = table.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "permission" -> world.spaceMembersPage.setPermission(value);
                case "expirationDate" -> world.spaceMembersPage.setExpirationDate(value);
            }
        }
        world.spaceMembersPage.shareWithMember();

    }

    @Then("{word} should be member of the space {word} with")
    public void should_be_member_of_space(String userName, String spaceName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        OCSpaceMember member = world.graphAPI.getMemberOfSpace(spaceName, userName);
        Log.log(Level.FINE, "Member from backend: " + member.getDisplayName() +
                " " + member.getPermission() +
                " " + member.getExpirationDate());
        Map<String, String> fields = table.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "permission" -> {
                    // Local validation
                    assertTrue(world.spaceMembersPage.isUserMember(userName, value));
                    // Remote validation
                    assertTrue(member.getPermission().contains(value));
                }
                case "expirationDate" -> {
                    // Local validation
                    assertTrue(world.spaceMembersPage.isExpirationDateCorrect(userName, value));
                    // Remote validation
                    Log.log(Level.FINE, "Remote date: " + member.getExpirationDate());
                    if (value != null) {
                        // Normalize dates to compare them
                        String dateLocal = DateUtils.displayedDate(value);
                        String dateRemote = DateUtils.convertDate(member.getExpirationDate().substring(0, 10));
                        Log.log(Level.FINE, "Days: " + value);
                        Log.log(Level.FINE, "Date in server: " + dateRemote);
                        Log.log(Level.FINE, "Date in local: " + dateLocal);
                        assertEquals(dateLocal, dateRemote);
                    } else { // if value is null, remote should be null as well
                        assertEquals(null, member.getExpirationDate());
                    }
                }
            }
        }
    }
}
