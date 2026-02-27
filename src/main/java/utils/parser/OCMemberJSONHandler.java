package utils.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.entities.OCSpaceMember;
import utils.entities.OCSpacePermission;

public class OCMemberJSONHandler {

    public static List<OCSpacePermission> parsePermissions(String jsonText){
        JSONObject root = new JSONObject(jsonText);
        ArrayList<OCSpacePermission> permissionList = new ArrayList<OCSpacePermission>();
        JSONArray rolesArray = root
                .getJSONArray("@libre.graph.permissions.roles.allowedValues");
        for (int i = 0; i < rolesArray.length(); i++) {
            JSONObject permission = rolesArray.getJSONObject(i);
            String id = permission.getString("id");
            String displayName = permission.getString("displayName");
            String description = permission.getString("description");
            permissionList.add(new OCSpacePermission(id, displayName, description));
        }
        return permissionList;
    }

    public static List<OCSpaceMember> parse(String jsonText) {
        JSONObject root = new JSONObject(jsonText);

        // 1. Mapping roles
        Map<String, String> rolesMap = new HashMap<>();
        JSONArray rolesArray = root
                .getJSONArray("@libre.graph.permissions.roles.allowedValues");

        for (int i = 0; i < rolesArray.length(); i++) {
            JSONObject role = rolesArray.getJSONObject(i);
            String id = role.getString("id");
            String displayName = role.getString("displayName");
            rolesMap.put(id, displayName);
        }

        // 2. Check members
        List<OCSpaceMember> result = new ArrayList<>();
        JSONArray values = root.getJSONArray("value");

        for (int i = 0; i < values.length(); i++) {
            JSONObject item = values.getJSONObject(i);

            String expirationDate = item.optString("expirationDateTime", null);

            // ignore non-users
            if (!item.has("grantedToV2"))
                continue;

            JSONObject memberSpace = item.getJSONObject("grantedToV2");
            JSONObject user = memberSpace.getJSONObject("user");
            String userId = user.optString("id", null);
            String userType = user.optString("@libre.graph.userType", null);
            String displayName = user.optString("displayName", null);

            // roles
            String permission = null;
            if (item.has("roles")) {
                JSONArray roles = item.getJSONArray("roles");
                if (!roles.isEmpty()) {
                    String roleId = roles.getString(0);
                    permission = rolesMap.get(roleId);
                }
            }

            OCSpaceMember m = new OCSpaceMember(userId, userType, displayName, permission, expirationDate);
            result.add(m);
        }
        return result;
    }
}
