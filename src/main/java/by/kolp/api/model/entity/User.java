package by.kolp.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String username;


    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @Builder.Default
    @Column(nullable = false, name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();


    @Builder.Default
    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt = Instant.now();

    @Builder.Default
    @Column(nullable = false)
    private Instant lastLoginAt = Instant.now();


}
