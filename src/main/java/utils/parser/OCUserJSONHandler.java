package utils.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.entities.OCSpaceMember;

public class OCUserJSONHandler {

    public static OCSpaceMember parse(String jsonText) {
        JSONObject root = new JSONObject(jsonText);
        JSONArray usersArray = root.getJSONArray("value");
        OCSpaceMember member = new OCSpaceMember();
        //Getting the first one
        JSONObject memberFromServer = usersArray.getJSONObject(0);
        member.setId(memberFromServer.getString("id"));
        member.setDisplayName(memberFromServer.getString("displayName"));
        return member;
    }
}
