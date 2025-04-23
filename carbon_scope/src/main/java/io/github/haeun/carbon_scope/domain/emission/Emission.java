package io.github.haeun.carbon_scope.domain.emission;

import io.github.haeun.carbon_scope.domain.projectDetail.ProjectDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Emission {
    @Id
    private String projectDetailCode;

    @OneToOne
    @JoinColumn(name = "project_detail_code", insertable = false, updatable = false)
    private ProjectDetail projectDetail;

    private Double constructionCo2;
    private Double constructionCh4;
    private Double constructionN2o;
    private Double constructionEtc;

    private Double operationCo2;
    private Double operationCh4;
    private Double operationN2o;
    private Double operationEtc;

    @Column(length = 500)
    private String note;
}
