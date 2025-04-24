package io.github.haeun.petstats.web.domain.animal_type;

import io.github.haeun.petstats.web.domain.species.Species;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "multiUniqueConstraint", columnNames = {
                "name", "species_id"
        })
})
@Entity
public class AnimalType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;
}
