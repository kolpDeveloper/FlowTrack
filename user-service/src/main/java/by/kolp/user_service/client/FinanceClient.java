package by.kolp.user_service.client;

import by.kolp.user_service.model.dto.NumericDataEntryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "finance-service")
public interface FinanceClient {

    @GetMapping("/api/value/user/{userId}")
    List<NumericDataEntryDTO> getUserFinances(@PathVariable("userId") UUID userId);
}