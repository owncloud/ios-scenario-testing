package utils.api;

import java.util.Base64;

import okhttp3.OkHttpClient;
import utils.LocProperties;

public class CommonAPI {

    protected final OkHttpClient httpClient = new OkHttpClient();

    protected String urlServer = LocProperties.getProperties().getProperty("serverURL");
    protected String userAgent = LocProperties.getProperties().getProperty("userAgent");
    protected String host = LocProperties.getProperties().getProperty("hostName");
    protected String user = LocProperties.getProperties().getProperty("userName1");
    protected String password = LocProperties.getProperties().getProperty("passw1");;
    protected String credentialsB64 = Base64.getEncoder().encodeToString((user+":"+password).getBytes());

    public CommonAPI(){
    }

}
