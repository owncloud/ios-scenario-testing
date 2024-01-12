package io.cucumber;

import ios.FileListPage;
import ios.FolderPickerPage;
import ios.InputNamePage;
import ios.LoginPage;

import java.io.IOException;

import ios.PrivateSharePage;
import ios.PublicLinkPage;
import ios.SharePage;
import ios.SpacesPage;
import ios.UploadsPage;
import utils.api.AuthAPI;
import utils.api.FilesAPI;
import utils.api.GraphAPI;
import utils.api.ShareAPI;
import utils.api.TrashbinAPI;

public class World {

    //Involved pages
    private LoginPage loginPage;
    private FileListPage fileListPage;
    private InputNamePage inputNamePage;
    private FolderPickerPage folderPickerPage;
    private PrivateSharePage privateSharePage;
    private PublicLinkPage publicLinkPage;
    private SharePage sharePage;
    private SpacesPage spacesPage;
    private UploadsPage uploadsPage;

    //APIs to call
    private AuthAPI authAPI;
    private ShareAPI shareAPI;
    private FilesAPI filesAPI;
    private GraphAPI graphAPI;
    private TrashbinAPI trashbinAPI;

    public World() throws IOException {
    }

    public LoginPage getLoginPage(){
        if (loginPage == null)
            loginPage = new LoginPage();
        return loginPage;
    }

    public FileListPage getFileListPage(){
        if (fileListPage == null)
            fileListPage = new FileListPage();
        return fileListPage;
    }

    public InputNamePage getInputNamePage(){
        if (inputNamePage == null)
            inputNamePage = new InputNamePage();
        return inputNamePage;
    }

    public FolderPickerPage getFolderPickerPage(){
        if (folderPickerPage == null)
            folderPickerPage = new FolderPickerPage();
        return folderPickerPage;
    }

    public PrivateSharePage getPrivateSharePage(){
        if (privateSharePage == null)
            privateSharePage = new PrivateSharePage();
        return privateSharePage;
    }

    public PublicLinkPage getPublicLinkPage(){
        if (publicLinkPage == null)
            publicLinkPage = new PublicLinkPage();
        return publicLinkPage;
    }

    public SharePage getSharePage(){
        if (sharePage == null)
            sharePage = new SharePage();
        return sharePage;
    }

    public SpacesPage getSpacesPage(){
        if (spacesPage == null)
            spacesPage = new SpacesPage();
        return spacesPage;
    }

    public UploadsPage getUploadsPage(){
        if (uploadsPage == null)
            uploadsPage = new UploadsPage();
        return uploadsPage;
    }

    public AuthAPI getAuthAPI(){
        if (authAPI == null)
            authAPI = new AuthAPI();
        return authAPI;
    }

    public ShareAPI getShareAPI()
            throws IOException{
        if (shareAPI == null)
            shareAPI = new ShareAPI();
        return shareAPI;
    }

    public FilesAPI getFilesAPI()
            throws IOException{
        if (filesAPI == null)
            filesAPI = new FilesAPI();
        return filesAPI;
    }

    public GraphAPI getGraphAPI()
            throws IOException{
        if (graphAPI == null)
            graphAPI = new GraphAPI();
        return graphAPI;
    }

    public TrashbinAPI getTrashbinAPI()
            throws IOException{
        if (trashbinAPI == null)
            trashbinAPI = new TrashbinAPI();
        return trashbinAPI;
    }
}
