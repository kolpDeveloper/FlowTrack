package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappsecurity.security.JWTUtil;
import by.kolp.myappservice.service.RegistrationService;
import by.kolp.myappweb.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ModelMapper modelMapper;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(ModelMapper modelMapper, RegistrationService registrationService, JWTUtil jwtUtil, UserValidator userValidator) {
        this.modelMapper = modelMapper;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.userValidator = userValidator;
    }

    @PostMapping("/registration")
    public Map<@NotNull String, @NotNull String> performRegistration(@RequestBody @Valid UserCreatingRequestDTO registration, BindingResult bindingResult) {
        User user = convertToUser(registration);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("ERROR", "This value already exists");
        }

        registrationService.registerUser(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
    }

    public User convertToUser(UserCreatingRequestDTO userCreatingRequestDTO) {
        return this.modelMapper.map(userCreatingRequestDTO, User.class);
    }
}
