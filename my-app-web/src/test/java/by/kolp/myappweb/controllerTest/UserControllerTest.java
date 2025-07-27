package by.kolp.myappweb.controllerTest;


import by.kolp.myappcore.model.dto.UserCreatingRequestDTO;
import by.kolp.myappcore.model.entity.User;
import by.kolp.myappcore.repository.interfaces.UserRepository;
import by.kolp.myappweb.TestApplication;
import by.kolp.myappweb.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUser() throws Exception {
        UserCreatingRequestDTO request = UserCreatingRequestDTO.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .build();

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"));
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
}