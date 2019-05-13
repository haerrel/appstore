package base.appstore.controller;

public class AppNotFoundException extends RuntimeException {
  AppNotFoundException(Long id) {
    super("Could not find app " + id);
  }
}
