/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.log.StepLogger;

public class SpacesSteps {

    private World world;

    public SpacesSteps(World world) {
        this.world = world;
    }

    @Given("the following spaces have been created in {word} account")
    public void spaces_have_been_created(String userName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String description = rows.get(1);
            world.graphAPI.createSpace(name, description, userName);
        }
    }

    @When("Alice selects the spaces view")
    public void user_selects_spaces_view() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListPage.openSpacesList();
    }

    @When("following space is disabled in server")
    public void space_disabled_server(DataTable table)
            throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String description = rows.get(1);
            world.graphAPI.disableSpace(name, description);
        }
    }

    @Then("Alice should see the following spaces")
    public void user_should_see_following_spaces(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        world.spacesPage.waitBySpaces();
        assertTrue(world.spacesPage.areAllSpacesVisible(listItems));
    }

    @Then("Alice should not see the following spaces")
    public void user_should_not_see_following_spaces(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        assertFalse(world.spacesPage.areAllSpacesVisible(listItems));
    }
}
