package io.github.haeun.carbon_scope.domain.project;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectCode;

    @Column(length = 50, nullable = false)
    private String type;

    @Column(length = 100, nullable = false)
    private String name;

    @Builder
    public Project(Integer projectCode, String type, String name) {
        this.projectCode = projectCode;
        this.type = type;
        this.name = name;
    }
}
