package utils.api;

import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.LocProperties;
import utils.log.Log;
import utils.network.oCHttpClient;
import utils.parser.DrivesJSONHandler;


public class CommonAPI {

    protected OkHttpClient httpClient =  oCHttpClient.getUnsafeOkHttpClient();

    protected String urlServer = System.getProperty("server");
    protected String userAgent = LocProperties.getProperties().getProperty("userAgent");
    protected String host = urlServer.split("//")[1];

    protected String user = LocProperties.getProperties().getProperty("userNameDefault");
    protected String password = LocProperties.getProperties().getProperty("pwdDefault");
    protected String credentialsB64 = Base64.getEncoder().encodeToString((user+":"+password).getBytes());

    protected final String webdavEndpoint = "/remote.php/dav/files";
    protected final String spacesEndpoint = "/dav/spaces/";
    protected final String graphDrivesEndpoint = "/graph/v1.0/me/drives";
    protected String davEndpoint = "";
    protected String space = "";

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
            "    <OC:favorite/>\n" +
            "  </prop>\n" +
            "</propfind>";

    protected String setFavoriteBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<D:propertyupdate xmlns:D=\"DAV:\" xmlns:oc=\"http://owncloud.org/ns\">\n" +
            "  <D:set>\n" +
            "    <D:prop>\n" +
            "      <favorite xmlns=\"http://owncloud.org/ns\">1</favorite>\n" +
            "    </D:prop>\n" +
            "  </D:set>\n" +
            "</D:propertyupdate>";

    public CommonAPI()
            throws IOException {
        AuthAPI authAPI = new AuthAPI();
        //ftm, OIDC == oCIS. Bad.
        if (authAPI.checkAuthMethod().equals("OIDC")){
            space = getPersonalDrives(urlServer);
            davEndpoint = spacesEndpoint + space;
        } else {
            davEndpoint = webdavEndpoint+"/"+user;
        }
        Log.log(Level.FINE, "Endpoint: " + davEndpoint);
    }

    public String getEndpoint(){
        return davEndpoint;
    }

    public String getSharedEndpoint() throws IOException {
        Log.log(Level.FINE, "Starts: Get Shared Space Endpoint");
        String sharesSpaceId = getSharesDrives(urlServer);
        String endpoint = spacesEndpoint + sharesSpaceId;
        Log.log(Level.FINE, "Endpoint: " + endpoint);
        return endpoint;
    }

    protected Request davRequest(String url, String method, RequestBody body, String userName) {
        Log.log(Level.FINE, "Starts: DAV Request against account: " + userName);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsBuilder(userName))
                .addHeader("Host", host)
                .method(method, body)
                .build();
        return request;
    }

    protected Request postRequest(String url, RequestBody body, String userName) {
        Log.log(Level.FINE, "Starts: POST Request with username " + userName + ": " + url);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsBuilder(userName))
                .addHeader("Host", host)
                .post(body)
                .build();
        Log.log(Level.FINE, "RE: " + request.toString());
        return request;
    }

    protected Request deleteRequest(String url){
        Log.log(Level.FINE, "Starts: DELETE Request: " + url);
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

    //overloaded, to use with specific credentials
    protected Request getRequest(String url, String userName) {
        Log.log(Level.FINE, "Starts: GET Request with username " + userName + ": " + url );
        String password = LocProperties.getProperties().getProperty("pwdDefault");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsBuilder(userName))
                .addHeader("Host", host)
                .get()
                .build();
        return request;
    }

    private String getPersonalDrives(String url)
            throws IOException {
        Log.log(Level.FINE, "Starts: GET personal drives: " + url );
        Request request = getRequest(url + graphDrivesEndpoint, user);
        Response response = httpClient.newCall(request).execute();
        String body = response.body().string();
        response.close();
        String personalId = DrivesJSONHandler.getPersonalDriveId(body);
        Log.log(Level.FINE, "Personal Drive ID: " + personalId);
        return personalId;
    }

    private String getSharesDrives(String url)
            throws IOException {
        Log.log(Level.FINE, "Starts: GET personal drives: " + url );
        Request request = getRequest(url + graphDrivesEndpoint, user);
        Response response = httpClient.newCall(request).execute();
        String body = response.body().string();
        response.close();
        String sharesId = DrivesJSONHandler.getSharesDriveId(body);
        Log.log(Level.FINE, "Shares Drive ID: " + sharesId);
        return sharesId;
    }

    private String credentialsBuilder (String userName) {
        return Base64.getEncoder().encodeToString((userName.toLowerCase()+":"+password).getBytes());
    }
}
