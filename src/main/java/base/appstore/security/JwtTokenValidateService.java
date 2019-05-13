package base.appstore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenValidateService {
    private String secret;

    @Autowired
    public JwtTokenValidateService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String getUsernameFromToken(String token) {

        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {

        return getAllClaimsFromToken(token).getExpiration();
    }

    public Claims getAllClaimsFromToken(String token) {
        final String base64secret = Base64.getEncoder().encodeToString(secret.getBytes());
        return Jwts.parser()
                .setSigningKey(base64secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenNotExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.after(new Date());
    }


    public Optional<Boolean> validateToken_opt(String token) {
        return isTokenNotExpired(token) ? Optional.of(Boolean.TRUE) : Optional.empty();
    }

    public Boolean validateToken(String token) {
        return isTokenNotExpired(token);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(final String token) {

        final Claims claims = getAllClaimsFromToken(token);

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return authorities;
    }
}
