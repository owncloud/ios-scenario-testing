package io.cucumber;

import ios.PublicLinkPage;
import ios.SharePage;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.StepEventBus;

import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.ShareAPI;
import utils.entities.OCShare;
import utils.log.Log;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LinkSteps {

    //Involved pages
    @Steps
    protected SharePage sharePage;

    @Steps
    protected PublicLinkPage publicLinkPage;

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();

    @Given("the item (.+) has been already shared by link")
    public void item_already_shared_by_link(String itemName)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        shareAPI.createShare(itemName, "", "3", "1", itemName + " link");
    }

    @When("^user creates link on (.+) with the following fields$")
    public void i_select_to_link_with_fields(String itemName, DataTable table)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        publicLinkPage.createLink(itemName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    publicLinkPage.addLinkName(rows.get(1));
                    break;
                }
                case "password": {
                    publicLinkPage.addPassword(itemName, rows.get(1));
                    break;
                }
                case "permission": {
                    publicLinkPage.setPermission(rows.get(1));
                    break;
                }
                case "expiration days": {
                    publicLinkPage.setExpiration(rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        publicLinkPage.submitLink();
    }

    @When("^user edits the link on (.+) with the following fields$")
    public void user_edits_public_link(String itemName, DataTable table)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        List<List<String>> listItems = table.asLists();
        publicLinkPage.openPublicLink(itemName + " link");
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    publicLinkPage.addLinkName(rows.get(1));
                    break;
                }
                case "permissions": {
                    Log.log(Level.FINE, "Set permission: " + rows.get(1));
                    publicLinkPage.setPermission(rows.get(1));
                    break;
                }
                case "password": {
                    publicLinkPage.addPassword(itemName, rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        publicLinkPage.backToLinksList();
    }

    @When("^user deletes the link on (.+)$")
    public void user_deletes_link(String item) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        publicLinkPage.openPublicLink(item + " link");
        publicLinkPage.deleteLink();
    }

    @Then("^link should be created on (.+) with the following fields$")
    public void link(String itemName, DataTable table)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        //Asserts in UI
        String linkName = "";
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "name": {
                    linkName = rows.get(1);
                    assertTrue(publicLinkPage.isItemInListLinks(rows.get(1)));
                    break;
                }
                case "password": {
                    publicLinkPage.openPublicLink(linkName);
                    assertTrue(publicLinkPage.isPasswordEnabled(itemName));
                    break;
                }
                case "permission": {
                    Log.log(Level.FINE, "checking permissions");
                    publicLinkPage.openPublicLink(linkName);
                    assertTrue(publicLinkPage.checkPermissions(rows.get(1)));
                    break;
                }
                case "expiration days": {
                    publicLinkPage.openPublicLink(linkName);
                    assertTrue(publicLinkPage.checkExpiration(rows.get(1)));
                    break;
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = shareAPI.getShare(itemName);
        assertTrue(sharePage.checkCorrectShare(share, listItems));
        shareAPI.removeShare(share.getId());
    }

    @Then("^link on (.+) should not exist anymore$")
    public void link_not_existing(String itemName)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertFalse(publicLinkPage.isItemInListLinks(itemName+ " link"));
        assertTrue(shareAPI.getShare(itemName) == null);
    }
}
