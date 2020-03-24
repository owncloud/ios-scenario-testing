package utils.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Request;
import okhttp3.Response;
import utils.LocProperties;
import utils.entities.OCShare;
import utils.log.Log;
import utils.parser.ShareSAXHandler;

public class ShareAPI extends CommonAPI {

    private String sharingEndpoint = "/ocs/v2.php/apps/files_sharing/api/v1/shares";
    private String shareeUser = LocProperties.getProperties().getProperty("userToShare");
    private String shareePassword = LocProperties.getProperties().getProperty("userToSharePwd");
    private String credentialsB64Sharee = Base64.getEncoder().encodeToString((shareeUser+":"+shareePassword).getBytes());

    public ShareAPI(){
        super();
    }

    public OCShare getShare(String itemPath)
            throws IOException, SAXException, ParserConfigurationException {
        String url = urlServer + sharingEndpoint + "?path=/" + itemPath;
        Log.log(Level.FINE, "Starts: Request Share from server");
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url);
        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        return share;
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
            throws IOException, SAXException, ParserConfigurationException{
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        ShareSAXHandler handler = new ShareSAXHandler();

        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        httpResponse.body().close();
        return handler.getShare();
    }

}
