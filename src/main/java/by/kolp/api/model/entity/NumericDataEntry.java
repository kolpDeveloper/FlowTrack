package by.kolp.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "numeric_data_entry")
public class NumericDataEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String key;
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "numeric_data_id")
    private NumericData numericData;

    @Builder.Default
    private Instant createdAt = Instant.now();

    public NumericDataEntry(String key, Integer value, NumericData numericData) {
        this.key = key;
        this.value = value;
        this.numericData = numericData;
    }
}
