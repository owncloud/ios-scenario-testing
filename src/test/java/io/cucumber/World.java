package io.cucumber;

import ios.FileListPage;
import ios.FolderPickerPage;
import ios.InputNamePage;
import ios.LinkPermissionsPage;
import ios.LoginPage;

import java.io.IOException;

import ios.PrivateSharePage;
import ios.PublicLinkPage;
import ios.SharePage;
import ios.SharePermissionsPage;
import utils.api.FilesAPI;
import utils.api.ShareAPI;

public class World {

    //Involved pages
    LoginPage loginPage = new LoginPage();
    FileListPage fileListPage = new FileListPage();
    InputNamePage inputNamePage = new InputNamePage();
    FolderPickerPage folderPickerPage = new FolderPickerPage();
    LinkPermissionsPage linkPermissionsPage = new LinkPermissionsPage();
    PrivateSharePage privateSharePage = new PrivateSharePage();
    PublicLinkPage publicLinkPage = new PublicLinkPage();
    SharePage sharePage = new SharePage();
    SharePermissionsPage sharePermissionsPage = new SharePermissionsPage();


    //APIs to call
    ShareAPI shareAPI = new ShareAPI();
    FilesAPI filesAPI = new FilesAPI();

    public World() throws IOException {
    }
}
