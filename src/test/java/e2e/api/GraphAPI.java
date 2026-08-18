package e2e.api;

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
import e2e.support.date.DateUtils;
import e2e.model.OCSpace;
import e2e.model.OCSpaceMember;
import e2e.model.OCSpacePermission;
import e2e.support.log.Log;
import e2e.support.parser.OCMemberJSONHandler;
import e2e.support.parser.OCUserJSONHandler;

public class GraphAPI extends CommonAPI {

    private final String graphPath = "/graph/v1.0/";
    private final String drives = "drives/";
    private final String myDrives = "me/drives/";
    private final String members = "/graph/v1beta1/drives/";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public GraphAPI() throws IOException {
    }

    public void createSpace(String name, String description, String userName) throws IOException {
        Log.log(Level.FINE, "CREATE SPACE: " + name + " " + description);
        String url = urlServer + graphPath + drives;
        Log.log(Level.FINE, "URL: " + url);
        Request request = postRequest(url, createBodySpace(name, description), userName);
        try (Response response = httpClient.newCall(request).execute()) {
            Log.log(Level.FINE, "Response Code: " + response.code());
            Log.log(Level.FINE, "Response Body: " + response.body().string());
        }
    }

    private RequestBody createBodySpace(String name, String description) {
        Log.log(Level.FINE, "BODY SPACE: Name: " + name + " . Description: " + description);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", name);
        jsonObj.put("driveType", "project");
        jsonObj.put("description", description);
        return RequestBody.create(JSON, jsonObj.toString());
    }

    public List<OCSpace> getMySpaces() throws IOException {
        Log.log(Level.FINE, "GET my SPACES");
        String url = urlServer + graphPath + myDrives;
        Request request = getRequest(url, user);
        try (Response response = httpClient.newCall(request).execute()) {
            return getSpacesFromResponse(response);
        }
    }

    public List<OCSpace> getMySpaces(String userName) throws IOException {
        Log.log(Level.FINE, "GET my SPACES");
        String url = urlServer + graphPath + myDrives;
        Request request = getRequest(url, userName);
        try (Response response = httpClient.newCall(request).execute()) {
            return getSpacesFromResponse(response);
        }
    }

    public void removeSpacesOfUser() throws IOException {
        Log.log(Level.FINE, "REMOVE custom SPACES of: " + user);
        List<OCSpace> spacesOfUser = getMySpaces();
        for (OCSpace space : spacesOfUser) {
            String url = urlServer + graphPath + drives + space.getId();
            Log.log(Level.FINE, "URL remove space: " + url);
            try (Response disableResponse = httpClient.newCall(deleteRequest(url, user)).execute()) {
                Log.log(Level.FINE, "Disable response: " + disableResponse.code());
            }
            try (Response deleteResponse = httpClient.newCall(deleteSpaceRequest(url)).execute()) {
                Log.log(Level.FINE, "Delete response: " + deleteResponse.code());
            }
        }
    }

    public void disableSpace(String name, String description) throws IOException {
        Log.log(Level.FINE, "DISABLE SPACE: " + name + " " + description);
        String spaceId = getSpaceIdFromNameAndDescription(name, description);
        String url = urlServer + graphPath + drives + spaceId;
        Log.log(Level.FINE, "URL: " + url);
        Request request = deleteRequest(url, user);
        try (Response response = httpClient.newCall(request).execute()) {
            Log.log(Level.FINE, "Response Code: " + response.code());
            Log.log(Level.FINE, "Response Body: " + response.body().string());
        }
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
        try (Response response = httpClient.newCall(request).execute()) {
            return getIdFromResponse(response);
        }
    }

    private String getIdFromResponse(Response httpResponse) throws IOException {
        String json = httpResponse.body().string();
        JSONObject obj = new JSONObject(json);
        String id = obj.getString("id");
        Log.log(Level.FINE, "ID of user: " + id);
        return id;
    }

    private String getSpaceIdFromNameAndDescription(String name, String description) throws IOException {
        List<OCSpace> mySpaces = getMySpaces();
        for (OCSpace space : mySpaces) {
            if (space.getName().trim().equals(name) && space.getDescription().trim().equals(description)) {
                return space.getId();
            }
        }
        return null;
    }

