package io.cucumber;

import java.io.IOException;

import ios.FileListPage;
import ios.FolderPickerPage;
import ios.InputNamePage;
import ios.LoginPage;
import ios.PreviewPage;
import ios.PrivateSharePage;
import ios.PublicLinkPage;
import ios.SharePage;
import ios.ShortcutPage;
import ios.SpacesPage;
import ios.UploadsPage;
import utils.api.AuthAPI;
import utils.api.FilesAPI;
import utils.api.GraphAPI;
import utils.api.ShareAPI;
import utils.api.TrashbinAPI;

public class World {

    //Involved pages
    public LoginPage loginPage = LoginPage.getInstance();
    public FileListPage fileListPage = FileListPage.getInstance();
    public InputNamePage inputNamePage= InputNamePage.getInstance();
    public FolderPickerPage folderPickerPage= FolderPickerPage.getInstance();
    public PrivateSharePage privateSharePage = PrivateSharePage.getInstance();
    public PublicLinkPage publicLinkPage = PublicLinkPage.getInstance();
    public SharePage sharePage = SharePage.getInstance();
    public SpacesPage spacesPage = SpacesPage.getInstance();
    public UploadsPage uploadsPage = UploadsPage.getInstance();
    public ShortcutPage shortcutPage = ShortcutPage.getInstance();
    public PreviewPage previewPage = PreviewPage.getInstance();

    //APIs to call
    public AuthAPI authAPI = AuthAPI.getInstance();
    public ShareAPI shareAPI = ShareAPI.getInstance();
    public FilesAPI filesAPI = FilesAPI.getInstance();
    public GraphAPI graphAPI = GraphAPI.getInstance();
    public TrashbinAPI trashbinAPI = TrashbinAPI.getInstance();

    public World() throws IOException {
    }
}
