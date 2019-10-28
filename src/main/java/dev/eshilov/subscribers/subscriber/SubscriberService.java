package dev.eshilov.subscribers.subscriber;

import java.util.Optional;

public interface SubscriberService {

    Optional<Subscriber> findByNumber(String number);
    Subscriber getByNumber(String number);
}
