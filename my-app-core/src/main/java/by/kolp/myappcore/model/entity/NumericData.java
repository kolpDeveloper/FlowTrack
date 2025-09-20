package by.kolp.myappcore.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "numeric_data")
public class NumericData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "numeric_data_seq_gen")
    @SequenceGenerator(
            name = "numeric_data_seq_gen",
            sequenceName = "numeric_data_seq",
            allocationSize = 1
    )
    private Long id;

    @OneToMany(mappedBy = "numericData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<by.kolp.myappcore.model.entity.NumericDataEntry> entriesList = new ArrayList<>();

    @CreationTimestamp
    private Instant createdAt;

    //public void addEntry(String key, Integer value) {
      //  entriesList.add(new NumericDataEntry(key, value, this));
    //}

}
