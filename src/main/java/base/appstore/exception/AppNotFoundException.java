package base.appstore.exception;

public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(Long id) {
        super("Could not find app " + id);
    }
}
