package dev.eshilov.subscribers.action;

public interface ActionService {

    void call(String number);
    void sms(String number);
}
