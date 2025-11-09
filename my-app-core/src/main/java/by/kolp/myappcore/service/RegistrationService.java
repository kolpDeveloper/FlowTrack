package by.kolp.myappcore.service;

import by.kolp.myappcore.mapper.UserMapper;
import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.model.dto.UserRegistrationDTO;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
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
    public UserRegistrationDTO register(UserCreatingRequestDTO request) {
        User newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(newUser);
        log.info("User:{} successfully registered!", saved.getUsername());
        return userMapper.toRegistrationDTO(saved);
    }
}
