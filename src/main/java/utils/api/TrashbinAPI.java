package utils.api;

import java.io.IOException;
import java.util.logging.Level;

import okhttp3.Request;
import okhttp3.Response;
import utils.log.Log;

public class TrashbinAPI extends CommonAPI {

    private final String trashEndpointOCIS = "/remote.php/dav/spaces/trash-bin/";
    private final String trashEndpointOC10 = "/remote.php/dav/trash-bin/";

    public TrashbinAPI() throws IOException {
        super();
    }

    public void emptyTrashbin(String userName) throws IOException {
        Log.log(Level.FINE, "Starts: Empty trashbin");
        String url = urlServer + getTrashEndpoint(userName);
        Log.log(Level.FINE, url);
        Request request = deleteRequest(url, userName);
        Response response = httpClient.newCall(request).execute();
        response.close();
    }

    private String getTrashEndpoint(String userName) {
        if (isOidc) {
            return trashEndpointOCIS + personalSpaces.get(userName);
        } else {
            return trashEndpointOC10 + userName + "/";
        }
    }
}
