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

    @ManyToOne
    @JoinColumn(name = "project_code")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "region_code")
    private Region region;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100)
    private String address;

}
