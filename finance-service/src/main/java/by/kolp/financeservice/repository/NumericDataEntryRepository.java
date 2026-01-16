package by.kolp.financeservice.repository;

import by.kolp.financeservice.entity.NumericDataEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface NumericDataEntryRepository extends JpaRepository<NumericDataEntry, Long> {

    @Query("SELECT SUM(e.amount) from NumericDataEntry e")
    Long getTotalSum();


    Optional<NumericDataEntry> findById(UUID id);

    @Override
    <S extends NumericDataEntry> S saveAndFlush(S entity);

    void deleteById(UUID uuid);
}
