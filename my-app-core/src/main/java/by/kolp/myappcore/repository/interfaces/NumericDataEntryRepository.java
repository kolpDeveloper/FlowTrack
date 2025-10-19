package by.kolp.myappcore.repository.interfaces;

import by.kolp.myappcore.model.entity.NumericDataEntry;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NumericDataEntryRepository extends JpaRepository<NumericDataEntry, Long> {

    @Query("SELECT SUM(e.value) from NumericDataEntry e")
    Long getTotalSum();


    Optional<NumericDataEntry> findById(Long id);

    @Override
    <S extends NumericDataEntry> S saveAndFlush( S entity);

    @Override
    void delete(@NonNull NumericDataEntry entity);
}
