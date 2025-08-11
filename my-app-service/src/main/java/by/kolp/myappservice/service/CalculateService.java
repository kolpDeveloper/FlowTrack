package by.kolp.myappservice.service;

import by.kolp.myappservice.serviceDto.NumericDataEntryRequest;

//@Slf4j
//@Service
public interface CalculateService {

    Long sumAllValues(NumericDataEntryRequest data);

}
    /*private final NumericDataEntryRepository numericDataEntryRepository;

    @Autowired
    public CalculateService(NumericDataEntryRepository numericDataEntryRepository) {
        this.numericDataEntryRepository = numericDataEntryRepository;
    }

    @Transactional
    public Long sumAllValues(@Validated NumericDataEntryDTO data) {
        var numericData = NumericDataEntryDTO.builder()
                .key(data.key())
                .value(data.value())
                .build();
        numericDataEntryRepository.save(numericData);
        log.info("Saved numericDataEntry : {}", numericData);
        return getTotalSum();
    }

    public Long getTotalSum() {
        return numericDataEntryRepository.getTotalSum();
    }
}*/