    public String getSpaceIdFromName(String name) throws IOException {
        Log.log(Level.FINE, "Look for space ID or null: " + name);
        List<OCSpace> mySpaces = getMySpaces();
        for (OCSpace space : mySpaces) {
            if (space.getName().trim().equals(name)) {
                Log.log(Level.FINE, "FOUND: ID of space: " + space.getId() + " " + space.getName());
                return space.getId();
            }
        }
        return null;
    }

    public OCSpaceMember getMemberOfSpace(String spaceName, String userName) throws IOException {
        Log.log(Level.FINE, "Get member of space: " + spaceName + " user: " + userName);
        String spaceId = getSpaceIdFromName(spaceName);
        String url = urlServer + members + spaceId + "/root/permissions";
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url, userName);
        List<OCSpaceMember> spaceMembers;
        try (Response response = httpClient.newCall(request).execute()) {
            spaceMembers = OCMemberJSONHandler.parse(response.body().string());
        }
        for (OCSpaceMember member : spaceMembers){
            Log.log(Level.FINE, "Checking " + member.getDisplayName());
            if (member.getDisplayName().equals(userName)) {
                return member;
            }
        }
        return null;
    }

    private OCSpaceMember getUserIdFromName(String userName) throws IOException {
        Log.log(Level.FINE, "Get OCMember from name: " + userName);
        String url = urlServer + graphPath + "users?%24search=%22" + userName + "%22&%24orderby=displayName";
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url);
        try (Response response = httpClient.newCall(request).execute()) {
            return OCUserJSONHandler.parse(response.body().string());
        }
    }

    private String getPermissionId(String spaceId, String permissionName) throws IOException {
        Log.log(Level.FINE, "Get id of permission: " + permissionName);
        String url = urlServer + members + spaceId + "/root/permissions";
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url);
        List<OCSpacePermission> spacePermissions;
        try (Response response = httpClient.newCall(request).execute()) {
            spacePermissions = OCMemberJSONHandler.parsePermissions(response.body().string());
        }
        for (OCSpacePermission permission : spacePermissions){
            if (permission.getPermissionName().contains(permissionName)) {
                Log.log(Level.FINE, "Found Permission: " + permission.getPermissionName() + " :" + permission.getPermissionId());
                return permission.getPermissionId();
            }
        }
        return "";
    }

    public void addMemberToSpace(String spaceName, String userName, String permission
            , String expirationDate) throws IOException {
        Log.log(Level.FINE, "Add user: " + userName + " to space: " + spaceName
                + " with permission "  + permission + " and expiration: " + expirationDate);
        String spaceId = getSpaceIdFromName(spaceName);
        String permissionId = getPermissionId(spaceId, permission);
        String userId = getUserIdFromName(userName).getId();
        boolean hasExpiration = expirationDate != null && !expirationDate.trim().isEmpty();
        String url = urlServer + members + spaceId + "/root/invite";
        Log.log(Level.FINE, "URL: " + url);
        JSONObject recipient = new JSONObject();
        recipient.put("@libre.graph.recipient.type", "user");
        recipient.put("objectId", userId);
        JSONObject jsonObj = new JSONObject();
        if (hasExpiration) {
            String expirationFormatted = DateUtils.daysToUTCForExpiration(expirationDate);
            Log.log(Level.FINE, "Formatted date: " + expirationFormatted);
            jsonObj.put("expirationDateTime", expirationFormatted);
        }
        jsonObj.put("recipients", new JSONArray().put(recipient));
        jsonObj.put("roles", new JSONArray().put(permissionId));
        Log.log(Level.FINE, "Body: " + jsonObj);
        RequestBody body = RequestBody.create(JSON, jsonObj.toString());
        Request request = postRequest(url, body, "Alice");
        try (Response response = httpClient.newCall(request).execute()) {
            Log.log(Level.FINE, "Response Code: " + response.code());
            Log.log(Level.FINE, "Response Body: " + response.body().string());
        }
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
                JSONObject root = jsonObject.getJSONObject("root");
                if (root.has("deleted")) {
                    space.setStatus("deleted");
                } else {
                    space.setStatus("active");
                }
                spaces.add(space);
                Log.log(Level.FINE, "Space id returned: " + space.getId() + " " + space.getName());
            }
        }
        return spaces;
    }
}
