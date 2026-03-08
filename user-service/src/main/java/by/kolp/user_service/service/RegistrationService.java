package by.kolp.user_service.service;


import by.kolp.user_service.mapper.UserMapper;
import by.kolp.user_service.model.dto.UserCreatingRequestDTO;
import by.kolp.user_service.model.dto.UserRegistrationDTO;
import by.kolp.user_service.model.entity.User;
import by.kolp.user_service.repository.interfaces.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Transactional
    @CircuitBreaker(name = "UserBackend")
    public UserRegistrationDTO register(UserCreatingRequestDTO request) {

        User newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(newUser);
        log.info("User:{} successfully registered!", saved.getUsername());
        return userMapper.toRegistrationDTO(saved);
    }
}
