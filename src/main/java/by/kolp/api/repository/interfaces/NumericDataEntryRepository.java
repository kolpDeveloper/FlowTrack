package by.kolp.api.repository.interfaces;

import by.kolp.api.model.entity.NumericDataEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NumericDataEntryRepository extends JpaRepository<NumericDataEntry, Long> {

    @Query("SELECT SUM(e.value) from NumericDataEntry e")
    Long getTotalSum();


    Optional<NumericDataEntry> findById(@NonNull Long id);

}
