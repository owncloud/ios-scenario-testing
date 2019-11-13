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
import utils.entities.Share;

public class ShareAPI extends CommonAPI {

    private String serverUrl = "http://10.40.40.198:17000";
    private String host = "10.40.40.198:17000";
    private String sharingEndpoint = "/ocs/v2.php/apps/files_sharing/api/v1/shares";
    private String sharingUser = "user1";
    private String shareeUser = "user2";
    private String sharingPassword = "a";
    private String shareePassword = "a";
    private String userAgent = "Mozilla/5.0 (Android) ownCloud-android/2.13.1";
    private String ownerName = "user1";
    private String credentialsB64 = Base64.getEncoder().encodeToString((sharingUser+":"+sharingPassword).getBytes());
    private String credentialsB64Sharee = Base64.getEncoder().encodeToString((shareeUser+":"+sharingPassword).getBytes());


    public ShareAPI(){
        super();
    }

    public String getIdShare(String itemPath)
            throws IOException, SAXException, ParserConfigurationException, InterruptedException {

        String requestString = serverUrl + sharingEndpoint + "?path=/" + itemPath;

        Request request = new Request.Builder()
                .url(requestString)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        Share share = getId(response);
        return share.getId();

    }

    public boolean checkCorrectShared (String id, String itemName, String type, String shareeName)
            throws IOException, SAXException, ParserConfigurationException {

        String requestString = serverUrl + sharingEndpoint + "/" + id;

        Request request = new Request.Builder()
                .url(requestString)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        Share share = getId(response);
        if ((share.getId().equals(id)) &&
                (share.getShareeName().equals(shareeName)) &&
                (share.getType().equals(type)) &&
                (share.getOwner().equals(ownerName)))
            return true;
        else
            return false;

    }

    public boolean checkReceivedShare (String id, String itemName, String type, String shareeName)
            throws IOException, SAXException, ParserConfigurationException {

        String requestString = serverUrl + sharingEndpoint + "?shared_with_me=true";

        Request request = new Request.Builder()
                .url(requestString)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64Sharee)
                .addHeader("Host", host)
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        Share share = getId(response);
        if ((share.getId().equals(id)) &&
                (share.getShareeName().equals(shareeName)) &&
                (share.getType().equals(type)) &&
                (share.getOwner().equals(ownerName)))
            return true;
        else
            return false;

    }

    public void removeShare(String id) throws IOException {

        String requestString = serverUrl + sharingEndpoint + "/" + id;

        Request request = new Request.Builder()
                .url(requestString)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .delete()
                .build();

        httpClient.newCall(request).execute();

    }

    private Share getId(Response httpResponse)
            throws IOException, SAXException, ParserConfigurationException{
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();

        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        return handler.getShare();
    }

}