/*
package by.kolp.myappweb.controllerTest;


import by.kolp.myappdataapi.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.model.entity.Role;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.model.enums.RoleName;
import by.kolp.myappcore.repository.interfaces.RoleRepository;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import by.kolp.myappweb.TestApplication;
import by.kolp.myappweb.controller.UserController;
import by.kolp.myappweb.factories.UserRegistrationDtoFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {


    @MockitoBean
    private UserRegistrationDtoFactory userRegistrationDtoFactory;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        Mockito.reset(userRegistrationDtoFactory, passwordEncoder, roleRepository);

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");
        Role defaultRole = new Role();
        defaultRole.setId(100);
        defaultRole.setName(RoleName.ROLE_USER);
        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(defaultRole));
        Mockito.when(roleRepository.save(any(Role.class))).thenReturn(defaultRole); // Если роль создается
    }

    @Test
    void testGetUser() throws Exception {
        UserCreatingRequestDTO request = UserCreatingRequestDTO.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .email(request.getEmail())
                .username(request.getUsername())
                .password("encodedPassword")
                .role(new Role())
                .build();

        Mockito.when(userRepository.save(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.password").value("encodedPassword"))
                .andExpect(jsonPath("$.role").value(RoleName.ROLE_USER.toString()));
    }

    @Test
    void testDeleteUser() throws Exception {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        assertEquals(userId, userRepository.findById(userId).get().getId());

        mockMvc.perform(delete("/api/user/" + userId))
                .andExpect(status().isOk());

        Mockito.verify(userRepository).deleteById(userId);
    }

    void testUpdateUser() throws Exception {



    }
}*/
