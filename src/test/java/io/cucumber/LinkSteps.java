package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCShare;
import utils.log.Log;

public class LinkSteps {

    private World world;

    public LinkSteps(World world) {
        this.world = world;
    }

    @Given("Alice has shared the {itemtype} {word} by link")
    public void item_already_shared_by_link(String type, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.shareAPI.createShare("Alice", itemName, "", "3", "1", itemName + " link", 0);
    }

    @When("Alice creates link on {itemtype} {word} with the following fields")
    public void create_link_with_fields(String type, String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.publicLinkPage.createLink(itemName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    world.linkPermissionsPage.addLinkName(rows.get(1));
                    break;
                }
                case "password": {
                    world.linkPermissionsPage.addPassword(itemName, rows.get(1));
                    break;
                }
                case "permission": {
                    world.linkPermissionsPage.setPermission(rows.get(1));
                    break;
                }
                case "expiration days": {
                    world.linkPermissionsPage.setExpiration(type, rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        world.linkPermissionsPage.submitLink();
    }

    @When("Alice edits the link on {word} with the following fields")
    public void edit_public_link(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        world.publicLinkPage.openPublicLink(itemName + " link");
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    world.linkPermissionsPage.addLinkName(rows.get(1));
                    break;
                }
                case "permissions": {
                    world.linkPermissionsPage.setPermission(rows.get(1));
                    break;
                }
                case "password": {
                    world.linkPermissionsPage.addPassword(itemName, rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        world.linkPermissionsPage.backToLinksList();
    }

    @When("Alice deletes the link on {word}")
    public void delete_link(String item) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.publicLinkPage.openPublicLink(item + " link");
        world.linkPermissionsPage.deleteLink();
    }

    @When("Alice closes Public Links")
    public void close_public_links() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.publicLinkPage.close();
    }

    @When("Alice opens public link with name {word}")
    public void opens_public_links_with_name(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.publicLinkPage.openPublicLink(itemName);
    }

    @Then("link should be created on {word} with the following fields")
    public void link_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        String linkName = "";
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "name": {
                    linkName = rows.get(1);
                    assertTrue(world.publicLinkPage.isItemInListLinks(rows.get(1)));
                    break;
                }
                case "password": {
                    world.publicLinkPage.openPublicLink(linkName);
                    assertTrue(world.linkPermissionsPage.isPasswordEnabled(itemName));
                    break;
                }
                case "permission": {
                    world.publicLinkPage.openPublicLink(linkName);
                    assertTrue(world.linkPermissionsPage.checkPermissions(rows.get(1)));
                    break;
                }
                case "expiration days": {
                    world.publicLinkPage.openPublicLink(linkName);
                    assertTrue(world.linkPermissionsPage.checkExpiration(rows.get(1)));
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

    @Then("link on {word} should not exist anymore")
    public void link_not_existing(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.publicLinkPage.isItemInListLinks(itemName + " link"));
        assertTrue(world.shareAPI.getSharesByUser("").isEmpty());
    }
}
