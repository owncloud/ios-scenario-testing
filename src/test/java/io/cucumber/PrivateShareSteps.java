package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
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
        List<List<String>> listUser = table.asLists();
        String sharee = listUser.get(0).get(1);
        String permissions = listUser.get(1).get(1);
        String expiration = listUser.get(2).get(1);
        world.sharePage.invite();
        world.privateSharePage.searchSharee(sharee);
        world.privateSharePage.setPermissions(permissions);
        world.privateSharePage.setExpiration(expiration);
        world.privateSharePage.savePermissions();
    }

    @When("Alice edits the share with the following fields")
    public void user_edits_share(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> fieldList = table.asLists();
        String sharee = fieldList.get(0).get(1);
        String permissions = fieldList.get(1).get(1);
        String expiration = fieldList.get(2).get(1);
        world.sharePage.openPrivateShare(sharee);
        world.privateSharePage.setPermissions(permissions);
        world.privateSharePage.setExpiration(expiration);
        world.privateSharePage.saveChanges();
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
        String sharee = table.asLists().get(0).get(1);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "group" -> {
                    Log.log(Level.FINE, "Checking group: " + rows.get(1));
                    assertTrue(world.sharePage.isItemInListPrivateShares(rows.get(1)));
                    assertTrue(world.sharePage.isGroup());
                }
                case "sharee" -> {
                    Log.log(Level.FINE, "Checking sharee: " + rows.get(1));
                    assertTrue(world.sharePage.isItemInListPrivateShares(rows.get(1)));
                    assertFalse(world.sharePage.isGroup());
                }
                case "permissions" -> {
                    Log.log(Level.FINE, "Checking permissions: " + rows.get(1));
                    assertTrue(world.sharePage.isSharePermissionCorrect(rows.get(1)));
                }
            }
        }
        //1.2 Checking in share page
        world.sharePage.openPrivateShare(sharee);
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "sharee", "group" -> {
                    Log.log(Level.FINE, "Checking sharee/group: " + rows.get(1));
                    assertTrue(world.privateSharePage.isNameCorrect(rows.get(1)));
                }
                case "permission" -> {
                    Log.log(Level.FINE, "Checking permission: " + rows.get(1));
                    assertTrue(world.privateSharePage.isPermissionEnabled(rows.get(1)));
                }
                case "expiration" -> {
                    Log.log(Level.FINE, "Checking expiration: " + rows.get(1));
                    assertTrue(world.privateSharePage.isExpirationCorrect(rows.get(1)));
                }
            }
        }
        //Asserts in server via API
        OCShare share = world.shareAPI.getShare(itemName);
        assertTrue(world.sharePage.checkCorrectShare(share, listItems));
    }

    @Then("{word} should not be shared anymore with")
    public void share_is_deleted(String itemName, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        assertFalse(world.sharePage.isItemInListPrivateShares(sharee));
    }
}
