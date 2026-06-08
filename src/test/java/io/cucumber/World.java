package io.cucumber;

import java.io.IOException;

import io.appium.java_client.ios.IOSDriver;
import ios.AppiumManager;
import ios.FileListPage;
import ios.FolderPickerPage;
import ios.InputNamePage;
import ios.LoginPage;
import ios.PreviewPage;
import ios.PrivateSharePage;
import ios.PublicLinkPage;
import ios.SharePage;
import ios.ShortcutPage;
import ios.SpaceMembersPage;
import ios.SpacesPage;
import ios.UploadsPage;
import utils.api.FilesAPI;
import utils.api.GraphAPI;
import utils.api.ShareAPI;
import utils.api.TrashbinAPI;

public class World {

    private final IOSDriver driver;
    //Involved pages
    private LoginPage loginPage;
    private FileListPage fileListPage;
    private InputNamePage inputNamePage;
    private FolderPickerPage folderPickerPage;
    private PrivateSharePage privateSharePage;
    private PublicLinkPage publicLinkPage;
    private SharePage sharePage;
    private SpacesPage spacesPage;
    private SpaceMembersPage spaceMembersPage;
    private UploadsPage uploadsPage;
    private ShortcutPage shortcutPage;
    private PreviewPage previewPage;

    //APIs to call
    private ShareAPI shareAPI;
    private FilesAPI filesAPI;
    private GraphAPI graphAPI;
    private TrashbinAPI trashbinAPI;

    public World() {
        this.driver = AppiumManager.getManager().getDriver();
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public FileListPage fileListPage() {
        if (fileListPage == null) {
            fileListPage = new FileListPage(driver);
        }
        return fileListPage;
    }

    public InputNamePage inputNamePage() {
        if (inputNamePage == null) {
            inputNamePage = new InputNamePage(driver);
        }
        return inputNamePage;
    }

    public FolderPickerPage folderPickerPage() {
        if (folderPickerPage == null) {
            folderPickerPage = new FolderPickerPage(driver);
        }
        return folderPickerPage;
    }

    public PrivateSharePage privateSharePage() {
        if (privateSharePage == null) {
            privateSharePage = new PrivateSharePage(driver);
        }
        return privateSharePage;
    }

    public PublicLinkPage publicLinkPage() {
        if (publicLinkPage == null) {
            publicLinkPage = new PublicLinkPage(driver);
        }
        return publicLinkPage;
    }

    public SharePage sharePage() {
        if (sharePage == null) {
            sharePage = new SharePage(driver);
        }
        return sharePage;
    }

    public SpacesPage spacesPage() {
        if (spacesPage == null) {
            spacesPage = new SpacesPage(driver);
        }
        return spacesPage;
    }

    public SpaceMembersPage spaceMembersPage() {
        if (spaceMembersPage == null) {
            spaceMembersPage = new SpaceMembersPage(driver);
        }
        return spaceMembersPage;
    }

    public UploadsPage uploadsPage() {
        if (uploadsPage == null) {
            uploadsPage = new UploadsPage(driver);
        }
        return uploadsPage;
    }

    public ShortcutPage shortcutPage() {
        if (shortcutPage == null) {
            shortcutPage = new ShortcutPage(driver);
        }
        return shortcutPage;
    }

    public PreviewPage previewPage() {
        if (previewPage == null) {
            previewPage = new PreviewPage(driver);
        }
        return previewPage;
    }


    public ShareAPI shareAPI() throws IOException {
        if (shareAPI == null) {
            shareAPI = new ShareAPI();
        }
        return shareAPI;
    }

    public FilesAPI filesAPI() throws IOException {
        if (filesAPI == null) {
            filesAPI = new FilesAPI();
        }
        return filesAPI;
    }

    public GraphAPI graphAPI() throws IOException {
        if (graphAPI == null) {
            graphAPI = new GraphAPI();
        }
        return graphAPI;
    }

    public TrashbinAPI trashbinAPI() throws IOException {
        if (trashbinAPI == null) {
            trashbinAPI = new TrashbinAPI();
        }
        return trashbinAPI;
    }
}
