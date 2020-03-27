package utils.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Request;
import okhttp3.Response;
import utils.entities.OCShare;
import utils.log.Log;
import utils.parser.ShareSAXHandler;

public class ShareAPI extends CommonAPI {

    private String sharingEndpoint = "/ocs/v2.php/apps/files_sharing/api/v1/shares";

    public ShareAPI(){
        super();
    }

    public OCShare getShare(String itemPath)
            throws IOException, SAXException, ParserConfigurationException {
        String url = urlServer + sharingEndpoint + "?path=/" + itemPath;
        Log.log(Level.FINE, "Starts: Request Share from server - " + itemPath);
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url, false);
        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        return share;
    }

    public boolean isSharedWithMe(String itemName)
            throws IOException, ParserConfigurationException, SAXException {
        String url = urlServer + sharingEndpoint + "?shared_with_me=true";
        Log.log(Level.FINE, "Starts: Request items shared with me - " + itemName);
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url, true);
        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        Log.log(Level.FINE, "Item returned: Sharee: " +
                share.getShareeName() + " - Owner: " + share.getOwner());
        return share.getShareeName().equals("user2") && share.getOwner().equals("user1");
    }

    public void removeShare(String id)
            throws IOException {
        String url = urlServer + sharingEndpoint + "/" + id;
        Log.log(Level.FINE, "Starts: Remove Share from server");
        Log.log(Level.FINE, "URL: " + url);
        Request request = deleteRequest(url);
        httpClient.newCall(request).execute();
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
