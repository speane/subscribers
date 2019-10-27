package dev.eshilov.subscribers.auth;

import dev.eshilov.subscribers.subscriber.Subscriber;
import dev.eshilov.subscribers.subscriber.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenHelper tokenHelper;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        var userDetails = tryToAuthenticate(request);
        var token = tokenHelper.generateToken(userDetails);
        return new AuthResponse(token);
    }

    private UserDetails tryToAuthenticate(AuthRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(
                request.getNumber(),
                request.getPin());

        authenticationManager.authenticate(authentication);
        return userDetailsService.loadUserByUsername(request.getNumber());
    }
}
