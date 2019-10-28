package dev.eshilov.subscribers.action;

import dev.eshilov.subscribers.subscriber.Subscriber;
import dev.eshilov.subscribers.subscriber.SubscriberService;
import dev.eshilov.subscribers.subscriber.SubscriberStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private static final Function<Subscriber, Optional<PreconditionError>> NO_CHECK =
            subscriber -> Optional.empty();

    private final SubscriberService subscriberService;
    private final ActionBusinessConfig businessConfig;

    @Override
    public ActionResponse call(String number) {
        return doAction(number, this::checkNotExceededMaxCallCount, this::makeCall);
    }

    @Override
    public ActionResponse sms(String number) {
        return doAction(number, NO_CHECK, this::sendSms);
    }

    private ActionResponse doAction(String number,
                                    Function<Subscriber, Optional<PreconditionError>> preconditionCheck,
                                    Consumer<Subscriber> action) {

        Subscriber subscriber = subscriberService.getByNumber(number);
        Optional<PreconditionError> error = checkActionPreconditions(subscriber)
                .or(() -> preconditionCheck.apply(subscriber));

        if (error.isEmpty()) {
            action.accept(subscriber);
            return buildSuccessResponse();
        } else {
            return buildErrorResponse(error.get());
        }
    }

    private Optional<PreconditionError> checkActionPreconditions(Subscriber subscriber) {
        return checkNonBlocked(subscriber)
                .or(() -> checkNonNegativeBalance(subscriber));
    }

    private Optional<PreconditionError> checkNonBlocked(Subscriber subscriber) {
        if (isBlocked(subscriber)) {
            return Optional.empty();
        } else {
            return Optional.of(PreconditionError.BLOCKED);
        }
    }

    private boolean isBlocked(Subscriber subscriber) {
        return subscriber.getStatus() == SubscriberStatus.BLOCKED;
    }

    private Optional<PreconditionError> checkNonNegativeBalance(Subscriber subscriber) {
        if (hasNegativeBalance(subscriber)) {
            return Optional.of(PreconditionError.NEGATIVE_BALANCE);
        } else {
            return Optional.empty();
        }
    }

    private boolean hasNegativeBalance(Subscriber subscriber) {
        return subscriber.getBalance() < 0;
    }

    private Optional<PreconditionError> checkNotExceededMaxCallCount(Subscriber subscriber) {
        if (isExceededMaxCallCount(subscriber)) {
            return Optional.of(PreconditionError.EXCEEDED_MAX_CALL_COUNT);
        } else {
            return Optional.empty();
        }
    }

    private boolean isExceededMaxCallCount(Subscriber subscriber) {
        return subscriber.getCallCount() >= businessConfig.getMaxCallCount();
    }

    private ActionResponse buildErrorResponse(PreconditionError error) {
        return new ActionResponse(error);
    }

    private ActionResponse buildSuccessResponse() {
        return new ActionResponse(null);
    }

    private void makeCall(Subscriber subscriber) {

    }

    private void sendSms(Subscriber subscriber) {

    }
}
