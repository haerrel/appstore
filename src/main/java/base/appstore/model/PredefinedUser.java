package base.appstore.model;

public enum PredefinedUser {
    ADMIN("DemoAdmin", Constants.DEMO_PW),
    DEVELOPER("DemoDeveloper",Constants.DEMO_PW),
    USER("DemoUser",Constants.DEMO_PW);

    private final String username;
    private final String password;

    PredefinedUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private static class Constants {
        public static final String DEMO_PW = "DemoPassword";
    }
}
