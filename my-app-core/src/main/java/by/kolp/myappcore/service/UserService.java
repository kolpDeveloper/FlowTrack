package by.kolp.myappcore.service;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.exceptions.NotFoundException;
import by.kolp.myappcore.mapper.UserMapper;
import by.kolp.myappcore.model.dto.UserRegistrationDTO;
import by.kolp.myappcore.model.dto.UsernameDto;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
       return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<User> streamAllByPrefix(Optional<String> prefixName, Pageable pageable) {
        return userRepository.streamAllByUsernameStartingWithIgnoreCase(prefixName, pageable);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public Page<User> streamAll(Pageable pageable) {
        return userRepository.streamAll(pageable);
    }

    public void deleteById(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    public Page<UserRegistrationDTO> fetchUser(Optional<String> prefixName, Pageable pageable) {
        Page<User> users = (prefixName != null
                ? (streamAllByPrefix(prefixName, pageable))
                : streamAll(pageable));

        log.info(users.toString());
        return users.map(userMapper::toRegistrationDTO);

    }

    public UserRegistrationDTO edit(Integer id, UsernameDto user) {
        log.info("User:{} successfully edited!", user);

        findByUsername(user.username())
                .filter(anotherUser -> !Objects.equals(anotherUser.getId(), id))
                .ifPresent(anotherUser -> {
                    throw new BadRequestException(format("User \"%s\" already exists.", user));
                });

        User newUser = findById(id).orElseThrow(() ->
                new NotFoundException("User not found!"));

        newUser.setUsername(user.username());
        User saved = userRepository.save(newUser);
        return userMapper.toRegistrationDTO(saved);
    }
}
