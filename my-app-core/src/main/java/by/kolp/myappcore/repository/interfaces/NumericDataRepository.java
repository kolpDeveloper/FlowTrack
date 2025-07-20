package by.kolp.myappcore.repository.interfaces;

import by.kolp.myappcore.model.entity.NumericData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NumericDataRepository extends JpaRepository<NumericData, Long> {

    Optional<NumericData> findById(long id);

    @Override
    @NonNull
    <S extends NumericData> S save(@NonNull S entity);

}
