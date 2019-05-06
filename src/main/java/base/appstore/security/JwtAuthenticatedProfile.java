package base.appstore.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class JwtAuthenticatedProfile implements Authentication {

    private final String username;
    private final String token;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticatedProfile(String username, String token) {

        this.username = username;
        this.token = token;
        this.authorities = new ArrayList<>();
    }

    public JwtAuthenticatedProfile(String username, String token, Collection<? extends GrantedAuthority> authorities) {

        this.username = username;
        this.token = token;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return username;
    }
}

