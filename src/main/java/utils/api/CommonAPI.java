package utils.api;

import okhttp3.OkHttpClient;

public class CommonAPI {

    protected final OkHttpClient httpClient = new OkHttpClient();
    protected String urlServer;

    public CommonAPI(){
    }

}
