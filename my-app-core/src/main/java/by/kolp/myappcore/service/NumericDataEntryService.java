package by.kolp.myappcore.service;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.mapper.NumericMapper;
import by.kolp.myappcore.mapper.UserMapper;
import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.repository.interfaces.NumericDataEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NumericDataEntryService {

    private final NumericDataEntryRepository numericDataEntryRepository;
    private final NumericMapper numericMapper;

    public NumericDataEntryDTO createNumericDataEntry(NumericDataEntryDTO numericDataEntryDTO) {
        if (numericDataEntryDTO.value() == null) {
            throw new BadRequestException("Value is required.");
        }

        NumericDataEntry newEntry = numericMapper.toEntity(numericDataEntryDTO);
        NumericDataEntry savedEntry = numericDataEntryRepository.save(newEntry);
        return numericMapper.toDto(savedEntry);
    }

    public Long getTotalSum() {
        return Optional.ofNullable(numericDataEntryRepository.getTotalSum()).orElse(0L);
    }

    public Optional<NumericDataEntry> findById(Long id) {
        return numericDataEntryRepository.findById(id);
    }

    public NumericDataEntry saveAndFlush(NumericDataEntry entity) {
        return numericDataEntryRepository.saveAndFlush(entity);
    }

    public void delete(NumericDataEntry entity) {
        numericDataEntryRepository.delete(entity);
    }
}
