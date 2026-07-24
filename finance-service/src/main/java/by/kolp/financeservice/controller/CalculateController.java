package by.kolp.financeservice.controller;


import by.kolp.financeservice.dto.NumericDataEntryDTO;
import by.kolp.financeservice.service.NumericDataEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CalculateController {

    private static final String CREATE_VALUE = "/api/value";
    private static final String DELETE_VALUE = "/api/value/{id}";
    private static final String GET_VALUE = "/api/value/sum";
    private static final String GET_BY_USER_ID = "/api/value/user/{userId}";
    private final NumericDataEntryService numericDataEntryService;

    @GetMapping(GET_BY_USER_ID)
    @ResponseStatus(HttpStatus.OK)
    public List<NumericDataEntryDTO> getByUserId(@PathVariable UUID userId) {
        log.info("Getting numeric data entries for user with id {}", userId);
        return numericDataEntryService.findByUserId(userId);
    }

    @PostMapping(CREATE_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create_value(@RequestBody @Valid NumericDataEntryDTO numericDataEntryDTO) {
        log.info("Creating new value for {}", numericDataEntryDTO);
        numericDataEntryService.createNumericDataEntry(numericDataEntryDTO);
    }

    @DeleteMapping(DELETE_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete_value(@PathVariable UUID id) {
        numericDataEntryService.deleteById(id);
    }

    @GetMapping(GET_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Long getTotalSum() {
        log.info("Getting total sum");
        return numericDataEntryService.getTotalSum();
    }
}