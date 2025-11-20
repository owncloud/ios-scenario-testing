package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCShare;
import utils.log.Log;
import utils.log.StepLogger;

public class PrivateShareSteps {

    private World world;

    public PrivateShareSteps(World world) {
            this.world = world;
        }

    @When("Alice selects the following {usertype} as sharee with the following fields")
    public void select_sharee_default(String type, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.sharePage.invite();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key){
                case "sharee", "group" -> world.privateSharePage.searchSharee(value);
                case "permissions" -> world.privateSharePage.setPermissions(value);
                case "expiration" -> world.privateSharePage.setExpiration(value);
            }
        }
        world.privateSharePage.savePermissions();
    }

    @When("Alice edits the share with the following fields")
    public void user_edits_share(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        //List<List<String>> fieldList = table.asLists();
        Map<String, String> fields = table.asMap(String.class, String.class);
        // To open the correct menu
        String sharee = fields.get("sharee");
        world.sharePage.openPrivateShare(sharee);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "permissions" -> world.privateSharePage.setPermissions(value);
                case "expiration" -> world.privateSharePage.setExpiration(value);
            }
            world.privateSharePage.saveChanges();
        }
    }

    @When("Alice deletes the share with")
    public void user_deletes_share(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        world.sharePage.openPrivateShare(sharee);
        world.privateSharePage.deletePrivateShare();
    }

    @Then("{usertype} {word} should have access to {word}")
    public void user_has_acces_to_the_item(String type, String shareeName, String itemName)
            throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        if (type.equals("user")) {
            assertTrue(world.shareAPI.isSharedWithMe(itemName, shareeName, false));
        } else if (type.equals("group")) {
            assertTrue(world.shareAPI.isSharedWithMe(itemName, shareeName, true));
        }
    }

    @Then("user {word} should not have access to {word}")
    public void sharee_does_not_have_access(String userName, String itemName)
            throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        assertFalse(world.shareAPI.isSharedWithMe(itemName, userName, false));
    }

    @Then("share should be created/edited on {word} with the following fields")
    public void share_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        StepLogger.logCurrentStep(Level.FINE);
        //Asserts in UI
        //1.1 Checking in Shares page
        String sharee = "";
        //List<List<String>> listItems = table.asLists();
        Map<String, String> fields = table.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "group" -> {
                    Log.log(Level.FINE, "Checking group: " + value);
                    sharee = value;
                    assertTrue(world.sharePage.isItemInListPrivateShares(value));
                    assertTrue(world.sharePage.isGroup());
                }
                case "sharee" -> {
                    Log.log(Level.FINE, "Checking sharee: " + value);
                    sharee = value;
                    assertTrue(world.sharePage.isItemInListPrivateShares(value));
                    assertFalse(world.sharePage.isGroup());
                }
                case "permissions" -> {
                    Log.log(Level.FINE, "Checking permissions: " + value);
                    assertTrue(world.sharePage.isSharePermissionCorrect(value));
                }
            }
        }
        //1.2 Checking in share page
        world.sharePage.openPrivateShare(sharee);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "sharee", "group" -> {
                    Log.log(Level.FINE, "Checking sharee/group: " + value);
                    assertTrue(world.privateSharePage.isNameCorrect(value));
                }
                case "permissions" -> {
                    Log.log(Level.FINE, "Checking permission: " + value);
                    assertTrue(world.privateSharePage.isPermissionEnabled(value));
                }
                case "expiration" -> {
                    Log.log(Level.FINE, "Checking expiration: " + value);
                    assertTrue(world.privateSharePage.isExpirationCorrect(value));
                }
            }
        }
        //Asserts in server via API
        OCShare share = world.shareAPI.getShare(itemName);
        assertTrue(world.sharePage.checkCorrectShare(share, fields));
    }

    @Then("{word} should not be shared anymore with")
    public void share_is_deleted(String itemName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        assertFalse(world.sharePage.isItemInListPrivateShares(sharee));
    }
}
