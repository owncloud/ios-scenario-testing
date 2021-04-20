package io.cucumber;

import android.FileListPage;
import android.PrivateSharePage;
import android.SharePage;
import android.SharePermissionsPage;

import net.thucydides.core.steps.StepEventBus;

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

public class PrivateShareSteps {

    //Involved pages
    protected SharePage sharePage = new SharePage();
    protected SharePermissionsPage sharePermissionsPage = new SharePermissionsPage();
    protected PrivateSharePage privateSharePage = new PrivateSharePage();

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();

    @Given("^the item (.+) has been already shared with (.+)$")
    public void item_already_shared(String itemName, String sharee)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        shareAPI.createShare(itemName, sharee, "0", "31", "");
    }

    @When("^user selects (user|group) (.+) as sharee$")
    public void i_select_sharee(String type, String sharee)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        privateSharePage.searchSharee(sharee, type);
        sharePermissionsPage.savePermissions();
    }

    /*@When("^user selects (.+) to share with (.+)$")
    public void i_select_to_share_with(String itemName, String sharee)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.executeOperation("share", itemName);
        sharePage.addPrivateShare();
        //searchShareePage.shareWithUser(sharee);
    }*/

    @When("^user edits the share on (.+) with permissions (.+)$")
    public void user_edits_share(String itemName, String permissions)
            throws Throwable{
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        sharePage.openPrivateShare(itemName);
        int permissionsToInt = Integer.parseInt(permissions);
        String permissionsToString =String.format("%5s", Integer.toBinaryString(permissionsToInt))
                .replace(" ", "0");
        Log.log(Level.FINE, "Permissions converted: " + permissionsToString);
        for (int i=0 ; i<=permissionsToString.length()-1 ; i++){
            switch(i) {
                case(0):{
                    Log.log(Level.FINE, "Check Share");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = privateSharePage.isShareEnabled();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        privateSharePage.switchShare();
                    break;
                }
                case(1):{
                    Log.log(Level.FINE, "Check Delete");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = privateSharePage.isDeleteSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        privateSharePage.switchDelete();
                    break;
                }
                case(2):{
                    Log.log(Level.FINE, "Check Create");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = privateSharePage.isCreateSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        privateSharePage.switchCreate();
                    break;
                }
                case(3):{
                    Log.log(Level.FINE, "Check Change");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = privateSharePage.isChangeSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        privateSharePage.switchChange();
                    break;
                }
                default:
                    break;
            }
        }
        privateSharePage.close();
        //An implicit wait to be used till a close button is available. To improve.
        Thread.sleep(2000);
    }

    @When("^user deletes the share$")
    public void user_deletes_share() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        privateSharePage.deletePrivateShare(LocProperties.getProperties().getProperty("userToShare"));
    }

    @Then("^share should be created on (.+) with the following fields$")
    public void share_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        //Asserts in UI
        String groupName = null;
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "password": {
                    sharePage.openPrivateShare(itemName);
                    assertTrue(privateSharePage.isPasswordEnabled());
                    privateSharePage.close();
                    break;
                }
                case "sharee": {
                    assertTrue(privateSharePage.isItemInListPrivateShares(rows.get(1)));
                    break;
                }
                case "group": {
                    assertTrue(privateSharePage.isItemInListPrivateShares(rows.get(1)));
                    groupName = rows.get(1);
                    break;
                }
                case "permissions": {
                    //Not implemented yet
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

    @Then("^group including (.+) should have access to (.+)$")
    public void group_has_the_file (String userName, String itemName)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(shareAPI.isSharedWithMe(itemName, true));
    }

    @Then("^user (.+) should not have access to (.+)$")
    public void sharee_does_not_have_the_file (String userName, String itemName)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertFalse(shareAPI.isSharedWithMe(itemName, false));
    }

    @Then("^user (.+) should have access to (.+)$")
    public void sharee_has_the_file (String userName, String itemName)
            throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(shareAPI.isSharedWithMe(itemName, false));
    }

    @Then("^(.+) should not be shared anymore with (.+)$")
    public void share_is_deleted(String itemName, String sharee) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertFalse(privateSharePage.isItemInListPrivateShares(sharee));
    }
}
