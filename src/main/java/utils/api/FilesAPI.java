package utils.api;

import java.io.IOException;

import okhttp3.Request;

public class FilesAPI extends CommonAPI {

    private final String davEndpoint = "/remote.php/dav/files/";

    public FilesAPI(){
        super();
    }

    public void removeFolder(String folderName) throws IOException {

        String removeURL = urlServer+davEndpoint;

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
}
