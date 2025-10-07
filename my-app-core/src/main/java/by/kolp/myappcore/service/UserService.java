package by.kolp.myappcore.service;

import by.kolp.myappcore.exceptions.NotFoundException;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

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

    public Stream<User> streamAllByUsernameStartingWithIgnoreCase(String username) {
        return userRepository.streamAllByUsernameStartingWithIgnoreCase(username);
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

}
