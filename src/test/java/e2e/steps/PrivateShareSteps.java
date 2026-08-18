/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.steps;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import e2e.world.World;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import e2e.support.log.StepLogger;

public class PrivateShareSteps {

    private final World world;

    public PrivateShareSteps(World world) {
        this.world = world;
    }

    @When("Alice selects the following {usertype} as sharee with the following fields")
    public void select_sharee_default(String type, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.privateShareTasks().inviteShareeWithFields(fields);
    }

    @When("Alice edits the share with the following fields")
    public void user_edits_share(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.privateShareTasks().editShareWithFields(fields);
    }

    @When("Alice deletes the share with")
    public void user_deletes_share(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        world.privateShareTasks().deleteShare(sharee);
    }

    @Then("{usertype} {word} should have access to {word}")
    public void user_has_acces_to_the_item(String type, String shareeName, String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.privateShareAssertions().userHasAccessToItem(type, shareeName, itemName);
    }

    @Then("user {word} should not have access to {word}")
    public void sharee_does_not_have_access(String userName, String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.privateShareAssertions().userDoesNotHaveAccessToItem(userName, itemName);
    }

    @Then("share should be created/edited on {word} with the following fields")
    public void share_created_with_fields(String itemName, DataTable table) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.privateShareAssertions().shareCreatedWithFields(itemName, fields);
    }

    @Then("{word} should not be shared anymore with")
    public void share_is_deleted(String itemName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        world.privateShareAssertions().shareIsDeleted(sharee);
    }
}
