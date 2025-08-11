package by.kolp.myappservice.service;


import by.kolp.myappservice.serviceDto.UserRegistrationRequest;


public interface RegistrationService {

    void registerUser(UserRegistrationRequest user);
    void register(UserRegistrationRequest user, boolean isAdmin);
    /*private final UserRepository userRepository;
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
*/


    /*@Transactional
    public void register(UserResponseDTO user, boolean isAdmin) {
        user.password(passwordEncoder.encode(user.password()));
        user.ole(isAdmin ? adminRole : defaultUserRole);
        userRepository.save(user);
    }*/

    /*@PostConstruct
    private void initialize() {
        this.defaultUserRole = roleRepository.findByName()
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
    }*/

    /*@Transactional
    public void registerUser(User user) {
        register(user, false);
    }*/

    /*@Transactional
    public User registerAdmin(User user) {
       return register(user, true);
    }*/
}
