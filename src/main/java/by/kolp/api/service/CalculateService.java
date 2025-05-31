package by.kolp.api.service;

import by.kolp.api.model.dto.NumericDataEntryDTO;
import by.kolp.api.model.entity.NumericDataEntry;
import by.kolp.api.repository.interfaces.NumericDataEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
public class CalculateService {
    private final NumericDataEntryRepository numericDataEntryRepository;

    @Autowired
    public CalculateService(NumericDataEntryRepository numericDataEntryRepository) {
        this.numericDataEntryRepository = numericDataEntryRepository;
    }


    @Transactional(readOnly = true)
    public Integer sumAllValues(@Validated NumericDataEntryDTO data) {

        NumericDataEntry newNumericdataEntry = new NumericDataEntry();
        newNumericdataEntry.setKey(data.key());
        newNumericdataEntry.setValue(data.value());
        numericDataEntryRepository.save(newNumericdataEntry);
        log.info("saved numericDataEntry : {}", newNumericdataEntry);
        return numericDataEntryRepository.sumAllValues();
    }
}
