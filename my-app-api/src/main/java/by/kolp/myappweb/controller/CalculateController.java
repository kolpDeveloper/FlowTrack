package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.service.NumericDataEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CalculateController {

    private final NumericDataEntryService numericDataEntryService;

    private static final String CREATE_VALUE = "/api/value";
    private static final String DELETE_VALUE = "/api/value/{id}";


    @PostMapping(CREATE_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create_value(@RequestBody @Valid NumericDataEntryDTO numericDataEntryDTO) {
        log.info("Creating new value for {}", numericDataEntryDTO);
        numericDataEntryService.createNumericDataEntry(numericDataEntryDTO);
    }

    @DeleteMapping(DELETE_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete_value(@PathVariable Long id) {
        numericDataEntryService.deleteById(id);
    }

    @GetMapping("/api/value/sum")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Long getTotalSum() {
        log.info("Getting total sum");
        return numericDataEntryService.getTotalSum();
    }

}