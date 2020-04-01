package utils.api;

import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import utils.LocProperties;

public class CommonAPI {

    protected final OkHttpClient httpClient = new OkHttpClient();

    protected String urlServer = LocProperties.getProperties().getProperty("serverURL");
    protected String userAgent = LocProperties.getProperties().getProperty("userAgent");
    protected String host = LocProperties.getProperties().getProperty("hostName");

    protected String user = LocProperties.getProperties().getProperty("userName1");
    protected String password = LocProperties.getProperties().getProperty("passw1");
    protected String shareeUser = LocProperties.getProperties().getProperty("userToShare");
    protected String shareePassword = LocProperties.getProperties().getProperty("userToSharePwd");
    protected String credentialsB64 = Base64.getEncoder().encodeToString((user+":"+password).getBytes());
    protected String credentialsB64Sharee = Base64.getEncoder().encodeToString((shareeUser+":"+shareePassword).getBytes());


    protected String basicPropfindBody = "<?xml version='1.0' encoding='UTF-8' ?>\n" +
            "<propfind xmlns=\"DAV:\" xmlns:CAL=\"urn:ietf:params:xml:ns:caldav\"" +
            " xmlns:CARD=\"urn:ietf:params:xml:ns:carddav\" " +
            " xmlns:SABRE=\"http://sabredav.org/ns\" " +
            " xmlns:OC=\"http://owncloud.org/ns\">\n" +
            "  <prop>\n" +
            "    <displayname />\n" +
            "    <getcontenttype />\n" +
            "    <resourcetype />\n" +
            "    <getcontentlength />\n" +
            "    <getlastmodified />\n" +
            "    <creationdate />\n" +
            "    <getetag />\n" +
            "    <quota-used-bytes />\n" +
            "    <quota-available-bytes />\n" +
            "    <OC:permissions />\n" +
            "    <OC:id />\n" +
            "    <OC:size />\n" +
            "    <OC:privatelink />\n" +
            "  </prop>\n" +
            "</propfind>";

    public CommonAPI(){
    }

    protected Request davRequest(String url, String method, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+ credentialsB64)
                .addHeader("Host", host)
                .method(method, body)
                .build();
        return request;
    }

    protected Request postRequest(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+ credentialsB64)
                .addHeader("Host", host)
                .post(body)
                .build();
        return request;
    }

    protected Request deleteRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .delete()
                .build();
        return request;
    }

    protected Request getRequest(String url, boolean isSharee) {
        String credentials = (isSharee) ? credentialsB64Sharee : credentialsB64;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentials)
                .addHeader("Host", host)
                .get()
                .build();
        return request;
    }

    /*overloaded, to use with specific credentials*/
    protected Request getRequest(String url, String credentials) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentials)
                .addHeader("Host", host)
                .get()
                .build();
        return request;
    }
}
