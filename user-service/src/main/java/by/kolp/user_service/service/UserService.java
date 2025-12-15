package by.kolp.user_service.service;


import by.kolp.commonexceptions.exceptions.BadRequestException;
import by.kolp.commonexceptions.exceptions.NotFoundException;
import by.kolp.user_service.mapper.UserMapper;
import by.kolp.user_service.model.dto.UserRegistrationDTO;
import by.kolp.user_service.model.dto.UsernameDto;
import by.kolp.user_service.model.entity.User;
import by.kolp.user_service.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

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

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<User> streamAllByPrefix(String prefixName, Pageable pageable) {
        return userRepository.streamAllByUsernameStartingWithIgnoreCase(prefixName, pageable);
    }

    public Page<User> streamAll(Pageable pageable) {
        return userRepository.streamAll(pageable);
    }

    public void deleteById(Integer id) {
        User user = findById(id)
                .orElseThrow(() -> new NotFoundException(format("User not found with id %d", id)));

        if (user.isAdmin()) {
            throw new BadRequestException(format("Cannot delete admin user", user));
        }
        userRepository.deleteById(user.getId());
    }

    public Page<UserRegistrationDTO> fetchUser(String prefixName, Pageable pageable) {
        Page<User> users = Optional.ofNullable(prefixName)
                .filter(prefix -> !prefix.isBlank())
                .map(prefix -> streamAllByPrefix(prefix, pageable))
                .orElse(streamAll(pageable));

        log.info(users.toString());
        return users.map(userMapper::toRegistrationDTO);
    }

    @Transactional
    public UserRegistrationDTO edit(Integer id, UsernameDto user) {
        log.info("User:{} successfully edited!", user);

        findByUsername(user.username())
                .filter(anotherUser -> !Objects.equals(anotherUser.getId(), id))
                .ifPresent(anotherUser -> {
                    throw new BadRequestException(format("User \"%s\" already exists.", anotherUser.getUsername()));
                });

        User newUser = findById(id).orElseThrow(() ->
                new NotFoundException("User not found!"));

        newUser.setUsername(user.username());
        User savedUser = userRepository.save(newUser);
        return userMapper.toRegistrationDTO(savedUser);
    }

    public Page<String> findAllEmails(Pageable pageable) {
        return userRepository.findAllEmails(pageable);
    }
}
