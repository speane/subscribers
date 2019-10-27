package dev.eshilov.subscribers.security;

import dev.eshilov.subscribers.subscriber.Subscriber;
import dev.eshilov.subscribers.subscriber.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriberUserDetailsService implements UserDetailsService {

    private final SubscriberService subscriberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return subscriberService.findByNumber(username)
                .map(this::buildUserDetailsOfSubscriber)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private UserDetails buildUserDetailsOfSubscriber(Subscriber subscriber) {
        return new User(
                subscriber.getNumber(),
                subscriber.getPin(),
                List.of(new SimpleGrantedAuthority(subscriber.getRole().toString())));
    }
}
