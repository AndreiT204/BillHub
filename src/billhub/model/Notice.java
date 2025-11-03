package billhub.model;

public class Notice {
    private String id;
    private String providerName;
    private String message;
    private String validFrom;
    private String validTo;

    public Notice(String id, String providerName, String message, String validFrom, String validTo) {
        this.id = id;
        this.providerName = providerName;
        this.message = message;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    @Override public String toString() {
        return "[" + providerName + "] " + message + " (" + validFrom + " -> " + validTo + ")";
    }
}
