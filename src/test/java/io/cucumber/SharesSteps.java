package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ios.PrivateSharePage;
import ios.SharePage;
import ios.SharePermissionsPage;
import utils.LocProperties;
import utils.api.ShareAPI;
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

    @Given("{word} has {sharelevel} {itemtype} {word} with {word} with permissions {word}")
    public void item_already_shared(String sharingUser, int sharelevel, String type, String itemName,
                                    String recipientUser, String permissions) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.shareAPI.createShare(sharingUser, itemName, recipientUser, "0", permissions, "", sharelevel);
    }

    @When("Alice selects the following {usertype} as sharee with default permissions")
    public void select_sharee_default(String type,  DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listUser = table.asLists();
        String sharee = listUser.get(0).get(1);
        String email = "";
        if (listUser.size() > 1) { //in case there are more than one column... distinguish oCIS-oC10
            email = listUser.get(1).get(1);
        }
        world.privateSharePage.searchSharee(sharee, email, type);
        world.sharePermissionsPage.savePermissions();
        world.shareAPI.acceptAllShares(type,sharee);
    }

    @When("Alice selects the following {usertype} as sharee without {word} permission")
    public void select_sharee_permissions(String type, String permission, DataTable table) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listUser = table.asLists();
        String sharee = listUser.get(0).get(1);
        String email = "";
        if (listUser.size() > 1) { //in case there are more than one column... distinguish oCIS-oC10
            email = listUser.get(1).get(1);
        }
        world.privateSharePage.searchSharee(sharee, email, type);
        switch (permission){
            case "share": {
                world.sharePermissionsPage.switchShare();
                break;
            }
            case "change": {
                world.sharePermissionsPage.switchChange();
                break;
            }
            case "create": {
                world.sharePermissionsPage.switchCreate();
                break;
            }
            case "delete": {
                world.sharePermissionsPage.switchDelete();
                break;
            }
            default:
                break;
        }

        world.sharePermissionsPage.savePermissions();
    }

    @When("Alice edits the share on {word} with permissions {word}")
    public void user_edits_share(String itemName, String permissions) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.privateSharePage.openPrivateShare(LocProperties.getProperties().getProperty("userToShare"));
        int permissionsToInt = Integer.parseInt(permissions);
        String permissionsToString =String.format("%5s", Integer.toBinaryString(permissionsToInt))
                .replace(" ", "0");
        Log.log(Level.FINE, "Permissions converted: " + permissionsToString);
        for (int i=0 ; i<=permissionsToString.length()-1 ; i++){
            switch(i) {
                case(0):{
                    Log.log(Level.FINE, "Check Share");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = world.sharePermissionsPage.isShareEnabled();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        world.sharePermissionsPage.switchShare();
                    break;
                }
                case(1):{
                    Log.log(Level.FINE, "Check Delete");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = world.sharePermissionsPage.isDeleteSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        world.sharePermissionsPage.switchDelete();
                    break;
                }
                case(2):{
                    Log.log(Level.FINE, "Check Create");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = world.sharePermissionsPage.isCreateSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        world.sharePermissionsPage.switchCreate();
                    break;
                }
                case(3):{
                    Log.log(Level.FINE, "Check Change");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = world.sharePermissionsPage.isChangeSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        world.sharePermissionsPage.switchChange();
                    break;
                }
                default:
                    break;
            }
        }
        world.privateSharePage.close();
    }

    @When("Alice deletes the share with {word}")
    public void user_deletes_share(String sharee) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.privateSharePage.deletePrivateShare(sharee);
    }

    @Then("share should be created on {word} with the following fields")
    public void share_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        String groupName = null;
        String permissionString = "Read, Share, Create, Change, Delete";
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "password": {
                    world.privateSharePage.openPrivateShare(itemName);
                    assertTrue(world.privateSharePage.isPasswordEnabled());
                    world.privateSharePage.close();
                    break;
                }
                case "sharee": {
                    assertTrue(world.privateSharePage.isItemInListPrivateShares(rows.get(1)));
                    break;
                }
                case "group": {
                    assertTrue(world.privateSharePage.isItemInListPrivateShares(rows.get(1)));
                    break;
                }
                case "permissions": {
                    int permissionsToInt = Integer.parseInt(rows.get(1));
                    String permissionsToString = String.format("%5s", Integer.toBinaryString(permissionsToInt))
                            .replace(" ", "0");
                    Log.log(Level.FINE, "Permissions converted: " + permissionsToString);
                    for (int i = 0; i <= permissionsToString.length() - 1; i++) {
                        switch (i) {
                            case (0): {
                                if (permissionsToString.charAt(i) == '0') {
                                    Log.log(Level.FINE, "Share removed");
                                    permissionString = permissionString.replace(", Share", "");
                                }
                                break;
                            }
                            case (1): {
                                if (permissionsToString.charAt(i) == '0') {
                                    Log.log(Level.FINE, "Delete removed");
                                    permissionString = permissionString.replaceAll(", Delete", "");
                                }
                                break;
                            }
                            case (2): {
                                if (permissionsToString.charAt(i) == '0') {
                                    Log.log(Level.FINE, "Create removed");
                                    permissionString = permissionString.replaceAll(", Create", "");
                                }
                                break;
                            }
                            case (3): {
                                if (permissionsToString.charAt(i) == '0') {
                                    Log.log(Level.FINE, "Change removed");
                                    permissionString = permissionString.replaceAll(", Change", "");
                                }
                                break;
                            }
                        }
                    }
                    Log.log(Level.FINE, "String permissions: " + permissionString);
                    assertTrue(world.privateSharePage.displayedPermission(permissionString));
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = world.shareAPI.getShare(itemName);
        assertTrue(world.sharePage.checkCorrectShare(share, listItems));
    }

    @Then("{usertype} {word} should have access to {word}")
    public void group_has_the_file(String type, String shareeName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (type.equals("user")){
            assertTrue(world.shareAPI.isSharedWithMe(itemName, shareeName, false));
        } else if (type.equals("group")){
            assertTrue(world.shareAPI.isSharedWithMe(itemName, shareeName, true));
        }
    }

    @Then("user {word} should not have access to {word}")
    public void sharee_does_not_have_access(String userName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.shareAPI.isSharedWithMe(itemName, userName,false));
    }

    @Then("{word} should not be shared anymore with {word}")
    public void share_is_deleted(String itemName, String sharee) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.privateSharePage.isItemInListPrivateShares(sharee));
    }
}
