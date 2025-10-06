package by.kolp.myappcore.repository.interfaces;

import by.kolp.myappcore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Stream<User> streamAllByUsernameStartingWithIgnoreCase(String username);

    @Override
    void deleteById(Integer integer);

    @Query("SELECT u FROM User u")
    Stream<User> streamAll();

    @Query("select u.email from User u")
    List<String> findAllEmails();

    @Override
    <S extends User> S save(S entity);
}