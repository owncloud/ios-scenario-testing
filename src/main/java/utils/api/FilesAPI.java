package utils.api;

import java.io.IOException;

import okhttp3.Request;

public class FilesAPI extends CommonAPI {

    private final String davEndpoint = "/remote.php/dav/files/";

    public FilesAPI(){
        super();
    }

    public void removeItem(String itemName) throws IOException {

        String removeURL = urlServer+davEndpoint+user+"/";

        Request request = new Request.Builder()
                .url(removeURL+itemName+"/")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .delete()
                .build();

        httpClient.newCall(request).execute();

    }

    public void createFolder(String folderName) throws IOException {

        String createURL = urlServer+davEndpoint+user+"/";

        Request request = new Request.Builder()
                .url(createURL+folderName+"/")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .method("MKCOL", null)
                .build();

        httpClient.newCall(request).execute();

    }
}
