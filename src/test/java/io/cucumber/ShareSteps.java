package io.cucumber;

import android.FileListPage;
import android.PrivateSharePage;
import android.PublicLinkPage;
import android.SearchShareePage;
import android.SharePage;

import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.LocProperties;
import utils.api.ShareAPI;
import utils.entities.OCShare;
import utils.log.Log;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShareSteps {

    //Involved pages
    protected SharePage sharePage = new SharePage();
    protected FileListPage fileListPage = new FileListPage();
    protected SearchShareePage searchShareePage = new SearchShareePage();
    protected PublicLinkPage publicLinkPage = new PublicLinkPage();
    protected PrivateSharePage privateSharePage = new PrivateSharePage();

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();

    @Given("the item (.+) is already shared with (.+)")
    public void item_already_shared(String itemName, String sharee)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + " - " + itemName
                + " - " + sharee);
        shareAPI.createShare(itemName, sharee, "0", "31", "");
    }

    @Given("the item (.+) is already shared by link")
    public void item_already_shared_by_link(String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + " - " + itemName);
        shareAPI.createShare(itemName, "", "3", "1", itemName + " link");
    }

    @When("^user selects (.+) to share with (.+)$")
    public void i_select_to_share_with(String itemName, String sharee)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        fileListPage.executeOperation("Share", itemName);
        sharePage.addPrivateShare();
        searchShareePage.shareWithUser(sharee);
    }

    @When("^user selects (.+) to create link with the following fields$")
    public void i_select_to_link_with_fields(String itemName, DataTable table)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        fileListPage.executeOperation("Share", itemName);
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
                default:
                    break;
            }
        }
        publicLinkPage.submitLink();
    }

    @When("^user edits the share on (.+) with permissions (.+)$")
    public void user_edits_share(String itemName, String permissions) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName
                + " - " + permissions);
        sharePage.openPrivateShare(itemName);
        int permissionsToInt = Integer.parseInt(permissions);
        String permissionsToString = String.format("%05d", permissionsToInt);
        Log.log(Level.FINE, "Permissions converted: " + permissionsToString);
        for (int i=0 ; i<=permissionsToString.length()-1 ; i++){
            switch(i){
                case(1):{
                    Log.log(Level.FINE, "Check Change");
                    char status = permissionsToString.charAt(2);
                    boolean enabled = privateSharePage.isCreateSelected();
                    Log.log(Level.FINE, "Status: " + status+". Enabled: "+ enabled);
                    if (enabled != (status==1))
                        privateSharePage.switchChange();
                    break;
                }
                case(2):{
                    Log.log(Level.FINE, "Check Create");
                    char status = permissionsToString.charAt(2);
                    boolean enabled = privateSharePage.isChangeSelected();
                    Log.log(Level.FINE, "Status: " + status+". Enabled: "+ enabled);
                    if (enabled != (status==1))
                        privateSharePage.switchCreate();
                    break;
                }
                case(3):{
                    Log.log(Level.FINE, "Check Delete");
                    char status = permissionsToString.charAt(2);
                    boolean enabled = privateSharePage.isDeleteSelected();
                    Log.log(Level.FINE, "Status: " + status+". Enabled: "+ enabled);
                    if (enabled != (status==1))
                        privateSharePage.switchDelete();
                    break;
                }
                case(4):{
                    Log.log(Level.FINE, "Check Share");
                    char status = permissionsToString.charAt(2);
                    boolean enabled = privateSharePage.isShareEnabled();
                    Log.log(Level.FINE, "Status: " + status+". Enabled: "+ enabled);
                    if (enabled != (status==1))
                        privateSharePage.switchShare();
                    break;
                }
                default:
                    break;
            }
        }
        privateSharePage.close();
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

    @When("^user deletes the share$")
    public void user_deletes_share() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        sharePage.deletePrivateShare();
        sharePage.acceptDeletion();
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
                    assertTrue(sharePage.isItemInList(rows.get(1)));
                    break;
                }
                case "password": {
                    sharePage.openPublicLink(itemName);
                    assertTrue(publicLinkPage.isPasswordEnabled());
                    publicLinkPage.close();
                    break;
                }
                case "user": {
                    assertTrue(sharePage.isItemInList(itemName));
                    break;
                }
                case "permissions": {
                    sharePage.openPublicLink(itemName);
                    assertTrue(publicLinkPage.checkPermissions(rows.get(1)));
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

    @Then("^share is created on (.+) with the following fields$")
    public void share_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        //Asserts in UI
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "password": {
                    sharePage.openPrivateShare(itemName);
                    assertTrue(privateSharePage.isPasswordEnabled());
                    privateSharePage.close();
                    break;
                }
                case "user": {
                    assertTrue(sharePage.isItemInList(itemName));
                    break;
                }
                case "permissions": {
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

    @Then("^(.+) has access to (.+)")
    public void sharee_has_the_file (String userName, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
            new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        assertTrue(shareAPI.isSharedWithMe(itemName));
    }

    @Then("^(.+) does not have access to (.+)")
    public void sharee_does_not_have_the_file (String userName, String itemName)
            throws Throwable {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName() + ": " + itemName);
        assertFalse(shareAPI.isSharedWithMe(itemName));
    }

    @Then("^(.+) is not shared anymore with (.+)$")
    public void share_is_deleted(String itemName, String username) {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        assertFalse(sharePage.isItemInList(username));
    }

    @Then("^link on (.+) does not exist anymore$")
    public void link_not_existing(String itemName)
            throws Throwable {
        share_is_deleted(itemName, LocProperties.getProperties().getProperty("userName1"));
        assertFalse(sharePage.isItemInList(itemName));
        assertTrue(shareAPI.getShare(itemName) == null);
    }
}
