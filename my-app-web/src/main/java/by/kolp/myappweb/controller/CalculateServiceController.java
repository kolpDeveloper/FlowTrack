package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.repository.interfaces.NumericDataEntryRepository;
import by.kolp.myappdataapi.dto.AckDTO;
import by.kolp.myappdataapi.dto.NumericDataEntryDTO;
import by.kolp.myappdataapi.exceptions.BadRequestException;
import by.kolp.myappdataapi.exceptions.NotFoundException;
import by.kolp.myappdataapi.factories.NumericDataEntryDtoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping

public class CalculateServiceController {


    private final NumericDataEntryRepository numericDataEntryRepository;
    private final NumericDataEntryDtoFactory numericDataEntryDtoFactory;

    private static final String CREATE_VALUE = "/api/value";
    private static final String DELETE_VALUE = "/api/value/{id}";


    @Autowired
    public CalculateServiceController(NumericDataEntryRepository numericDataEntryRepository) {
        this.numericDataEntryRepository = numericDataEntryRepository;
        this.numericDataEntryDtoFactory = new NumericDataEntryDtoFactory();
    }


    @PostMapping(CREATE_VALUE)
    public NumericDataEntryDTO create_value(@RequestBody NumericDataEntryDTO numericDataEntryDTO) {

        if (numericDataEntryDTO.value() == null) {
            throw new BadRequestException("Value is required.");
        }

        NumericDataEntry data = numericDataEntryRepository.saveAndFlush(NumericDataEntry.builder().key(numericDataEntryDTO.key()).value(numericDataEntryDTO.value()).build());
        return numericDataEntryDtoFactory.makeNumericDataEntryDto(data);
    }

    @DeleteMapping(DELETE_VALUE)
    public AckDTO delete_value(@PathVariable Long id) {
        NumericDataEntry dataEntry = numericDataEntryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("This id \"%s%%\" doesn't exist", id)));
        numericDataEntryRepository.delete(dataEntry);

        return new AckDTO("Value successfully deleted", true);
    }

    @GetMapping("/api/value/sum")
    public Long getTotalSum() {
        log.info("Getting total sum");
        return numericDataEntryRepository.getTotalSum();
    }

}