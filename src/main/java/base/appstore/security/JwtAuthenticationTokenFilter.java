package base.appstore.security;


import base.appstore.exception.JwtAuthenticationException;
import com.sun.javafx.scene.control.behavior.OptionalBoolean;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;
    private final JwtTokenValidateService jwtService;
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Autowired
    public JwtAuthenticationTokenFilter(JwtTokenValidateService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(this.tokenHeader);

        try {
            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                String token = requestHeader.substring(7);

                if (token != null){
                    String username = jwtService.getUsernameFromToken(token);
                    Collection<? extends GrantedAuthority> authorities;
                    authorities = jwtService.getAuthorities(token);

                    if (jwtService.validateToken(token)) {
                        JwtAuthenticatedProfile authentication = new JwtAuthenticatedProfile(username, token, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }catch (JwtException ex) {
                log.error(String.format("Invalid JWT Token: %s", ex.getMessage()));
                throw new JwtAuthenticationException("Failed to verify token");
            }
        chain.doFilter(request, response);
    }
}
