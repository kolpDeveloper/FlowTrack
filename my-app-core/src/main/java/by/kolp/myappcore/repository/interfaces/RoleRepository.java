package by.kolp.myappcore.repository.interfaces;

import by.kolp.myappcore.model.entity.Role;
import by.kolp.myappcore.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName role);
}
