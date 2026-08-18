package e2e.world;

import java.io.IOException;

import e2e.api.FilesAPI;
import e2e.api.GraphAPI;
import e2e.api.ShareAPI;
import e2e.api.TrashbinAPI;
import e2e.assertions.FileListAssertions;
import e2e.assertions.PrivateShareAssertions;
import e2e.assertions.PublicLinkAssertions;
import e2e.pages.AppiumManager;
import e2e.pages.FileListPage;
import e2e.pages.FolderPickerPage;
import e2e.pages.InputNamePage;
import e2e.pages.LoginPage;
import e2e.pages.PreviewPage;
import e2e.pages.PrivateSharePage;
import e2e.pages.PublicLinkPage;
import e2e.pages.SharePage;
import e2e.pages.ShortcutPage;
import e2e.pages.SpaceMembersPage;
import e2e.pages.SpacesPage;
import e2e.pages.UploadsPage;
import e2e.preconditions.FileListPreconditions;
import e2e.preconditions.LoginPreconditions;
import e2e.preconditions.PublicLinkPreconditions;
import e2e.preconditions.SharesPreconditions;
import e2e.tasks.FileListTasks;
import e2e.tasks.PrivateShareTasks;
import e2e.tasks.PublicLinkTasks;
import io.appium.java_client.ios.IOSDriver;

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

    private LoginPreconditions loginPreconditions;

    private FileListPreconditions fileListPreconditions;
    private FileListTasks fileListTasks;
    private FileListAssertions fileListAssertions;

    private PublicLinkPreconditions publicLinkPreconditions;
    private PublicLinkTasks publicLinkTasks;
    private PublicLinkAssertions publicLinkAssertions;

    private SharesPreconditions sharesPreconditions;
    private PrivateShareTasks privateShareTasks;
    private PrivateShareAssertions privateShareAssertions;

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

    public LoginPreconditions loginPreconditions() {
        if (loginPreconditions == null) {
            loginPreconditions = new LoginPreconditions(this);
        }
        return loginPreconditions;
    }

    public FileListPreconditions fileListPreconditions() {
        if (fileListPreconditions == null) {
            fileListPreconditions = new FileListPreconditions(this);
        }
        return fileListPreconditions;
    }

    public FileListTasks fileListTasks() {
        if (fileListTasks == null) {
            fileListTasks = new FileListTasks(this);
        }
        return fileListTasks;
    }

    public FileListAssertions fileListAssertions() {
        if (fileListAssertions == null) {
            fileListAssertions = new FileListAssertions(this);
        }
        return fileListAssertions;
    }

    public PublicLinkPreconditions publicLinkPreconditions() {
        if (publicLinkPreconditions == null) {
            publicLinkPreconditions = new PublicLinkPreconditions(this);
        }
        return publicLinkPreconditions;
    }

    public PublicLinkTasks publicLinkTasks() {
        if (publicLinkTasks == null) {
            publicLinkTasks = new PublicLinkTasks(this);
        }
        return publicLinkTasks;
    }

    public PublicLinkAssertions publicLinkAssertions() {
        if (publicLinkAssertions == null) {
            publicLinkAssertions = new PublicLinkAssertions(this);
        }
        return publicLinkAssertions;
    }

    public SharesPreconditions sharesPreconditions() {
        if (sharesPreconditions == null) {
            sharesPreconditions = new SharesPreconditions(this);
        }
        return sharesPreconditions;
    }

    public PrivateShareTasks privateShareTasks() {
        if (privateShareTasks == null) {
            privateShareTasks = new PrivateShareTasks(this);
        }
        return privateShareTasks;
    }

    public PrivateShareAssertions privateShareAssertions() {
        if (privateShareAssertions == null) {
            privateShareAssertions = new PrivateShareAssertions(this);
        }
        return privateShareAssertions;
    }
}
