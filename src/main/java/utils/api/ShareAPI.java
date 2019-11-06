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

public class ShareAPI {

    private final OkHttpClient httpClient = new OkHttpClient();

    public ShareAPI(){

    }

    public String getIdShare() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        Request request = new Request.Builder()
                .url("http://10.40.40.198:29000/ocs/v2.php/apps/files_sharing/api/v1/shares?path=/Documents")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjE6YQ=")
                .addHeader("Host", "10.40.40.198:29000")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        PublicShare publicShare = getId(response);
        return publicShare.getId();

    }

    public boolean checkCorrectShared (String id, String itemName, String type, String shareeName)
            throws IOException, SAXException, ParserConfigurationException {

        Request request = new Request.Builder()
                .url("http://10.40.40.198:29000/ocs/v2.php/apps/files_sharing/api/v1/shares/"+id)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjE6YQ=")
                .addHeader("Host", "10.40.40.198:29000")
                .get()
                .build();

        Response response = httpClient.newCall(request).execute();
        PublicShare publicShare = getId(response);
        if ((publicShare.getId().equals(id)) &&
                (publicShare.getShareeName().equals(shareeName)) &&
                (publicShare.getType().equals(type)) &&
                (publicShare.getOwner().equals("user1")))
            return true;
        else
            return false;

    }

    public void removeShare(String id) throws IOException {

        Request request = new Request.Builder()
                .url("http://10.40.40.198:29000/ocs/v2.php/apps/files_sharing/api/v1/shares/"+id)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", "Mozilla/5.0 (Android) ownCloud-android/2.13.1")
                .addHeader("Authorization", "Basic dXNlcjE6YQ=")
                .addHeader("Host", "10.40.40.198:29000")
                .delete()
                .build();

        httpClient.newCall(request).execute();

    }

    public PublicShare getId(Response httpResponse) throws IOException, SAXException, ParserConfigurationException{
        //Create SAX parser
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();

        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        return handler.getPublicShare();
    }

}
