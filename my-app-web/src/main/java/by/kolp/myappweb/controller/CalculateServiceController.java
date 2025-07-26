package by.kolp.myappweb.controller;

import by.kolp.myappcore.model.dto.AckDTO;
import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.model.exceptions.BadRequestException;
import by.kolp.myappcore.model.exceptions.NotFoundException;
import by.kolp.myappcore.repository.interfaces.NumericDataEntryRepository;
import by.kolp.myappservice.service.CalculateService;
import by.kolp.myappweb.factories.NumericDataEntryDtoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping

public class CalculateServiceController {

    private final CalculateService calculateService;
    private final NumericDataEntryRepository numericDataEntryRepository;
    private final NumericDataEntryDtoFactory numericDataEntryDtoFactory;

    private static final String CREATE_VALUE = "/api/value";
    private static final String DELETE_VALUE = "/api/value/{id}";


    @Autowired
    public CalculateServiceController(CalculateService calculateService, NumericDataEntryRepository numericDataEntryRepository) {
        this.calculateService = calculateService;
        this.numericDataEntryRepository = numericDataEntryRepository;
        this.numericDataEntryDtoFactory = new NumericDataEntryDtoFactory();
    }


        @PostMapping(CREATE_VALUE)
        public NumericDataEntryDTO create_value(@RequestBody NumericDataEntryDTO numericDataEntryDTO) {

            if (numericDataEntryDTO.value() == null) {
                throw new BadRequestException("Value is required.");
            }

            NumericDataEntry data = numericDataEntryRepository.saveAndFlush(
                    NumericDataEntry.builder()
                            .key(numericDataEntryDTO.key())
                            .value(numericDataEntryDTO.value())
                            .build());
            return numericDataEntryDtoFactory.makeNumericDataEntryDto(data);
        }

    @DeleteMapping(DELETE_VALUE)
    public AckDTO delete_value(@PathVariable Long id) {
        NumericDataEntry dataEntry = numericDataEntryRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String
                                .format("This id \"%s%%\" doesn't exist", id)));
        numericDataEntryRepository.delete(dataEntry);

        return new AckDTO("Value successfully deleted",true);
    }


    @GetMapping("/api/value/sum")
    public Long getTotalSum() {
        //log.info("Getting total sum");
        return calculateService.getTotalSum();
    }

}