package by.kolp.financeservice.repository;

import by.kolp.financeservice.entity.NumericDataEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface NumericDataEntryRepository extends JpaRepository<NumericDataEntry, UUID> {

    @Query("SELECT SUM(e.amount) from NumericDataEntry e")
    Long getTotalSum();

    List<NumericDataEntry> findByUserId(UUID userId);

    @Override
    <S extends NumericDataEntry> S saveAndFlush(S entity);
}
