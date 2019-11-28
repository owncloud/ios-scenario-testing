package utils.api;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class FilesAPI extends CommonAPI {

    private final String davEndpoint = "/remote.php/dav/files/";

    public FilesAPI(){
        super();
    }

    public void removeItem(String itemName) {

        String removeURL = urlServer+davEndpoint+user+"/";

        Request request = new Request.Builder()
                .url(removeURL+itemName+"/")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .delete()
                .build();

        try {
            httpClient.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void createFolder(String folderName) {

        String createURL = urlServer+davEndpoint+user+"/";

        Request request = new Request.Builder()
                .url(createURL+folderName+"/")
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .method("MKCOL", null)
                .build();

        try {
            httpClient.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public boolean itemExist(String itemName) {
        String createURL = urlServer+davEndpoint+user+"/";
        Response response = null;

        Request request = new Request.Builder()
                .url(createURL+itemName)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic "+credentialsB64)
                .addHeader("Host", host)
                .method("PROPFIND", null)
                .build();

        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

        switch (response.code()/100){
            case(2): { //Response 2xx, item exists
                return true;
            }
            case(4): { //Response 4xx, item does not exist
                return false;
            }
            default: {
                return false;
            }
        }
    }
}
