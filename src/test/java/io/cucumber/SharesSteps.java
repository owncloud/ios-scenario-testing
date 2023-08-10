package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCShare;
import utils.log.Log;

public class SharesSteps {

    private World world;

    public SharesSteps(World world) {
        this.world = world;
    }

    @ParameterType("user|group")
    public String usertype(String type){
        return type;
    }

    @ParameterType("shared|reshared")
    public int sharelevel(String type){
        if (type.equals("shared")) {
            return 0; //share, first level
        } else {
            return 1; //reshare
        }
    }

    @Given("{word} has {sharelevel} {itemtype} {word} with {usertype} {word} with {word} permissions")
    public void item_already_shared(String sharingUser, int sharelevel, String type, String itemName,
                                    String userType, String recipientUser, String permissions) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getShareAPI().createShare(sharingUser, itemName, recipientUser, "0",
                world.getSharePage().translatePermissionsToInt(permissions), "", sharelevel);
        //Sharee is not detected at the moment.
        Thread.sleep(2000);
        world.getShareAPI().acceptAllShares(userType, recipientUser);
    }

    @When("Alice selects the following {usertype} as sharee with {word} permissions")
    public void select_sharee_default(String type, String permissions, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listUser = table.asLists();
        String sharee = listUser.get(0).get(1);
        world.getSharePage().invite();
        world.getPrivateSharePage().searchSharee(sharee, type);
        world.getPrivateSharePage().setPermissions(permissions);
        world.getPrivateSharePage().savePermissions();
        //Sharee is not detected at the moment.
        Thread.sleep(2000);
        world.getShareAPI().acceptAllShares(type,sharee);
    }

    @When("Alice selects the following {usertype} as sharee without {word} permission")
    public void select_sharee_permissions(String type, String permission, DataTable table)
            throws InterruptedException, IOException, ParserConfigurationException, SAXException {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listUser = table.asLists();
        String sharee = listUser.get(0).get(1);
        world.getSharePage().invite();
        world.getPrivateSharePage().searchSharee(sharee, type);
        world.getPrivateSharePage().removeSharingPermission();
        world.getPrivateSharePage().invite();
        //Sharee is not detected at the moment.
        Thread.sleep(2000);
        world.getShareAPI().acceptAllShares(type,sharee);
    }

    @When("Alice edits the share with the following fields")
    public void user_edits_share(DataTable table) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> fieldList = table.asLists();
        String sharee = fieldList.get(0).get(1);
        String permissions = fieldList.get(1).get(1);
        world.getSharePage().openPrivateShare(sharee);
        world.getPrivateSharePage().setPermissions(permissions);
        world.getPrivateSharePage().saveChanges();
    }

    @When("Alice deletes the share with")
    public void user_deletes_share(DataTable table)
            throws InterruptedException {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        world.getSharePage().openPrivateShare(sharee);
        world.getPrivateSharePage().deletePrivateShare();
        //Need to wait a little bit till share is deleted. Improvable.
        Thread.sleep(2000);
    }

    @Then("share should be created on {word} with the following fields")
    public void share_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "sharee":
                case "group": {
                    Log.log(Level.FINE, "Checking sharee/group: " + rows.get(1));
                    assertTrue(world.getSharePage().isItemInListPrivateShares(rows.get(1)));
                    break;
                }
                case "permissions": {
                    Log.log(Level.FINE, "Checking permissions: " + rows.get(1));
                    assertTrue(world.getSharePage().displayedPermission(rows.get(1)));
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = world.getShareAPI().getShare(itemName);
        assertTrue(world.getSharePage().checkCorrectShare(share, listItems));
    }

    @Then("{usertype} {word} should have access to {word}")
    public void user_has_acces_to_the_item(String type, String shareeName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (type.equals("user")){
            assertTrue(world.getShareAPI().isSharedWithMe(itemName, shareeName, false));
        } else if (type.equals("group")){
            assertTrue(world.getShareAPI().isSharedWithMe(itemName, shareeName, true));
        }
    }

    @Then("user {word} should not have access to {word}")
    public void sharee_does_not_have_access(String userName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getShareAPI().isSharedWithMe(itemName, userName,false));
    }

    @Then("{word} should not be shared anymore with")
    public void share_is_deleted(String itemName, DataTable table) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> list = table.asLists();
        String sharee = list.get(0).get(0);
        assertFalse(world.getSharePage().isItemInListPrivateShares(sharee));
    }
}
