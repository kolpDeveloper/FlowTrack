package by.kolp.myappcore.repository.interfaces;


import by.kolp.myappcore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserClientRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long id);

    Optional<User> findUserByUsername(String username);
}
