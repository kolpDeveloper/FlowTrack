package by.kolp.myappcore.repository.interfaces;

import by.kolp.myappcore.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Override
    <S extends User> S save(S entity);
}