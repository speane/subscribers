package dev.eshilov.subscribers.action;

public interface ActionService {

    ActionResponse call(String number);
    ActionResponse sms(String number);
}
