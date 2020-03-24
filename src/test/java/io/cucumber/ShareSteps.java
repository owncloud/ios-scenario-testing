package io.cucumber;

import android.FileListPage;
import android.PublicLinkPage;
import android.SearchShareePage;
import android.SharePage;

import java.util.List;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.ShareAPI;
import utils.entities.OCShare;

import static org.junit.Assert.assertTrue;

public class ShareSteps {

    //Involved pages
    protected SharePage sharePage = new SharePage();
    protected FileListPage fileListPage = new FileListPage();
    protected SearchShareePage searchShareePage = new SearchShareePage();
    protected PublicLinkPage publicLinkPage = new PublicLinkPage();

    //APIs to call
    protected ShareAPI shareAPI = new ShareAPI();

    @When("^user selects (.+) to share with (.+)$")
    public void i_select_to_share_with(String itemName, String sharee)
            throws Throwable {
        fileListPage.executeOperation("Share", itemName);
        sharePage.addPrivateShare();
        searchShareePage.shareWithUser(sharee);
    }

    @When("^user selects (.+) to create link with the following fields$")
    public void i_select_to_link_with_fields(String itemName, DataTable table)
            throws Throwable {
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

    @Then("^(.*) is created on (.+) with the following fields$")
    public void public_link_created(String type, String itemName, DataTable table)
            throws Throwable {
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
}
