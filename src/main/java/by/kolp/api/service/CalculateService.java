package by.kolp.api.service;

import by.kolp.api.model.dto.NumericDataEntryDTO;
import by.kolp.api.model.entity.NumericDataEntry;
import by.kolp.api.model.entity.NumericData;
import by.kolp.api.repository.interfaces.NumericDataEntryRepository;
import by.kolp.api.repository.interfaces.NumericDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateService {


        private final NumericDataRepository numericDataRepository;

        private final NumericDataEntryRepository numericDataEntryRepository;

        @Autowired
        public CalculateService(NumericDataRepository numericDataRepository, NumericDataEntryRepository numericDataEntryRepository) {
            this.numericDataRepository = numericDataRepository;
            this.numericDataEntryRepository = numericDataEntryRepository;
        }


    public Integer sumAllValues(NumericDataEntryDTO data) {

        Integer sum = 0;
        NumericData newNumericdata = new NumericData();
        newNumericdata.addEntry(data.key(), data.value());

        numericDataRepository.save(newNumericdata);

        List<NumericDataEntry> AllEntries = numericDataEntryRepository.findAll();

        sum = AllEntries.stream()
                .mapToInt(NumericDataEntry::getValue)
                .sum();

        for (NumericDataEntry entry : AllEntries) {
            if (entry != null) {
                sum += entry.getValue();
            }
        }

        return sum;
    }
}
