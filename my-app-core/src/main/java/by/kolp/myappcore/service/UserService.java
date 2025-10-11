package by.kolp.myappcore.service;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.exceptions.NotFoundException;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow  (() ->
                new NotFoundException("User \"%s\" doesn't exist"));
    }

    public User save(User user) {
       return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<User> findAllByPrefix(String prefixName, Pageable pageable) {
        return userRepository.streamAllByUsernameStartingWithIgnoreCase(prefixName, pageable);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Stream<User> streamAll() {
        return userRepository.streamAll();
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User edit(Integer id, String user) {

        if (user.isBlank()) {
            throw new BadRequestException("Username cannot be empty");
        }

        findByUsername(user)
                .filter(anotherUser -> !Objects.equals(anotherUser.getId(), id))
                .ifPresent(anotherUser -> {
                    throw new BadRequestException(format("User \"%s\" already exists.", user));
                });

        User newUser = findById(id);
        newUser.setUsername(user);

        return saveAndFlush(newUser);
    }

}
