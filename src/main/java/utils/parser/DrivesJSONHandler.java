package utils.parser;

import org.json.JSONArray;
import org.json.JSONObject;

public class DrivesJSONHandler {

    public static String getPersonalDriveId(String json){
        JSONObject drivesObj = new JSONObject(json);
        JSONArray valuesArr = drivesObj.getJSONArray("value");
        for (int i = 0; i < valuesArr.length() ; i++){
            JSONObject drivesList = valuesArr.getJSONObject(i);
            if (drivesList.get("driveType").equals("personal")){
                return (String)drivesList.get("id");
            }
        }
        return "";
    }

}
