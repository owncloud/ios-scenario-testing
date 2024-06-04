package utils.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.LocProperties;
import utils.entities.OCSpace;
import utils.log.Log;

public class GraphAPI extends CommonAPI {

    private final String graphPath = "/graph/v1.0/";
    private final String drives = "drives/";
    private final String myDrives = "me/drives/";
    private final String owner = LocProperties.getProperties().getProperty("userNameDefault");

    public GraphAPI() throws IOException {
    }

    public void createSpace(String name, String description, String userName) throws IOException {
        Log.log(Level.FINE, "CREATE SPACE: " + name + " " + description);
        String url = urlServer + graphPath + drives;
        Log.log(Level.FINE, "URL: " + url);
        Request request = postRequest(url, createBodySpace(name, description), userName);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, "Response Code: " + response.code());
        Log.log(Level.FINE, "Response Body: " + response.body().string());
        response.close();
    }

    private RequestBody createBodySpace(String name, String description) {
        Log.log(Level.FINE, "BODY SPACE: Name: " + name + " . Description: " + description);
        String json = "{\"Name\":\" " + name + " \",\"driveType\":\"project\", \"description\":\" " + description + " \"}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        return body;
    }

    private List<OCSpace> geyMySpaces() throws IOException {
        Log.log(Level.FINE, "GET my SPACES");
        String url = urlServer + graphPath + myDrives;
        Request request = getRequest(url, user);
        Response response = httpClient.newCall(request).execute();
        List<OCSpace> mySpaces = getSpacesFromResponse(response);
        response.close();
        return mySpaces;
    }

    public void removeSpacesOfUser() throws IOException {
        Log.log(Level.FINE, "REMOVE custom SPACES of: " + user);
        List<OCSpace> spacesOfUser = geyMySpaces();
        for (OCSpace space : spacesOfUser) {
            String url = urlServer + graphPath + drives + space.getId();
            Log.log(Level.FINE, "URL remove space: " + url);
            //First, disable
            Request requestDisable = deleteRequest(url, user);
            httpClient.newCall(requestDisable).execute();
            //Then, delete
            Request requestDelete = deleteSpaceRequest(url);
            httpClient.newCall(requestDelete).execute();
        }
    }

    public void disableSpace(String name, String description) throws IOException {
        Log.log(Level.FINE, "DISABLE SPACE: " + name + " " + description);
        String spaceId = getSpaceIdFromName(name, description);
        String url = urlServer + graphPath + drives + spaceId;
        Log.log(Level.FINE, "URL: " + url);
        Request request = deleteRequest(url, user);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, "Response Code: " + response.code());
        Log.log(Level.FINE, "Response Body: " + response.body().string());
        response.close();
    }

    private Request deleteSpaceRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsB64)
                .addHeader("Host", host)
                .addHeader("Purge", "T")
                .delete()
                .build();
        return request;
    }

    private String getUserId(String userName) throws IOException {
        Log.log(Level.FINE, "GET id OF: " + user);
        String url = urlServer + graphPath + "me";
        Request request = getRequest(url, userName);
        Response response = httpClient.newCall(request).execute();
        String userId = getIdFromResponse(response);
        response.close();
        return userId;
    }

    private String getIdFromResponse(Response httpResponse) throws IOException {
        String json = httpResponse.body().string();
        JSONObject obj = new JSONObject(json);
        String id = obj.getString("id");
        Log.log(Level.FINE, "ID of user: " + id);
        return id;
    }

    private String getSpaceIdFromName(String name, String description) throws IOException {
        List<OCSpace> mySpaces = geyMySpaces();
        for (OCSpace space : mySpaces) {
            if (space.getName().trim().equals(name) && space.getDescription().trim().equals(description)) {
                return space.getId();
            }
        }
        return null;
    }

    private List<OCSpace> getSpacesFromResponse(Response httpResponse) throws IOException {
        String json = httpResponse.body().string();
        ArrayList<OCSpace> spaces = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray value = obj.getJSONArray("value");
        for (int i = 0; i < value.length(); i++) {
            JSONObject jsonObject = value.getJSONObject(i);
            String type = jsonObject.getString("driveType");
            if (type.equals("project")) { //Just for user created spaces
                OCSpace space = new OCSpace();
                space.setType(jsonObject.getString("driveType"));
                space.setId(jsonObject.getString("id"));
                space.setName(jsonObject.getString("name"));
                space.setDescription(jsonObject.getString("description"));
                JSONObject owner = jsonObject.getJSONObject("owner");
                JSONObject user = owner.getJSONObject("user");
                space.setOwner(user.getString("id"));
                spaces.add(space);
                Log.log(Level.FINE, "Space id returned: " + space.getId() + " " + space.getName());
            }
        }
        return spaces;
    }
}
