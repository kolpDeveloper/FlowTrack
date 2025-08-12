package by.kolp.myappserviceimpl.serviceRepository;

import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.repository.interfaces.NumericDataEntryRepository;
import by.kolp.myappservice.service.CalculateService;
import by.kolp.myappservice.serviceDto.NumericDataEntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
public class CalculateServiceImpl implements CalculateService {

    private final NumericDataEntryRepository numericDataEntryRepository;

    @Autowired
    public CalculateServiceImpl(NumericDataEntryRepository numericDataEntryRepository) {
        this.numericDataEntryRepository = numericDataEntryRepository;
    }

    @Override
    @Transactional
    public Long sumAllValues(@Validated NumericDataEntryRequest data) {
        var numericData = NumericDataEntry.builder()
                .key(data.key())
                .value(data.value())
                .build();

        numericDataEntryRepository.save(numericData);

        return numericDataEntryRepository.getTotalSum();
    }
}
