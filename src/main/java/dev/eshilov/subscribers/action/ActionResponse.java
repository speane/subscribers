package dev.eshilov.subscribers.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ActionResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PreconditionError error;
}
