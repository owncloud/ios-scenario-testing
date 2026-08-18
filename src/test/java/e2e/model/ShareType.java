package e2e.model;

public enum ShareType {
    PRIVATE("0"),
    PUBLIC_LINK("3"),
    UNKNOWN("");

    private final String apiValue;

    ShareType(String apiValue) {
        this.apiValue = apiValue;
    }

    public static ShareType from(String apiValue) {
        for (ShareType t : values()) {
            if (t.apiValue.equals(apiValue)) return t;
        }
        return UNKNOWN;
    }
}
