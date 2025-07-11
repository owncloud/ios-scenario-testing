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
import utils.entities.OCSpace;
import utils.log.Log;
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

    @Given("the following spaces have been disabled in Alice account")
    public void spaces_have_been_disabled(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String description = rows.get(1);
            world.graphAPI.disableSpace(name, description);
        }
    }

    @When("Alice selects the spaces view")
    public void user_selects_spaces_view() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListPage.openSpacesList();
    }

    @When("Alice selects to create a new space with the following fields")
    public void user_creates_new_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String description = rows.get(1);
            world.spacesPage.createSpace(name, description);
        }
    }

    @When("Alice selects to edit a space with the following fields")
    public void user_edit_new_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        String name = listItems.get(0).get(0);
        String description = listItems.get(0).get(1);
        world.spacesPage.editSpace(name, description);
    }

    @When("Alice selects to disable the following spaces")
    public void disable_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        String name = listItems.get(0).get(0);
        world.spacesPage.disableSpace(name);
    }

    @When("Alice selects to enable the following spaces")
    public void enable_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        String name = listItems.get(0).get(0);
        world.spacesPage.enableSpace(name);
    }

    @When("Alice {word} disabled spaces")
    public void show_disabled_spaces(String action) {
        StepLogger.logCurrentStep(Level.FINE);
        switch (action) {
            case ("shows") -> world.spacesPage.showDisabledSpaces();
            case ("hides") -> world.spacesPage.hideDisabledSpaces();
        }
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
        assertTrue(world.spacesPage.areAllSpacesNotVisible(listItems));
    }

    @Then("Alice should see the following spaces in the list of disabled spaces")
    public void space_in_disabled_list(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        assertTrue(world.spacesPage.isSpaceInDisabledList(listItems));
    }

    @Then("Spaces should be created/updated in server with the following fields")
    public void spaces_created_in_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        // Spaces in scenario definition
        List<List<String>> listItems = table.asLists();
        // Spaces in server
        List<OCSpace> spaces = world.graphAPI.getMySpaces();
        boolean matches = true;
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String description = rows.get(1);
            for (OCSpace space : spaces) {
                Log.log(Level.FINE, "Space in server: " + space.getName() + " "
                        + space.getDescription());
                Log.log(Level.FINE, "Space in scenario: " + name + " " + description);
                if (!(space.getName().equals(name) && space.getDescription().equals(description))) {
                    matches = false;
                    break;
                }
            }
        }
        // Check if all spaces in scenario definition match with spaces in server
        assertTrue(matches);
    }

    @Then("Spaces should be disabled in server with the following fields")
    public void spaces_disabled_in_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        // Spaces in scenario definition
        List<List<String>> listItems = table.asLists();
        // Spaces in server
        List<OCSpace> spaces = world.graphAPI.getMySpaces();
        boolean matches = true;
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String description = rows.get(1);
            for (OCSpace space : spaces) {
                Log.log(Level.FINE, "Space in server: " + space.getName() + " "
                        + space.getDescription() + " " + space.getStatus());
                Log.log(Level.FINE, "Space in scenario: " + name + " " + description);
                Log.log(Level.FINE, String.valueOf(space.getName().equals(name)));
                Log.log(Level.FINE, String.valueOf(space.getDescription().equals(description)));
                if ((!(space.getName().equals(name) || space.getDescription().equals(description)))
                        && (space.getStatus().equals("deleted"))) {
                    matches = false;
                    break;
                }
            }
        }
        // Check if all spaces in scenario definition match with spaces in server
        assertTrue(matches);
    }
}
