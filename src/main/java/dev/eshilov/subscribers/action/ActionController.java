package dev.eshilov.subscribers.action;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/action")
@RequiredArgsConstructor
public class ActionController {

    private final ActionService actionService;

    @GetMapping("/demo")
    public String demo() {
        return "Demo";
    }

    @PostMapping("/call")
    public void call(Principal principal) {
        actionService.call(principal.getName());
    }

    @PostMapping("/sms")
    public void sms(Principal principal) {
        actionService.sms(principal.getName());
    }
}
