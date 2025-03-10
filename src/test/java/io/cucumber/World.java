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

    /*public LoginPage loginPage {
        if (loginPage == null)
            loginPage = new LoginPage();
        return loginPage;
    }

    public FileListPage fileListPage {
        if (fileListPage == null)
            fileListPage = new FileListPage();
        return fileListPage;
    }

    public InputNamePage inputNamePage {
        if (inputNamePage == null)
            inputNamePage = new InputNamePage();
        return inputNamePage;
    }

    public FolderPickerPage folderPickerPage {
        if (folderPickerPage == null)
            folderPickerPage = new FolderPickerPage();
        return folderPickerPage;
    }

    public PrivateSharePage privateSharePage {
        if (privateSharePage == null)
            privateSharePage = new PrivateSharePage();
        return privateSharePage;
    }

    public PublicLinkPage publicLinkPage {
        if (publicLinkPage == null)
            publicLinkPage = new PublicLinkPage();
        return publicLinkPage;
    }

    public SharePage sharePage {
        if (sharePage == null)
            sharePage = new SharePage();
        return sharePage;
    }

    public SpacesPage spacesPage {
        if (spacesPage == null)
            spacesPage = new SpacesPage();
        return spacesPage;
    }

    public UploadsPage uploadsPage {
        if (uploadsPage == null)
            uploadsPage = new UploadsPage();
        return uploadsPage;
    }

    public ShortcutPage shortcutPage {
        if (shortcutPage == null)
            shortcutPage = new ShortcutPage();
        return shortcutPage;
    }

    public PreviewPage previewPage {
        if (previewPage == null)
            previewPage = new PreviewPage();
        return previewPage;
    }

    public AuthAPI getAuthAPI() {
        if (authAPI == null)
            authAPI = new AuthAPI();
        return authAPI;
    }

    public ShareAPI shareAPI
            throws IOException {
        if (shareAPI == null)
            shareAPI = new ShareAPI();
        return shareAPI;
    }

    public FilesAPI filesAPI
            throws IOException {
        if (filesAPI == null)
            filesAPI = new FilesAPI();
        return filesAPI;
    }

    public GraphAPI graphAPI
            throws IOException {
        if (graphAPI == null)
            graphAPI = new GraphAPI();
        return graphAPI;
    }

    public TrashbinAPI getTrashbinAPI()
            throws IOException {
        if (trashbinAPI == null)
            trashbinAPI = new TrashbinAPI();
        return trashbinAPI;
    }*/
}
