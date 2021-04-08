package io.cucumber;

import android.PublicLinkPage;
import android.SharePage;

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
    protected SharePage sharePage = new SharePage();
    protected PublicLinkPage publicLinkPage = new PublicLinkPage();

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
        //if password is enforced, we must force to input
        /*if (publicLinkPage.isPasswordEnforced(itemName)){
            //Enter a fake password to fit the scenario
            publicLinkPage.addPassword(itemName,"a");
        }*/
        publicLinkPage.submitLink();
    }

    @When("^user edits the link on (.+) with the following fields$")
    public void user_edits_public_link(String itemName, DataTable table)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        List<List<String>> listItems = table.asLists();
        sharePage.openPublicLink(itemName);
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    publicLinkPage.addLinkName(rows.get(1));
                    break;
                }
                case "permissions": {
                    switch (rows.get(1)) {
                        case ("1"): { //Download / View
                            Log.log(Level.FINE, "Select Download / View");
                            publicLinkPage.selectDownloadView();
                            break;
                        }
                        case ("15"): { //Download / View / Upload
                            Log.log(Level.FINE, "Select Download / View / Upload");
                            publicLinkPage.selectDownloadViewUpload();
                            break;
                        }
                        case ("4"): { //Upload Only (File Drop)
                            Log.log(Level.FINE, "Select Upload Only (File Drop)");
                            publicLinkPage.selectUploadOnly();
                            break;
                        }
                        default:
                            break;
                    }
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
        publicLinkPage.submitLink();
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
                    //publicLinkPage.close();
                    break;
                }
                case "user": {
                    assertTrue(sharePage.isItemInListPublicShares(itemName));
                    break;
                }
                case "permission": {
                    Log.log(Level.FINE, "checking permissions");
                    publicLinkPage.openPublicLink(linkName);
                    assertTrue(publicLinkPage.checkPermissions(rows.get(1)));
                    //publicLinkPage.close();
                    break;
                }
                case "expiration days": {
                    publicLinkPage.openPublicLink(linkName);
                    assertTrue(publicLinkPage.checkExpiration(rows.get(1)));
                    //publicLinkPage.close();
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
