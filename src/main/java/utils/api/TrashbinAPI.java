package utils.api;

import java.io.IOException;
import java.util.logging.Level;

import okhttp3.Request;
import okhttp3.Response;
import utils.log.Log;

public class TrashbinAPI extends CommonAPI {

    private String trashEndpoint = "/remote.php/dav/trash-bin/";

    public TrashbinAPI() throws IOException {
        super();
    }

    public void emptyTrashbin() throws IOException {
        Log.log(Level.FINE, "Starts: Empty Trashbin");
        String url = urlServer + trashEndpoint + user + "/";
        Log.log(Level.FINE, url);
        Request request = deleteRequest(url);
        Response response = httpClient.newCall(request).execute();
        response.close();
    }
}
