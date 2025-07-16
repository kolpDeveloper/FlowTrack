package by.kolp.api.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "users_seq_gen")
    @SequenceGenerator(
            name = "users_seq_gen",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String username;


    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role ;

    @CreationTimestamp
    @Builder.Default
    @Column(nullable = false, name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();


    @UpdateTimestamp
    @Builder.Default
    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt = Instant.now();

    @UpdateTimestamp
    @Builder.Default
    @Column(nullable = false)
    private Instant lastLoginAt = Instant.now();
}
