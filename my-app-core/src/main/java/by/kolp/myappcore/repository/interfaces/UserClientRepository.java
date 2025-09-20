package by.kolp.myappcore.repository.interfaces;


import by.kolp.myappcore.model.entity.User;

import java.util.Optional;

public interface UserClientRepository {
    Optional<User> getUserById(Long id);

    Optional<User> findUserByUsername(String username);
}
