/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import e2e.world.World;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import e2e.support.log.StepLogger;

public class SpacesSteps {

    private final World world;

    public SpacesSteps(World world) {
        this.world = world;
    }

    @Given("the following spaces have been created in {word} account")
    public void spaces_have_been_created(String userName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.spacesPreconditions().spacesHaveBeenCreated(userName, rows);
    }

    @Given("the following spaces have been disabled in Alice account")
    public void spaces_have_been_disabled(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.spacesPreconditions().spacesHaveBeenDisabled(rows);
    }

    @Given("the following users are members of the space {word}")
    public void users_members_of_space(String spaceName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.spacesPreconditions().usersMembersOfSpace(spaceName, rows);
    }

    @When("Alice selects the spaces view")
    public void user_selects_spaces_view() {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesTasks().selectSpacesView();
    }

    @When("Alice selects to create a new space with the following fields")
    public void user_creates_new_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.spacesTasks().createSpace(fields);
    }

    @When("Alice selects to edit a space with the following fields")
    public void user_edit_new_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.spacesTasks().editSpace(fields);
    }

    @When("Alice selects to disable the following spaces")
    public void disable_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.spacesTasks().disableSpaces(rows);
    }

    @When("Alice selects to enable the following spaces")
    public void enable_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.spacesTasks().enableSpaces(rows);
    }

    @When("Alice {word} disabled spaces")
    public void show_disabled_spaces(String action) {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesTasks().showHideDisabledSpaces(action);
    }

    @When("the following space(s) is disabled in server")
    public void space_disabled_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesTasks().disableSpaceInServer(table.asLists());
    }

    @When("Alice opens the members menu")
    public void opens_members_menu() {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesTasks().openMembersMenu();
    }

    @When("Alice removes {word} from the space {word}")
    public void remove_user_form_space(String userName, String spaceName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesTasks().removeMemberFromSpace(userName);
    }

    @Then("Alice should{typePosNeg} see the following spaces")
    public void user_should_see_following_spaces(String sense, DataTable table) throws InterruptedException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.spacesAssertions().shouldSeeFollowingSpaces(sense, rows);
    }

    @Then("Alice should see the following spaces in the list of disabled spaces")
    public void space_in_disabled_list(DataTable table) throws InterruptedException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        world.spacesAssertions().spaceInDisabledList(rows);
    }

    @Then("Spaces should be created/updated in server with the following fields")
    public void spaces_created_in_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.spacesAssertions().spacesCreatedInServer(fields);
    }

    @Then("Spaces should be disabled in server with the following fields")
    public void spaces_disabled_in_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.spacesAssertions().spacesDisabledInServer(fields);
    }

    @Then("{word} should not be member of the space {word}")
    public void is_user_member(String userName, String spaceName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesAssertions().isNotMemberOfSpace(userName);
    }
}
