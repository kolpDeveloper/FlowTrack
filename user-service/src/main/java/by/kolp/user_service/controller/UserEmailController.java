package by.kolp.user_service.controller;

import by.kolp.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserEmailController {

    public static final String GET_INTERNAL_EMAILS = "/api/internal/user/emails";
    private final UserService userService;

    @GetMapping(GET_INTERNAL_EMAILS)
    public Page<String> getAllEmails(Pageable pageable) {
        return userService.findAllEmails(pageable);
    }
}
