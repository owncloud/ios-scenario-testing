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
import ios.LinkPermissionsPage;
import ios.PublicLinkPage;
import ios.SharePage;
import utils.api.FilesAPI;
import utils.api.ShareAPI;
import utils.entities.OCShare;
import utils.log.Log;

public class LinkSteps {

    //Involved pages
    protected SharePage sharePage = new SharePage();
    protected PublicLinkPage publicLinkPage = new PublicLinkPage();
    protected LinkPermissionsPage linkPermissionsPage = new LinkPermissionsPage();

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();

    public LinkSteps() throws IOException {
    }

    @Given("Alice has shared the {itemtype} {word} by link")
    public void item_already_shared_by_link(String type, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        shareAPI.createShare("Alice", itemName, "", "3", "1", itemName + " link", 0);
    }

    @When("Alice creates link on {itemtype} {word} with the following fields")
    public void create_link_with_fields(String type, String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        publicLinkPage.createLink(itemName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    linkPermissionsPage.addLinkName(rows.get(1));
                    break;
                }
                case "password": {
                    linkPermissionsPage.addPassword(itemName, rows.get(1));
                    break;
                }
                case "permission": {
                    linkPermissionsPage.setPermission(rows.get(1));
                    break;
                }
                case "expiration days": {
                    linkPermissionsPage.setExpiration(type, rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        linkPermissionsPage.submitLink();
    }

    @When("Alice edits the link on {word} with the following fields")
    public void edit_public_link(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        publicLinkPage.openPublicLink(itemName + " link");
        for (List<String> rows : listItems) {
            switch (rows.get(0)){
                case "name": {
                    linkPermissionsPage.addLinkName(rows.get(1));
                    break;
                }
                case "permissions": {
                    Log.log(Level.FINE, "Set permission: " + rows.get(1));
                    linkPermissionsPage.setPermission(rows.get(1));
                    break;
                }
                case "password": {
                    linkPermissionsPage.addPassword(itemName, rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        linkPermissionsPage.backToLinksList();
    }

    @When("Alice deletes the link on {word}")
    public void delete_link(String item) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        publicLinkPage.openPublicLink(item + " link");
        linkPermissionsPage.deleteLink();
    }

    @When("Alice closes Public Links")
    public void close_public_links() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        publicLinkPage.close();
    }

    @Then("link should be created on {word} with the following fields")
    public void link_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
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
                    assertTrue(linkPermissionsPage.isPasswordEnabled(itemName));
                    break;
                }
                case "permission": {
                    Log.log(Level.FINE, "checking permissions: " + rows.get(1));
                    publicLinkPage.openPublicLink(linkName);
                    assertTrue(linkPermissionsPage.checkPermissions(rows.get(1)));
                    break;
                }
                case "expiration days": {
                    publicLinkPage.openPublicLink(linkName);
                    assertTrue(linkPermissionsPage.checkExpiration(rows.get(1)));
                    break;
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = shareAPI.getShare(itemName);
        assertTrue(sharePage.checkCorrectShare(share, listItems));
    }

    @Then("link on {word} should not exist anymore")
    public void link_not_existing(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(publicLinkPage.isItemInListLinks(itemName + " link"));
        assertTrue(shareAPI.getSharesByUser("").isEmpty());
    }
}
