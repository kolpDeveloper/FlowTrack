package by.kolp.api.model.entity;

import by.kolp.api.model.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "role_seq_gen")
    @SequenceGenerator(
            name = "role_seq_gen",
            sequenceName = "role_seq",
            allocationSize = 1
    )
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

}
