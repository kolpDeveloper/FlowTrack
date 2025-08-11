package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import by.kolp.myappdataapi.dto.UserCreatingRequestDTO;
import by.kolp.myappsecurity.security.JWTUtil;
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

    private ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/registration")
    public Map<@NotNull String, @NotNull String> performRegistration(@RequestBody @Valid UserCreatingRequestDTO registration, BindingResult bindingResult) {
        User user = convertToUser(registration);


        if (bindingResult.hasErrors()) {
            return Map.of("ERROR", "This value already exists");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Map.of("ERROR", "User already exists");
        }

        //todo registration service implementation
        //registrationService.registerUser(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
    }

    public User convertToUser(UserCreatingRequestDTO userCreatingRequestDTO) {
        return this.modelMapper.map(userCreatingRequestDTO, User.class);
    }
}
