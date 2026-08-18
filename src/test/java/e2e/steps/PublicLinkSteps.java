/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.steps;

import java.util.Map;
import java.util.logging.Level;

import e2e.world.World;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import e2e.support.log.StepLogger;

public class PublicLinkSteps {

    private final World world;

    public PublicLinkSteps(World world) {
        this.world = world;
    }

    @Given("Alice has shared the {itemtype} {word} by link")
    public void item_already_shared_by_link(String type, String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.publicLinkPreconditions().itemSharedByLink(itemName);
    }

    @When("Alice creates link on {itemtype} {word} with the following fields")
    public void create_link_with_fields(String type, String itemName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.publicLinkTasks().createLinkWithFields(fields);
    }

    @When("Alice edits the link on {word} with the following fields")
    public void edit_public_link(String itemName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.publicLinkTasks().editLinkWithFields(fields);
    }

    @When("Alice deletes the link on {word}")
    public void delete_link(String item) {
        StepLogger.logCurrentStep(Level.FINE);
        world.publicLinkTasks().deleteLink();
    }

    @Then("link should be created on {word} with the following fields")
    public void link_created_with_fields(String itemName, DataTable table) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.publicLinkAssertions().linkCreatedWithFields(itemName, fields);
    }

    @Then("link on {word} should not exist anymore")
    public void link_not_existing(String itemName) throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        world.publicLinkAssertions().linkNotExisting(itemName);
    }
}
