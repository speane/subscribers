package dev.eshilov.subscribers.action;

public enum PreconditionError {

    BLOCKED("The subscriber is blocked"),
    NEGATIVE_BALANCE("The subscriber has negative balance"),
    EXCEEDED_MAX_CALL_COUNT("The subscriber has exceeded max call count");

    PreconditionError(String description) {
        this.description = description;
    }

    private final String description;

    public String getDescription() {
        return description;
    }
}
