/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.support.shares;

import java.util.Map;
import java.util.logging.Level;

import e2e.model.OCShare;
import e2e.support.date.DateUtils;
import e2e.support.log.Log;

public class ShareUtils {

    public static String permissionToInt(String permission) {
        Log.log(Level.FINE, "Permission to translate: " + permission);
        return switch (permission) {
            case "Viewer" -> "17";
            case "Editor" -> "15";
            case "Uploader" -> "4";
            case "Contributor" -> "5";
            default -> "";
        };
    }

    private static String permissionFromInt(String permission) {
        Log.log(Level.FINE, "Permission to translate: " + permission);
        return switch (permission) {
            case "1" -> "Viewer";
            case "3", "15" -> "Editor";
            case "4" -> "Secret";
            case "5" -> "Contributor";
            default -> "";
        };
    }

    public static boolean checkCorrectShare(OCShare remoteShare, Map<String, String> dataList) {
        Log.log(Level.FINE, "Starts: Check correct share");
        for (Map.Entry<String, String> entry : dataList.entrySet()) {
            Log.log(Level.FINE, "Entry KEY: " + entry.getKey() + " - VALUE: " + entry.getValue());
            switch (entry.getKey()) {
                case "id" -> {
                    if (!remoteShare.getId().equalsIgnoreCase(entry.getValue())) {
                        Log.log(Level.FINE, "ID does not match - Remote: " + remoteShare.getId()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                }
                case "group", "user" -> {
                    if (remoteShare.getType().equals("0")) {
                        if (!remoteShare.getShareeName().equalsIgnoreCase(entry.getValue())) {
                            Log.log(Level.FINE, "Sharee does not match - Remote: " + remoteShare.getShareeName()
                                    + " - Expected: " + entry.getValue());
                            return false;
                        }
                    }
                }
                case "password" -> {
                    if (!(remoteShare.getType().equals("3") && remoteShare.hasPassword())
                            && !entry.getValue().equals("\"\"")) {
                        Log.log(Level.FINE, "Password not present");
                        return false;
                    }
                }
                case "name" -> {
                    if (!remoteShare.getLinkName().equals(entry.getValue())) {
                        Log.log(Level.FINE, "Item name does not match - Remote: " + remoteShare.getLinkName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                }
                case "path" -> {
                    if (!remoteShare.getItemName().equals(entry.getValue())) {
                        Log.log(Level.FINE, "Item path does not match - Remote: " + remoteShare.getItemName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                }
                case "uid_owner" -> {
                    if (!remoteShare.getOwner().equalsIgnoreCase(entry.getValue())) {
                        Log.log(Level.FINE, "Owner name does not match - Remote: " + remoteShare.getOwner()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                }
                case "permission" -> {
                    String remotePermission = permissionFromInt(remoteShare.getPermissions());
                    if (!remotePermission.equals(entry.getValue())) {
                        Log.log(Level.FINE, "Permissions do not match - Remote: " + remotePermission
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                }
                case "expiration" -> {
                    String expirationDay = entry.getValue();
                    Log.log(Level.FINE, "Checking remote expiration: Get day: " + expirationDay);
                    if (!expirationDay.equals("0")) {
                        String remoteDate = remoteShare.getExpiration();
                        Log.log(Level.FINE, "Expiration date remote: " + remoteDate);
                        String timestamp = remoteDate.substring(11);
                        String remoteDateTZ = DateUtils.getCorrectTZ(remoteDate);
                        Log.log(Level.FINE, "Expiration date remote moved to TZ: " + remoteDateTZ);
                        String localDate = DateUtils.dateInDaysWithServerFormat(
                                Integer.valueOf(expirationDay), timestamp);
                        Log.log(Level.FINE, "Expiration dates: Remote: " + remoteDateTZ
                                + " - Local: " + localDate);
                        if (!remoteDateTZ.equals(localDate)) {
                            Log.log(Level.FINE, "Expiration dates do not match");
                            return false;
                        }
                    } else {
                        if (remoteShare.getExpiration() != null && !remoteShare.getExpiration().isEmpty()) {
                            Log.log(Level.FINE, "Expiration date not expected: " + remoteShare.getExpiration());
                            return false;
                        }
                    }
                }
            }
        }
        Log.log(Level.FINE, "All fields match. Returning true");
        return true;
    }
}
