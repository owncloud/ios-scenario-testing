package utils.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.LocProperties;
import utils.date.DateUtils;
import utils.entities.OCCapability;
import utils.entities.OCShare;
import utils.log.Log;
import utils.parser.ShareSAXHandler;

public class ShareAPI extends CommonAPI {

    private String sharingEndpoint = "/ocs/v1.php/apps/files_sharing/api/v1/shares";
    private String shareeEndpoint = "/ocs/v2.php/apps/files_sharing/api/v1/sharees";
    private final String owner = LocProperties.getProperties().getProperty("userName1");
    private final String shareeU = LocProperties.getProperties().getProperty("userToShare");
    private final String shareeG = LocProperties.getProperties().getProperty("groupToShare");

    public ShareAPI(){
        super();
    }

    public void createShare(String sharingUser, String itemPath, String sharee, String type,
                            String permissions, String name)
            throws IOException {
        String url = urlServer + sharingEndpoint;
        Log.log(Level.FINE, "Starts: Create Share - " + sharingUser + " " + sharee + " "
                + itemPath + " " + type);
        Log.log(Level.FINE, "URL: " + url);
        Request request = postRequest(url, createBodyShare(itemPath, sharee, type, permissions, name), sharingUser);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, String.valueOf(response.code()));
        Log.log(Level.FINE, response.body().string());
        response.close();
    }

    public OCShare getShare(String itemPath)
            throws IOException, SAXException, ParserConfigurationException {
        String url = urlServer + sharingEndpoint + "?path=/" + itemPath;
        Log.log(Level.FINE, "Starts: Request Share from server - " + itemPath);
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url);
        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.close();
        return share;
    }

    public boolean isSharedWithMe(String itemName, String userName, boolean isGroup)
            throws IOException, ParserConfigurationException, SAXException {
        String url = urlServer + sharingEndpoint + "?shared_with_me=true";
        Log.log(Level.FINE, "Starts: Request items shared with me - " + itemName + " - " + userName);
        Log.log(Level.FINE, "URL: " + url);
        Request request;
        //if it is a group, we use a predefined sharee inside the group (user2)
        if (isGroup){
            request = getRequest(url, shareeU);
        } else {
            request = getRequest(url, userName);
        }
        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.close();
        //Is not shared if the list of shares is empty (null) or any other share exists (different itemName)
        if (share == null || !share.getItemName().equalsIgnoreCase(itemName)) {
            Log.log(Level.FINE, itemName + " not shared with me");
            return false;
        }
        Log.log(Level.FINE, "Item returned: Sharee:" + share.getShareeName() +". Expected sharee:" + userName);
        Log.log(Level.FINE, "Owner returned:" + share.getOwner() +". Expected owner:" + owner);
        Log.log(Level.FINE, String.valueOf(share.getShareeName().equals(userName) && share.getOwner().equals(owner)));
        return share.getShareeName().equalsIgnoreCase(userName) && share.getOwner().equalsIgnoreCase(owner);
    }

    public void removeShare(String id)
            throws IOException {
        String url = urlServer + sharingEndpoint + "/" + id;
        Log.log(Level.FINE, "Starts: Remove Share from server");
        Log.log(Level.FINE, "URL: " + url);
        Request request = deleteRequest(url);
        httpClient.newCall(request).execute();
    }

    private RequestBody createBodyShare(String itemPath, String sharee, String type,
                                        String permissions, String name) {
        Boolean passwordEnforced = OCCapability.getInstance().isPasswordEnforced();
        Boolean expirationEnforced = OCCapability.getInstance().isExpirationDateEnforced();
        FormBody.Builder body = new FormBody.Builder();
        body.add("path", "\\" + itemPath + "\\");
        body.add("shareType", type);
        body.add("shareWith", sharee);
        body.add("permissions", permissions);
        body.add("name", name);
        //Password and expiration in body in case of enforcement
        if (passwordEnforced){
            body.add("password", "a");
        }
        if (expirationEnforced){
            //Add 7 days in the future as default...
            body.add("expirationDate", DateUtils.dateInDaysShareRequestFormat("7"));
        }
        return body.build();
    }

    private OCShare getId(Response httpResponse)
            throws IOException, SAXException, ParserConfigurationException {
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        ShareSAXHandler handler = new ShareSAXHandler();

        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        httpResponse.body().close();
        return handler.getShare();
    }
}
