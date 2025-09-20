package by.kolp.myappcore.service;

import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestUserClientService {

    private final UserClientRepository userClientRepository;

    public Optional<User> getUserById(Long id) {
        return userClientRepository.getUserById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userClientRepository.findUserByUsername(username);
    }

}

//todo rest client