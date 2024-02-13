package utils.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.LocProperties;
import utils.entities.OCShare;
import utils.log.Log;
import utils.parser.ShareSAXHandler;

public class ShareAPI extends CommonAPI {

    private String sharingEndpoint = "/ocs/v2.php/apps/files_sharing/api/v1/shares";
    private String pendingEndpoint = "/pending";
    private final String shareeU = LocProperties.getProperties().getProperty("userToShare");
    private AuthAPI authAPI = new AuthAPI();

    public ShareAPI() throws IOException {
        super();
    }

    public void createShare(String sharingUser, String itemPath, String sharee, String type,
                            String permissions, String name, String password, int sharelevel)
            throws IOException {
        Log.log(Level.FINE, "Starts: Create Share - " + sharingUser + " " + sharee + " "
                + itemPath + " " + type);
        String url = urlServer + sharingEndpoint;
        Log.log(Level.FINE, "URL: " + url);
        Request request = postRequest(url, createBodyShare(itemPath, sharee, type, permissions, name,
                password, sharelevel), sharingUser);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, String.valueOf(response.code()));
        Log.log(Level.FINE, response.body().string());
        response.close();
    }

    public OCShare getShare(String itemPath)
            throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Request Share from server - " + itemPath);
        String url = urlServer + sharingEndpoint + "?path=/" + itemPath;
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url, user);
        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.close();
        return share;
    }

    public ArrayList<OCShare> getSharesByUser(String userName)
            throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Request Shares by user - " + userName);
        String url = urlServer + sharingEndpoint + "?state=all&shared_with_me=true";
        Log.log(Level.FINE, "URL get shares by user: " + url);
        if (userName.isEmpty()) {
            userName = user; //Fallback option and default user
        }
        Request request = getRequest(url, userName.toLowerCase());
        Response response = httpClient.newCall(request).execute();
        String responseBody = response.body().string();
        Log.log(Level.FINE, "Response code: " + response.code());
        Log.log(Level.FINE, "Response body: " + responseBody);
        ArrayList<OCShare> shares = getSharesFromRequest(responseBody);
        response.close();
        return shares;
    }

    public ArrayList<OCShare> getLinksByUser(String userName)
            throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Request Links by user - " + userName);
        String url = urlServer + sharingEndpoint + "?state=all";
        Log.log(Level.FINE, "URL get Links by user: " + url);
        if (userName.isEmpty()) {
            userName = user; //Fallback option and default user
        }
        Request request = getRequest(url, userName.toLowerCase());
        Response response = httpClient.newCall(request).execute();
        String responseBody = response.body().string();
        Log.log(Level.FINE, "Response code: " + response.code());
        Log.log(Level.FINE, "Response body: " + responseBody);
        ArrayList<OCShare> shares = getSharesFromRequest(responseBody);
        ArrayList<OCShare> linksInShares = new ArrayList<>();
        for (OCShare linkInShares : shares) {
            if (linkInShares.getType().equals("3")) {
                linksInShares.add(linkInShares);
            }
        }
        response.close();
        return linksInShares;
    }

    public boolean isSharedWithMe(String itemName, String userName, boolean isGroup)
            throws IOException, ParserConfigurationException, SAXException {
        Log.log(Level.FINE, "Starts: Request items shared with me - " + itemName + " - " + userName);
        String url = urlServer + sharingEndpoint + "?shared_with_me=true";
        Log.log(Level.FINE, "URL: " + url);
        Request request;
        //if it is a group, we use a predefined sharee inside the group (Bob)
        if (isGroup) {
            request = getRequest(url, shareeU);
        } else {
            request = getRequest(url, userName);
        }
        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        ArrayList<OCShare> myShares = getSharesFromRequest(responseString);
        Log.log(Level.FINE, myShares.size() + " shares found");
        response.close();
        for (OCShare share : myShares) {
            Log.log(Level.FINE, "ItemName: " + itemName + " ShareName: " + share.getItemName());
            if (share.getItemName().contains(itemName)) { //Current item found
                Log.log(Level.FINE, "Sharee: " + share.getShareeName() + " userName: " + userName);
                return share.getShareeName().equalsIgnoreCase(userName);
            }
        }
        return false;
    }

    public void removeAllShares(String userName)
            throws IOException, ParserConfigurationException, SAXException {
        Log.log(Level.FINE, "Starts: Remove shares from a username: " + userName);
        ArrayList<OCShare> allShares = getSharesByUser(userName);
        for (OCShare share : allShares) {
            String url = urlServer + sharingEndpoint + pendingEndpoint + "/" + share.getId();
            Log.log(Level.FINE, "URL: " + url);
            Request request = deleteRequest(url);
            httpClient.newCall(request).execute();
        }
    }

    private RequestBody createBodyShare(String itemPath, String sharee, String type,
                                        String permissions, String name, String password, int isReshare)
            throws IOException {
        Log.log(Level.FINE, "Starts: Create body share");
        Log.log(Level.FINE, "BODY SHARE: path " + itemPath + " sharee: " + sharee + " type: "
                + type + " permi: " + permissions + " name:" + name + " pwd: " + password);
        FormBody.Builder body = new FormBody.Builder();
        if (isReshare == 1 && authAPI.isOidc(urlServer)) {
            body.add("path", "/Shares/" + itemPath);
        } else {
            body.add("path", itemPath);
        }
        body.add("shareType", type);
        body.add("shareWith", sharee);
        body.add("permissions", permissions);
        body.add("name", name);
        body.add("password", password);
        return body.build();
    }

    private OCShare getId(Response httpResponse)
            throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Get Id");
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        ShareSAXHandler handler = new ShareSAXHandler();

        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        httpResponse.body().close();
        return handler.getShare();
    }

    private ArrayList<OCShare> getSharesFromRequest(String response)
            throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Get all shares");
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        ShareSAXHandler handler = new ShareSAXHandler();
        Log.log(Level.FINE, "RESPONSE to parse: " + response);
        parser.parse(new InputSource(new StringReader(response)), handler);
        return handler.getAllShares();
    }
}
