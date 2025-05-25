package by.kolp.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "numeric_data")
public class NumericData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "numericData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NumericDataEntry> entriesList = new ArrayList<>();

    private Instant createdAt = Instant.now();

    public void addEntry(String key, Integer value) {
        entriesList.add(new NumericDataEntry(key, value, this));
    }

}
