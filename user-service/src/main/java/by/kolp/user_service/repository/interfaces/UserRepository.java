package by.kolp.user_service.repository.interfaces;


import by.kolp.user_service.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Page<User> streamAllByUsernameStartingWithIgnoreCase(String username, Pageable pageable);

    void deleteById(UUID id);

    Optional<User> findById(UUID integer);

    @Query("SELECT u FROM User u")
    Page<User> streamAll(Pageable pageable);

    @Override
    <S extends User> S save(S entity);

    @Query(
            value = "select u.email from User u",
            countQuery = "select count(u) from User u"
    )
    Page<String> findAllEmails(Pageable pageable);

}