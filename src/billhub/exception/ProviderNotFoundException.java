package billhub.exception;

public class ProviderNotFoundException extends Exception {
    public ProviderNotFoundException(String name) {
        super("Provider not found: " + name);
    }
}
