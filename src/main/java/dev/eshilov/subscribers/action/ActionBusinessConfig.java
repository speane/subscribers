package dev.eshilov.subscribers.action;

import org.springframework.stereotype.Component;

@Component
public class ActionBusinessConfig {

    private static final int MAX_CALL_COUNT = 5;
    private static final long CALL_COST = 100;
    private static final long SMS_COST = 100;

    public int getMaxCallCount() {
        return MAX_CALL_COUNT;
    }

    public long getCallCost() {
        return CALL_COST;
    }

    public long getSmsCost() {
        return SMS_COST;
    }
}
