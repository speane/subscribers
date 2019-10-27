package dev.eshilov.subscribers.action;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ActionServiceImpl implements ActionService {

    @Override
    public void call(String number) {
        log.info("Call by number {}", number);
    }

    @Override
    public void sms(String number) {
        log.info("SMS by number {}", number);
    }
}
