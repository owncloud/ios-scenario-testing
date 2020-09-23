package utils.entities;

import java.util.HashMap;

public class OCCapability {

    private static OCCapability capabilities;

    private HashMap<String, String> publicLink;


    private boolean expirationDateEnforced;
    private int expirationDateDays;

    private boolean passwordEnforcedReadOnly;
    private boolean passwordEnforcedReadWriteDelete;
    private boolean passwordEnforcedUploadOnly;

    private boolean passwordEnforced;


    private OCCapability(){
        publicLink = new HashMap<>();
    }

    public static OCCapability getInstance(){
        if (capabilities == null){
            capabilities = new OCCapability();
        }
        return capabilities;
    }

    public boolean isExpirationDateEnforced() {
        return expirationDateEnforced;
    }

    public void setExpirationDateEnforced(boolean expirationDateEnforced) {
        this.expirationDateEnforced = expirationDateEnforced;
    }

    public int expirationDateDays() {
        return expirationDateDays;
    }

    public void setExpirationDateDays(int expirationDateDays) {
        this.expirationDateDays = expirationDateDays;
    }

    public boolean isPasswordEnforcedReadOnly() {
        return passwordEnforcedReadOnly;
    }

    public void setPasswordEnforcedReadOnly(boolean passwordEnforcedReadOnly) {
        this.passwordEnforcedReadOnly = passwordEnforcedReadOnly;
    }

    public boolean isPasswordEnforcedReadWriteDelete() {
        return passwordEnforcedReadWriteDelete;
    }

    public void setPasswordEnforcedReadWriteDelete(boolean passwordEnforcedReadWriteDelete) {
        this.passwordEnforcedReadWriteDelete = passwordEnforcedReadWriteDelete;
    }

    public boolean isPasswordEnforcedUploadOnly() {
        return passwordEnforcedUploadOnly;
    }

    public void setPasswordEnforcedUploadOnly(boolean passwordEnforcedUploadOnly) {
        this.passwordEnforcedUploadOnly = passwordEnforcedUploadOnly;
    }

    public boolean isPasswordEnforced() {
        return passwordEnforced;
    }

    public void setPasswordEnforced(boolean passwordEnforced) {
        this.passwordEnforced = passwordEnforced;
    }
}