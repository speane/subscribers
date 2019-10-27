package dev.eshilov.subscribers.action;

import dev.eshilov.subscribers.subscriber.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final SubscriberService subscriberService;

    @Override
    public void call(String number) {
        System.out.println(subscriberService.findByNumber(number));
        log.info("Call by number {}", number);
    }

    @Override
    public void sms(String number) {
        log.info("SMS by number {}", number);
    }
}
