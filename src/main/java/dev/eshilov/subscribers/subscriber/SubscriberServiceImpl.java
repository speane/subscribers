package dev.eshilov.subscribers.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Override
    public Optional<Subscriber> findByNumber(String number) {
        return subscriberRepository.findByNumber(number);
    }

    @Override
    public Subscriber getByNumber(String number) {
        return subscriberRepository.getByNumber(number);
    }
}
