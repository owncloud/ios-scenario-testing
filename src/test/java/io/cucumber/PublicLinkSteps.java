package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCShare;
import utils.log.Log;

public class PublicLinkSteps {

    private World world;

    public PublicLinkSteps(World world) {
        this.world = world;
    }

    @Given("Alice has shared the {itemtype} {word} by link")
    public void item_already_shared_by_link(String type, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getShareAPI().createShare("Alice", itemName, "", "3", "1"
                , "", "aa55AA.." ,0);
    }

    @When("Alice creates link on {itemtype} {word} with the following fields")
    public void create_link_with_fields(String type, String itemName, DataTable table) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().createLink();
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "password": {
                    world.getPublicLinkPage().setPassword(rows.get(1));
                    break;
                }
                case "permission": {
                    world.getPublicLinkPage().setPermission(rows.get(1));
                    break;
                }
                case "expiration": {
                    world.getPublicLinkPage().setExpiration(rows.get(1));
                    break;
                }
                case "name": {
                    world.getPublicLinkPage().setName(rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        world.getPublicLinkPage().submitLink();
    }

    @When("Alice edits the link on {word} with the following fields")
    public void edit_public_link(String itemName, DataTable table) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        world.getSharePage().openPublicLink();
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "permissions": {
                    world.getPublicLinkPage().setPermission(rows.get(1));
                    break;
                }
                case "password": {
                    world.getPublicLinkPage().setPassword(rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        world.getPublicLinkPage().saveChanges();
    }

    @When("Alice deletes the link on {word}")
    public void delete_link(String item) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().openPublicLink();
        world.getPublicLinkPage().deleteLink();
    }

    @Then("link should be created on {word} with the following fields")
    public void link_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        List<List<String>> listItems = table.asLists();
        world.getSharePage().openPublicLink();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "password": {
                    assertTrue(world.getPublicLinkPage().isPasswordEnabled(itemName));
                    break;
                }
                case "permission": {
                    //TODO: Check how assert ticked value in UI
                    break;
                }
                case "expiration": {
                    assertTrue(world.getPublicLinkPage().isExpirationCorrect(rows.get(1)));
                    break;
                }
                case "name": {
                    assertTrue(world.getPublicLinkPage().isNameCorrect(rows.get(1)));
                    break;
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = world.getShareAPI().getShare(itemName);
        assertTrue(world.getSharePage().checkCorrectShare(share, listItems));
    }

    @Then("link on {word} should not exist anymore")
    public void link_not_existing(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getSharePage().isItemInListLinks());
        ArrayList<OCShare> shares = world.getShareAPI().getLinksByUser("");
        assertTrue(shares.isEmpty());
    }
}
