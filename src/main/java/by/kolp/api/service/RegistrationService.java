package by.kolp.api.service;


import by.kolp.api.model.entity.Role;
import by.kolp.api.model.entity.User;
import by.kolp.api.repository.interfaces.RoleRepository;
import by.kolp.api.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static by.kolp.api.model.enums.RoleName.ROLE_ADMIN;
import static by.kolp.api.model.enums.RoleName.ROLE_USER;


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
    public User register(User user, boolean isAdmin) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(isAdmin ? adminRole : defaultUserRole);
        return userRepository.save(user);
    }



    private void initialize(){
        this.defaultUserRole = roleRepository.findByName(ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default user role not found"));
        this.adminRole = roleRepository.findByName(ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
    }


    @Transactional
    public User registerUser(User user) {
        return register(user, false);
    }

    @Transactional
    public User registerAdmin(User user) {
       return register(user, true);
    }
}

