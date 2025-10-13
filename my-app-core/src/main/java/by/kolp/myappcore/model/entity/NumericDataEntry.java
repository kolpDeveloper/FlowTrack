package by.kolp.myappcore.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;


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

    @CreationTimestamp
    private Instant createdAt = Instant.now();
}
