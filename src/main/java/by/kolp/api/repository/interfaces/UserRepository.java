package by.kolp.api.repository.interfaces;

import by.kolp.api.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long integer);

    Stream<User> streamAllByUsernameStartingWithIgnoreCase(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteUserBy(Long id);


   @Query("SELECT u FROM User u")
    Stream<User> streamAll();
}