package by.kolp.myappservice.service;


import by.kolp.myappcore.model.entity.Role;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.RoleRepository;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static by.kolp.myappcore.model.enums.RoleName.ROLE_ADMIN;
import static by.kolp.myappcore.model.enums.RoleName.ROLE_USER;


@Component
public class RegistrationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private Role defaultUserRole;
    private Role adminRole;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void register(User user, boolean isAdmin) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(isAdmin ? adminRole : defaultUserRole);
        userRepository.save(user);
    }

    @PostConstruct
    private void initialize() {
        this.defaultUserRole = roleRepository.findByName(ROLE_USER)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(ROLE_USER);
                    return roleRepository.save(newRole);
                });

        this.adminRole = roleRepository.findByName(ROLE_ADMIN)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(ROLE_ADMIN);
                    return roleRepository.save(newRole);
                });
    }

    @Transactional
    public void registerUser(User user) {
        register(user, false);
    }

    /*@Transactional
    public User registerAdmin(User user) {
       return register(user, true);
    }*/
}
