package by.kolp.financeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String name;

    @NonNull
    @Column(nullable = false, length = 300)
    private String description;

    @CreationTimestamp
    private Instant createdAt;


    @OneToOne
    @JoinColumn(name = "numeric_data_entry_id")
    private NumericDataEntry numericDataEntry;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
