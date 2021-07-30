package ios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import utils.date.DateUtils;
import utils.entities.OCCapability;
import utils.entities.OCShare;
import utils.log.Log;

public class SharePage extends CommonPage {

    public SharePage(){
        super();
    }

    public boolean checkCorrectShare(OCShare remoteShare, List<List<String>> dataList ){
        Log.log(Level.FINE, "Starts: Check correct share");
        HashMap<String, String> mapFields = turnListToHashmap(dataList);
        for (Map.Entry<String, String> entry : mapFields.entrySet()) {
            Log.log(Level.FINE, "Entry KEY: " + entry.getKey() + " - VALUE: " + entry.getValue());
            switch (entry.getKey()){
                case "id":{
                    if (!remoteShare.getId().equalsIgnoreCase(entry.getValue())){
                        Log.log(Level.FINE, "ID does not match - Remote: " + remoteShare.getId()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "user":{
                    if (remoteShare.getType().equals("0")) { // private share
                        if (!remoteShare.getShareeName().equalsIgnoreCase(entry.getValue())) {
                            Log.log(Level.FINE, "Sharee does not match - Remote: " + remoteShare.getShareeName()
                                    + " - Expected: " + entry.getValue());
                            return false;
                        }
                    }
                    break;
                }
                case "password":{
                    if (!(remoteShare.getType().equals("3") && remoteShare.hasPassword())) {
                        Log.log(Level.FINE, "Password not present");
                        return false;
                    }
                    break;
                }
                case "name":{
                    if (!remoteShare.getLinkName().equals(entry.getValue())){
                        Log.log(Level.FINE, "Item name does not match - Remote: " + remoteShare.getLinkName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "path":{
                    if (!remoteShare.getItemName().equals(entry.getValue())){
                        Log.log(Level.FINE, "Item path does not match - Remote: " + remoteShare.getItemName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "uid_owner":{
                    if (!remoteShare.getOwner().equalsIgnoreCase(entry.getValue())){
                        Log.log(Level.FINE, "Owner name does not match - Remote: " + remoteShare.getOwner()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "permissions":{
                    if (!remoteShare.getPermissions().equals(entry.getValue())){
                        Log.log(Level.FINE, "Permissions do not match - Remote: " + remoteShare.getPermissions()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "expiration days":{
                    String dateRemote = remoteShare.getExpiration();
                    int expiration = DateUtils.minExpirationDate(
                            OCCapability.getInstance().expirationDateDays(),
                            Integer.valueOf(entry.getValue())
                    );
                    String expDate = DateUtils.dateInDaysWithServerFormat(Integer.toString(expiration));
                    Log.log(Level.FINE, "Expiration dates: Remote: " + dateRemote
                            + " - Expected: " + expDate);
                    if (!dateRemote.equals(expDate)){
                        Log.log(Level.FINE, "Expiration dates do not match");
                        return false;
                    }
                }
            }
        }
        Log.log(Level.FINE, "All fields match. Returning true");
        return true;
    }

    private HashMap<String, String> turnListToHashmap(List<List<String>> dataList){
        HashMap<String, String> mapFields = new HashMap<String, String>();
        for (List<String> rows : dataList) {
            mapFields.put(rows.get(0),rows.get(1));
        }
        return mapFields;
    }
}