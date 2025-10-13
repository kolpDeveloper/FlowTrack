package by.kolp.myappcore.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

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

    @CreationTimestamp
    private Instant createdAt;

}
