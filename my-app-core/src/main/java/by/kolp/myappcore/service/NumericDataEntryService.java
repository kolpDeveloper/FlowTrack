package by.kolp.myappcore.service;

import by.kolp.myappcore.exceptions.BadRequestException;
import by.kolp.myappcore.exceptions.NotFoundException;
import by.kolp.myappcore.mapper.NumericMapper;
import by.kolp.myappcore.model.dto.NumericDataEntryDTO;
import by.kolp.myappcore.model.entity.NumericDataEntry;
import by.kolp.myappcore.repository.interfaces.NumericDataEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class NumericDataEntryService {

    private final NumericDataEntryRepository numericDataEntryRepository;
    private final NumericMapper numericMapper;

    public void createNumericDataEntry(NumericDataEntryDTO numericDataEntryDTO) {
        if (numericDataEntryDTO.value() == null) {
            throw new BadRequestException("Value is required.");
        }

        NumericDataEntry newEntry = numericMapper.toEntity(numericDataEntryDTO);
        NumericDataEntry savedEntry = numericDataEntryRepository.save(newEntry);
        numericMapper.toDto(savedEntry);
    }

    public Long getTotalSum() {
        return Optional.ofNullable(numericDataEntryRepository.getTotalSum()).orElse(0L);
    }

    public void deleteById(Long id) {
        NumericDataEntry entry = numericDataEntryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("User not found with id %d", id)));
        numericDataEntryRepository.deleteById(entry.getId());
    }
}
