package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import utils.api.FilesAPI;
import utils.api.ShareAPI;
import utils.entities.OCShare;
import utils.log.Log;

public class SharesSteps {

    //Involved pages
    protected SharePage sharePage = new SharePage();
    protected SharePermissionsPage sharePermissionsPage = new SharePermissionsPage();
    protected PrivateSharePage privateSharePage = new PrivateSharePage();

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();
    protected FilesAPI filesAPI = new FilesAPI();

    @ParameterType("user|group")
    public String usertype(String type){
        return type;
    }

    @Given("{word} has shared {itemtype} {word} with {word} with permissions {word}")
    public void item_already_shared(String sharingUser, String type, String itemName,
                                    String recipientUser, String permissions) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        shareAPI.createShare(sharingUser, itemName, recipientUser, "0", permissions, "");
    }

    @When("Alice selects {usertype} {word} as sharee with default permissions")
    public void select_sharee_default(String type, String sharee) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        privateSharePage.searchSharee(sharee, type);
        sharePermissionsPage.savePermissions();
    }

    @When("Alice selects {usertype} {word} as sharee without {word} permission")
    public void select_sharee_permissions(String type, String sharee, String permission) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        privateSharePage.searchSharee(sharee, type);
        switch (permission){
            case "share": {
                sharePermissionsPage.switchShare();
                break;
            }
            case "change": {
                sharePermissionsPage.switchChange();
                break;
            }
            case "create": {
                sharePermissionsPage.switchCreate();
                break;
            }
            case "delete": {
                sharePermissionsPage.switchDelete();
                break;
            }
            default:
                break;
        }

        sharePermissionsPage.savePermissions();
    }

    @When("Alice edits the share on {word} with permissions {word}")
    public void user_edits_share(String itemName, String permissions)
            throws Throwable{
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        privateSharePage.openPrivateShare(LocProperties.getProperties().getProperty("userToShare"));
        int permissionsToInt = Integer.parseInt(permissions);
        String permissionsToString =String.format("%5s", Integer.toBinaryString(permissionsToInt))
                .replace(" ", "0");
        Log.log(Level.FINE, "Permissions converted: " + permissionsToString);
        for (int i=0 ; i<=permissionsToString.length()-1 ; i++){
            switch(i) {
                case(0):{
                    Log.log(Level.FINE, "Check Share");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = sharePermissionsPage.isShareEnabled();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        sharePermissionsPage.switchShare();
                    break;
                }
                case(1):{
                    Log.log(Level.FINE, "Check Delete");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = sharePermissionsPage.isDeleteSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        sharePermissionsPage.switchDelete();
                    break;
                }
                case(2):{
                    Log.log(Level.FINE, "Check Create");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = sharePermissionsPage.isCreateSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        sharePermissionsPage.switchCreate();
                    break;
                }
                case(3):{
                    Log.log(Level.FINE, "Check Change");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = sharePermissionsPage.isChangeSelected();
                    Log.log(Level.FINE, "Status: " + status +". Enabled: "+ enabled);
                    if (enabled != (status=='1'))
                        sharePermissionsPage.switchChange();
                    break;
                }
                default:
                    break;
            }
        }
        privateSharePage.close();
    }

    @When("Alice deletes the share")
    public void user_deletes_share() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        privateSharePage.deletePrivateShare(LocProperties.getProperties().getProperty("userToShare"));
    }

    @Then("share should be created on {word} with the following fields")
    public void share_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        String groupName = null;
        String permissionString = "Read, Share, Create, Change, Delete";
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            Log.log(Level.FINE, rows.get(0));
            switch (rows.get(0)) {
                case "password": {
                    privateSharePage.openPrivateShare(itemName);
                    assertTrue(privateSharePage.isPasswordEnabled());
                    privateSharePage.close();
                    break;
                }
                case "sharee": {
                    Log.log(Level.FINE, "Checking sharee");
                    assertTrue(privateSharePage.isItemInListPrivateShares(rows.get(1)));
                    break;
                }
                case "group": {
                    assertTrue(privateSharePage.isItemInListPrivateShares(rows.get(1)));
                    groupName = rows.get(1);
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
                    assertTrue(privateSharePage.displayedPermission(permissionString));
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = shareAPI.getShare(itemName);
        assertTrue(sharePage.checkCorrectShare(share, listItems));
    }

    @Then("{usertype} {word} should have access to {word}")
    public void group_has_the_file(String type, String shareeName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (type.equals("user")){
            assertTrue(shareAPI.isSharedWithMe(itemName, shareeName, false));
        } else if (type.equals("group")){
            assertTrue(shareAPI.isSharedWithMe(itemName, shareeName, true));
        }
    }

    @Then("user {word} should not have access to {word}")
    public void sharee_does_not_have_access(String userName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(shareAPI.isSharedWithMe(itemName, userName,false));
    }

    @Then("{word} should not be shared anymore with {word}")
    public void share_is_deleted(String itemName, String sharee)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(privateSharePage.isItemInListPrivateShares(sharee));
    }
}
