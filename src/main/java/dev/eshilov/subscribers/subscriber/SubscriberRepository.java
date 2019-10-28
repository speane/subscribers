package dev.eshilov.subscribers.subscriber;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Optional<Subscriber> findByNumber(String number);

    Subscriber getByNumber(String number);
}
