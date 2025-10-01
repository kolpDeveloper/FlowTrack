package by.kolp.myappweb.controller;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.exceptions.NotFoundException;
import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.service.NumericDataEntryService;
import by.kolp.myappweb.dto.AckDTO;
import by.kolp.myappweb.dto.NumericDataEntryDTO;
import by.kolp.myappweb.mapper.NumericMapperImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CalculateController {

    private final NumericDataEntryService numericDataEntryService;
    private final NumericMapperImpl numericMapper;

    private static final String CREATE_VALUE = "/api/value";
    private static final String DELETE_VALUE = "/api/value/{id}";


    @PostMapping(CREATE_VALUE)
    public NumericDataEntryDTO create_value(@RequestBody NumericDataEntryDTO numericDataEntryDTO) {

        if (numericDataEntryDTO.value() == null) {
            throw new BadRequestException("Value is required.");
        }

        NumericDataEntry data = numericDataEntryService.saveAndFlush(NumericDataEntry.builder().key(numericDataEntryDTO.key()).value(numericDataEntryDTO.value()).build());
        return numericMapper.map(data);
    }

    @DeleteMapping(DELETE_VALUE)
    public AckDTO delete_value(@PathVariable Long id) {
        NumericDataEntry dataEntry = numericDataEntryService.findById(id).orElseThrow(() -> new NotFoundException(String.format("This id \"%s%%\" doesn't exist", id)));
        numericDataEntryService.delete(dataEntry);

        return new AckDTO("Value successfully deleted", true);
    }

    @GetMapping("/api/value/sum")
    public Long getTotalSum() {
        log.info("Getting total sum");
        return numericDataEntryService.getTotalSum();
    }

}