package base.appstore;

public enum Role {
    ADMIN("DemoAdmin","DemoPassword");

    private final String username;
    private final String password;

    Role(String username, String password) {
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
