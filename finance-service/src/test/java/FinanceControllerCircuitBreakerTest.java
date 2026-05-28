import by.kolp.financeservice.FinanceApplicationRunner;
import by.kolp.financeservice.dto.CategoryNameDTO;
import by.kolp.financeservice.dto.NumericDataEntryDTO;
import by.kolp.financeservice.repository.NumericDataEntryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FinanceApplicationRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FinanceControllerCircuitBreakerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @MockBean
    private NumericDataEntryRepository numericDataEntryRepository;

    @BeforeEach
    void setUp() {
        circuitBreakerRegistry.circuitBreaker("financeBackend").transitionToClosedState();
    }

    @Test
    void testCircuitBreakerOpenAndFallback() throws Exception {
        when(numericDataEntryRepository.save(any())).thenThrow(new RuntimeException("DB connection error"));

        CategoryNameDTO c = new CategoryNameDTO("Qwerty");
        NumericDataEntryDTO dataEntryDTO = new NumericDataEntryDTO(UUID.randomUUID(), "1", BigDecimal.valueOf(123.456789), Instant.now(), c);

        mockMvc.perform(post("/api/value")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dataEntryDTO))).andExpect(status().isServiceUnavailable());

        verify(numericDataEntryRepository, times(1)).save(any());
    }
}
