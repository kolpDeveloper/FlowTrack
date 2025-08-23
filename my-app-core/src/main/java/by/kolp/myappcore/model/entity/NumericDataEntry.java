package by.kolp.myappcore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "numeric_data_entry")
public class NumericDataEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "numeric_data_entry_seq_gen")
    @SequenceGenerator(
            name = "numeric_data_entry_seq_gen",
            sequenceName = "numeric_data_entry_seq",
            allocationSize = 1
    )
    private Long id;

    private String key;
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "numeric_data_id")
    private NumericData numericData;

    @CreationTimestamp
    @Builder.Default
    private Instant createdAt = Instant.now();

    public NumericDataEntry(String key, Integer value, NumericData numericData) {
        this.key = key;
        this.value = value;
        this.numericData = numericData;
    }
}
