package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCShare;
import utils.log.Log;

public class PrivateShareSteps {

    private World world;

    public PrivateShareSteps(World world) {
            this.world = world;
        }

    @When("Alice selects the following {usertype} as sharee with the following fields")
    public void select_sharee_default(String type, DataTable table) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listUser = table.asLists();
        String sharee = listUser.get(0).get(1);
        String permissions = listUser.get(1).get(1);
        String expiration = listUser.get(2).get(1);
        //boolean hasExpiration = "yes".equalsIgnoreCase(listUser.get(2).get(1));
        world.sharePage.invite();
        world.privateSharePage.searchSharee(sharee, type);
        world.privateSharePage.setPermissions(permissions);
        //if (hasExpiration) {
        world.privateSharePage.setExpiration(expiration);
        //}
        world.privateSharePage.savePermissions();
    }

    /*@When("Alice selects the following {usertype} as sharee without {word} permission")
    public void select_sharee_permissions(String type, String permission, DataTable table)
            throws InterruptedException, IOException, ParserConfigurationException, SAXException {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listUser = table.asLists();
        String sharee = listUser.get(0).get(1);
        world.sharePage.invite();
        world.privateSharePage.searchSharee(sharee, type);
        //world.privateSharePage.removeSharingPermission();
        world.privateSharePage.savePermissions();
    }*/

    @When("Alice edits the share with the following fields")
    public void user_edits_share(DataTable table) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        ;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> fieldList = table.asLists();
        String sharee = fieldList.get(0).get(1);
        String permissions = fieldList.get(1).get(1);
        String expiration = fieldList.get(2).get(1);
        world.sharePage.openPrivateShare(sharee);
        world.privateSharePage.setPermissions(permissions);
        //if (expiration.equals("yes")){
        world.privateSharePage.setExpiration(expiration);
        //}
        world.privateSharePage.saveChanges();
    }

    @When("Alice deletes the share with")
    public void user_deletes_share(DataTable table)
            throws InterruptedException {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        world.sharePage.openPrivateShare(sharee);
        world.privateSharePage.deletePrivateShare();
    }

    @Then("{usertype} {word} should have access to {word}")
    public void user_has_acces_to_the_item(String type, String shareeName, String itemName)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (type.equals("user")) {
            assertTrue(world.shareAPI.isSharedWithMe(itemName, shareeName, false));
        } else if (type.equals("group")) {
            assertTrue(world.shareAPI.isSharedWithMe(itemName, shareeName, true));
        }
    }

    @Then("user {word} should not have access to {word}")
    public void sharee_does_not_have_access(String userName, String itemName)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        ;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.shareAPI.isSharedWithMe(itemName, userName, false));
    }

    @Then("share should be created/edited on {word} with the following fields")
    public void share_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        //1.1 Checking in Shares page
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "sharee":
                case "group": {
                    Log.log(Level.FINE, "Checking sharee/group: " + rows.get(1));
                    assertTrue(world.sharePage.isItemInListPrivateShares(rows.get(1)));
                    break;
                }
                case "permissions": {
                    Log.log(Level.FINE, "Checking permissions: " + rows.get(1));
                    assertTrue(world.sharePage.displayedPermission(rows.get(1)));
                    break;
                }
                case "expiration": {
                    Log.log(Level.FINE, "Checking expiration: " + rows.get(1));
                    assertTrue(world.sharePage.isExpirationCorrect(rows.get(1)));
                }
                default:
                    break;
            }
        }
        //1.2 Checking in share page
        world.sharePage.openPrivateShare("Bob"); //Will be always the sharee... improve?
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "sharee":
                case "group": {
                    Log.log(Level.FINE, "Checking sharee/group: " + rows.get(1));
                    assertTrue(world.privateSharePage.isNameCorrect(rows.get(1)));
                    break;
                }
                case "permission": {
                    //TODO: Check how assert ticked value in UI
                    break;
                }
                case "expiration": {
                    assertTrue(world.privateSharePage.isExpirationCorrect(rows.get(1)));
                    break;
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = world.shareAPI.getShare(itemName);
        assertTrue(world.sharePage.checkCorrectShare(share, listItems));
    }

    @Then("{word} should not be shared anymore with")
    public void share_is_deleted(String itemName, DataTable table) {
        String stepName = new Object() {
        }.getClass().getEnclosingMethod().getName().toUpperCase();
        ;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        assertFalse(world.sharePage.isItemInListPrivateShares(sharee));
    }
}
