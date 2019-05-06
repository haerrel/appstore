package base.appstore.service;

import base.appstore.model.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenService {

    private String secret;
    private Long expiration;

    public JwtTokenService(@Value("${jwt.secret}") String secret,
                           @Value("${jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(Account account) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_"+ account.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(account.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 10000);
    }
}
