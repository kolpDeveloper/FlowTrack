package by.kolp.api.repository.interfaces;

import by.kolp.api.model.entity.NumericData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NumericDataRepository extends JpaRepository<NumericData, Long> {

    Optional<NumericData> findById(long id);

    @Override
    <S extends NumericData> S save(S entity);

}
