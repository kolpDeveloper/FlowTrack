package by.kolp.api.model.entity;

import by.kolp.api.model.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName name;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<User> users;

}
