package by.kolp.financeservice.service;

import by.kolp.commonexceptions.exceptions.BadRequestException;
import by.kolp.commonexceptions.exceptions.NotFoundException;
import by.kolp.financeservice.dto.NumericDataEntryDTO;
import by.kolp.financeservice.entity.NumericDataEntry;
import by.kolp.financeservice.mapper.NumericMapper;
import by.kolp.financeservice.repository.NumericDataEntryRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@CacheConfig(cacheNames = "data")
@RequiredArgsConstructor
public class NumericDataEntryService {

    private final NumericDataEntryRepository numericDataEntryRepository;
    private final NumericMapper numericMapper;


    public List<NumericDataEntryDTO> findByUserId(UUID userId) {
        return numericDataEntryRepository.findByUserId(userId)
                .stream()
                .map(numericMapper::toDto)
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "financeBackend",fallbackMethod = "createOperationFallback")
    @CacheEvict(value = "data", allEntries = true, beforeInvocation = true)
    public void createNumericDataEntry(NumericDataEntryDTO numericDataEntryDTO) {
        if (numericDataEntryDTO.value() == null) {
            throw new BadRequestException("Value is required.");
        }
        NumericDataEntry newEntry = numericMapper.toEntity(numericDataEntryDTO);
        NumericDataEntry savedEntry = numericDataEntryRepository.save(newEntry);
        numericMapper.toDto(savedEntry);
    }

    @Cacheable(key = "'totalSumValue'")
    public Long getTotalSum() {
        return Optional.ofNullable(numericDataEntryRepository.getTotalSum()).orElse(0L);
    }

    @CacheEvict(key = "'totalSumValue'")
    public void deleteById(UUID id) {
        NumericDataEntry entry = numericDataEntryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("User not found with id %d", id)));
        numericDataEntryRepository.deleteById(entry.getId());
    }
}
