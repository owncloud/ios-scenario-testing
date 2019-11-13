package utils.api;

import java.util.Base64;

import okhttp3.OkHttpClient;

public class CommonAPI {

    protected final OkHttpClient httpClient = new OkHttpClient();

    protected String urlServer = "http://10.40.40.198:17000";
    protected String userAgent = "Mozilla/5.0 (Android) ownCloud-android/2.13.1";
    protected String host = "10.40.40.198:17000";
    protected String user = "user1";
    protected String password = "a";
    protected String credentialsB64 = Base64.getEncoder().encodeToString((user+":"+password).getBytes());

    public CommonAPI(){
    }

}
