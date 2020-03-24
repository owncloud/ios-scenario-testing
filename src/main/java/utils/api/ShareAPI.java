package utils.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Request;
import okhttp3.Response;
import utils.LocProperties;
import utils.entities.OCShare;
import utils.parser.ShareSAXHandler;

public class ShareAPI extends CommonAPI {

    private String sharingEndpoint = "/ocs/v2.php/apps/files_sharing/api/v1/shares";
    private String shareeUser = LocProperties.getProperties().getProperty("userToShare");
    private String shareePassword = LocProperties.getProperties().getProperty("userToSharePwd");
    private String credentialsB64Sharee = Base64.getEncoder().encodeToString((shareeUser+":"+shareePassword).getBytes());

    public ShareAPI(){
        super();
    }

    /*public String getIdShare(String itemPath)
            throws IOException, SAXException, ParserConfigurationException {

        String url = urlServer + sharingEndpoint + "?path=/" + itemPath;

        Request request = getRequest(url);

        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        return share.getId();
    }*/

    public OCShare getShare(String itemPath)
            throws IOException, SAXException, ParserConfigurationException {

        String url = urlServer + sharingEndpoint + "?path=/" + itemPath;
        Request request = getRequest(url);

        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        return share;
    }

    /*public boolean checkCorrectShared (String id, String type, String shareeName)
            throws IOException, SAXException, ParserConfigurationException {

        String url = urlServer + sharingEndpoint + "/" + id;

        Request request = getRequest(url);

        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        if ((share.getId().equals(id)) &&
                (share.getShareeName().equals(shareeName)) &&
                (share.getType().equals(type)) &&
                (share.getOwner().equals(user)))
            return true;
        else
            return false;
    }*/

    /*public OCShare getShare (String itemName, List<List<String>> dataList)
            throws IOException, SAXException, ParserConfigurationException {

        HashMap<String, String> mapBody = new HashMap<String, String>();
        boolean hasPassword = false;

        mapBody.put("path",itemName + "\n");
        StringBuilder body = new StringBuilder();
        body.append("path: /" + itemName + "\n");
        for (List<String> rows : dataList) {
            mapBody.put(rows.get(0),rows.get(1));
            body.append(rows.get(0) + ":" + rows.get(1) + "\n");
        }
        String url = urlServer + sharingEndpoint + "/" + mapBody.get("id");
        String shareType = mapBody.get("shareType");
        Request request = getRequest(url);

        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();

        return share;

    }*/

    /*public boolean checkCorrectShared (String id, String type)
            throws IOException, SAXException, ParserConfigurationException {

        String url = urlServer + sharingEndpoint + "/" + id;

        Request request = getRequest(url);

        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        if ((share.getId().equals(id)) &&
                (share.getType().equals(type)) &&
                (share.getOwner().equals(user)))
            return true;
        else
            return false;
    }

    public boolean checkReceivedShare (String id, String type, String shareeName)
            throws IOException, SAXException, ParserConfigurationException {

        String url = urlServer + sharingEndpoint + "?shared_with_me=true";

        Request request = getRequest(url, credentialsB64Sharee);

        Response response = httpClient.newCall(request).execute();
        OCShare share = getId(response);
        response.body().close();
        if ((share.getId().equals(id)) &&
                (share.getShareeName().equals(shareeName)) &&
                (share.getType().equals(type)) &&
                (share.getOwner().equals(user)))
            return true;
        else
            return false;
    }*/

    public void removeShare(String id)
            throws IOException {
        String url = urlServer + sharingEndpoint + "/" + id;
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
