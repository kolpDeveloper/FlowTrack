package by.kolp.myappserviceimpl.serviceRepository;

import by.kolp.myappcore.repository.interfaces.NumericDataEntryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumericDataEntryServiceRepository extends JpaRepository<Long, String> {
}
