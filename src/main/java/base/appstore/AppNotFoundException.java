package base.appstore;

public class AppNotFoundException extends RuntimeException {
  AppNotFoundException(Long id) {
    super("Could not find app " + id);
  }
}
