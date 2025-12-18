package by.kolp.financeservice.repository;

import by.kolp.financeservice.entity.NumericDataEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NumericDataEntryRepository extends JpaRepository<NumericDataEntry, Long> {

    @Query("SELECT SUM(e.value) from NumericDataEntry e")
    Long getTotalSum();


    Optional<NumericDataEntry> findById(Long id);

    @Override
    <S extends NumericDataEntry> S saveAndFlush(S entity);
}
