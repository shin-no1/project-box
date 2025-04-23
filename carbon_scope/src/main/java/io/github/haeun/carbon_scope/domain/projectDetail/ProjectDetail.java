package io.github.haeun.carbon_scope.domain.projectDetail;

import io.github.haeun.carbon_scope.domain.project.Project;
import io.github.haeun.carbon_scope.domain.region.Region;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class ProjectDetail {
    @Id
    private String projectDetailCode;

    @OneToOne
    @JoinColumn(name = "project_code")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "region_code")
    private Region region;

    @Column(length = 100, nullable = false)
    private String name;

    private Double xCoord;
    private Double yCoord;

}
