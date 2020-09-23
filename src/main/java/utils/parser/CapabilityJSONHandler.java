package utils.parser;

import org.json.JSONObject;

import java.util.logging.Level;

import utils.entities.OCCapability;
import utils.log.Log;

public class CapabilityJSONHandler {

    public static OCCapability ocCapability = OCCapability.getInstance();
    private String json;

    public CapabilityJSONHandler(String json){
        Log.log(Level.FINE, "JSON: " + json);
        this.json = json;
    }

    public void parsePublicLink(){
        JSONObject object = new JSONObject(json);
        JSONObject ocs = object.getJSONObject("ocs");
        JSONObject data = ocs.getJSONObject("data");
        JSONObject capabilities = data.getJSONObject("capabilities");
        JSONObject sharing = capabilities.getJSONObject("files_sharing");
        JSONObject publicSharing = sharing.getJSONObject("public");
        JSONObject password = publicSharing.getJSONObject("password");
        JSONObject enforcedFor = password.getJSONObject("enforced_for");
        JSONObject expireDate = publicSharing.getJSONObject("expire_date");
        ocCapability.setPasswordEnforcedReadOnly(enforcedFor.getBoolean("read_only"));
        ocCapability.setPasswordEnforcedReadWriteDelete(enforcedFor.getBoolean("read_write"));
        ocCapability.setPasswordEnforcedUploadOnly(enforcedFor.getBoolean("upload_only"));
        ocCapability.setPasswordEnforced(password.getBoolean("enforced"));
        if (expireDate.has("enforced")) {
            ocCapability.setExpirationDateEnforced(expireDate.getBoolean("enforced"));
        } else {
            ocCapability.setExpirationDateEnforced(false);
        }
        if (expireDate.has("days")) {
            ocCapability.setExpirationDateDays(expireDate.getInt("days"));
        } else {
            ocCapability.setExpirationDateDays(0);
        }
    }

}
