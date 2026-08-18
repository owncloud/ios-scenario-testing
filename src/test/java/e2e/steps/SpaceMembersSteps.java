/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.steps;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import e2e.world.World;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import e2e.support.log.StepLogger;

public class SpaceMembersSteps {

    private final World world;

    public SpaceMembersSteps(World world) {
        this.world = world;
    }

    @When("Alice adds {word} to the space {word} with")
    public void add_member_space(String userName, String spaceName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.spaceMembersTasks().addMemberToSpace(userName, fields);
    }

    @When("Alice edits {word} from the space {word} with the following fields")
    public void edit_member_space(String userName, String spaceName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.spaceMembersTasks().editMemberInSpace(userName, fields);
    }

    @Then("{word} should be member of the space {word} with")
    public void should_be_member_of_space(String userName, String spaceName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.spaceMembersAssertions().shouldBeMemberOfSpace(userName, spaceName, fields);
    }
}
