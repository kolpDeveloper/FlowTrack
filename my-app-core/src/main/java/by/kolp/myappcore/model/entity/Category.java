package by.kolp.myappcore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Instant createdAt;


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
