package io.github.haeun.petstats.domain.species;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Species {
    @Id
    private Integer id;

    @Column(length = 10, unique = true, nullable = false)
    private String name;

}
