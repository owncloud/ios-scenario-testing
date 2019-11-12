package utils.api;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.entities.Share;

public class ShareAPI {

    private final OkHttpClient httpClient = new OkHttpClient();

    public ShareAPI(){

    }

    public String getIdShare()
            throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        Request request = new Request.Builder()
                .url("http://10.40.40.198:17000/ocs/v2.php/apps/files_sharing/api/v1/shares?path=/Documents")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjE6YQ==")
                .addHeader("Host", "10.40.40.198:17000")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        Share share = getId(response);
        return share.getId();

    }

    public boolean checkCorrectShared (String id, String itemName, String type, String shareeName)
            throws IOException, SAXException, ParserConfigurationException {

        Request request = new Request.Builder()
                .url("http://10.40.40.198:17000/ocs/v2.php/apps/files_sharing/api/v1/shares/"+id)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjE6YQ==")
                .addHeader("Host", "10.40.40.198:17000")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        Share share = getId(response);
        if ((share.getId().equals(id)) &&
                (share.getShareeName().equals(shareeName)) &&
                (share.getType().equals(type)) &&
                (share.getOwner().equals("user1")))
            return true;
        else
            return false;

    }

    public boolean checkReceivedShare (String id, String itemName, String type, String shareeName)
            throws IOException, SAXException, ParserConfigurationException {

        Request request = new Request.Builder()
                .url("http://10.40.40.198:17000/ocs/v2.php/apps/files_sharing/api/v1/shares?shared_with_me=true")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjI6YQ==")
                .addHeader("Host", "10.40.40.198:17000")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        Share share = getId(response);
        if ((share.getId().equals(id)) &&
                (share.getShareeName().equals(shareeName)) &&
                (share.getType().equals(type)) &&
                (share.getOwner().equals("user1")))
            return true;
        else
            return false;

    }

    public void removeShare(String id) throws IOException {

        Request request = new Request.Builder()
                .url("http://10.40.40.198:17000/ocs/v2.php/apps/files_sharing/api/v1/shares/"+id)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjE6YQ==")
                .addHeader("Host", "10.40.40.198:17000")
                .delete()
                .build();

        httpClient.newCall(request).execute();

    }

    public void removeFolder(String folderName) throws IOException {

        Request request = new Request.Builder()
                .url("http://10.40.40.198:17000/remote.php/dav/files/user1/"+folderName+"/")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjE6YQ==")
                .addHeader("Host", "10.40.40.198:17000")
                .delete()
                .build();

        httpClient.newCall(request).execute();

    }

    public Share getId(Response httpResponse)
            throws IOException, SAXException, ParserConfigurationException{
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();

        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        return handler.getShare();
    }

}
