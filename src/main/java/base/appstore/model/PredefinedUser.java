package base.appstore.model;

public enum PredefinedUser {
    ADMIN("DemoAdmin","DemoPassword"),
    DEVELOPER("DemoDeveloper","DemoPassword"),
    USER("DemoUser","DemoPassword");

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
}
