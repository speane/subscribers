package dev.eshilov.subscribers.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenHelper tokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        var header = request.getHeader(TOKEN_HEADER);
        if (isValidTokenHeader(header)) {
            var token = extractTokenFromHeader(header);
            var userDetails = tokenHelper.parseToken(token);
            var authentication = buildAuthenticationFromUserDetails(userDetails);

            setContextAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private boolean isValidTokenHeader(String header) {
        return !StringUtils.isEmpty(header) && header.startsWith(TOKEN_PREFIX);
    }

    private String extractTokenFromHeader(String header) {
        return header.substring(TOKEN_PREFIX.length());
    }

    private Authentication buildAuthenticationFromUserDetails(UserDetails details) {
        return new UsernamePasswordAuthenticationToken(
                details.getUsername(),
                null,
                details.getAuthorities());
    }

    private void setContextAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
