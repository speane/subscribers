package dev.eshilov.subscribers.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TokenHelper {

    private static final int TOKEN_DURATION_MILLIS = 1 * 60 * 1000;
    private static final String SECRET_KEY = "secret";
    private static final String ROLE_CLAIM = "rol";

    public String generateToken(UserDetails userDetails) {
        Date issuedAt = new Date();
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .claim(ROLE_CLAIM, convertAuthoritiesToRole(userDetails.getAuthorities()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(new Date(issuedAt.getTime() + TOKEN_DURATION_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public UserDetails parseToken(String token) {
        var parsed = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
        var username = parsed.getBody().getSubject();
        var role = (String) parsed.getBody().get(ROLE_CLAIM);
        var authorities = convertRoleToAuthorities(role);
        return new User(username, "", authorities);
    }

    private String convertAuthoritiesToRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.iterator().next().getAuthority();
    }

    private Collection<? extends GrantedAuthority> convertRoleToAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
