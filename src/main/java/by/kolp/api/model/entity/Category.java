package by.kolp.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "category_seq_gen")
    @SequenceGenerator(
            name = "category_seq_gen",
            sequenceName = "category_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @CreationTimestamp
    private Instant createdAt = Instant.now();

}
