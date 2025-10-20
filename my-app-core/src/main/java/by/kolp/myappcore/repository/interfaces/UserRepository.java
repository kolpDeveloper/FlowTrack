package by.kolp.myappcore.repository.interfaces;

import by.kolp.myappcore.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Page<User> streamAllByUsernameStartingWithIgnoreCase(String username, Pageable pageable);

    @Override
    void deleteById(Integer integer);

    @Query("SELECT u FROM User u")
    Page<User> streamAll(Pageable pageable);

    @Override
    <S extends User> S save(S entity);
}