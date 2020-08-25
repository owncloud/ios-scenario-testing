package io.cucumber;

import android.PublicLinkPage;
import android.SharePage;

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

public class PublicShareSteps {

    //Involved pages
    protected SharePage sharePage = new SharePage();
    protected PublicLinkPage publicLinkPage = new PublicLinkPage();

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();

    @Given("the item (.+) is already shared by link")
    public void item_already_shared_by_link(String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + " - " + itemName);
        shareAPI.createShare(itemName, "", "3", "1", itemName + " link");
    }

    @When("^user creates link on (.+) with the following fields$")
    public void i_select_to_link_with_fields(String itemName, DataTable table)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        sharePage.addPublicLink();
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    publicLinkPage.addLinkName(rows.get(1));
                    break;
                }
                case "password": {
                    publicLinkPage.addPassword(rows.get(1));
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
    public void user_edits_public_link(String itemName, DataTable table) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
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
                    publicLinkPage.addPassword(rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        publicLinkPage.submitLink();
    }

    @When("^user deletes the link$")
    public void user_deletes_link() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        sharePage.deletePublicShare();
        sharePage.acceptDeletion();
    }

    @Then("^link is created on (.+) with the following fields$")
    public void link(String itemName, DataTable table)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        //Asserts in UI
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "name": {
                    assertTrue(sharePage.isItemInListPublicShares(rows.get(1)));
                    break;
                }
                case "password": {
                    sharePage.openPublicLink(itemName);
                    assertTrue(publicLinkPage.isPasswordEnabled());
                    publicLinkPage.close();
                    break;
                }
                case "user": {
                    assertTrue(sharePage.isItemInListPublicShares(itemName));
                    break;
                }
                case "permission": {
                    Log.log(Level.FINE, "checking permissions");
                    sharePage.openPublicLink(itemName);
                    assertTrue(publicLinkPage.checkPermissions(rows.get(1)));
                    publicLinkPage.close();
                    break;
                }
                case "expiration days": {
                    sharePage.openPublicLink(itemName);
                    assertTrue(publicLinkPage.checkExpiration(rows.get(1)));
                    publicLinkPage.close();
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

    @Then("^link on (.+) does not exist anymore$")
    public void link_not_existing(String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        assertFalse(sharePage.isItemInListPublicShares(itemName+ " link"));
        assertTrue(shareAPI.getShare(itemName) == null);
    }
}
