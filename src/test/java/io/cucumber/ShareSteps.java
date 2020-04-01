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
import utils.api.ShareAPI;
import utils.entities.OCShare;
import utils.log.Log;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
        shareAPI.createShare(itemName, sharee);
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
        sharePage.editPrivateShare(itemName);
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
        privateSharePage.closeView();
    }

    @When("^user deletes the share$")
    public void user_deletes_share() {
        Log.log(Level.FINE, "----STEP----: " +
                new Object(){}.getClass().getEnclosingMethod().getName());
        sharePage.deletePrivateShare();
        privateSharePage.acceptDeletion();
    }

    @Then("^(.*) is created on (.+) with the following fields$")
    public void share_created_with_fields(String type, String itemName, DataTable table)
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
                    sharePage.openLink();
                    assertTrue(publicLinkPage.isPasswordEnabled());
                    publicLinkPage.close();
                    break;
                }
                case "user": {
                    assertTrue(sharePage.isItemInList(itemName));
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
        assertTrue(!sharePage.isItemInList(username));
    }
}
