package io.github.haeun.carbon_scope.domain.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @AfterEach
    public void cleanup() {
        projectRepository.deleteAll();
    }

    @Test
    public void getProject() {
        String projectCode = "SmapleCode";
        String type = "type1";
        String name = "name1";

        projectRepository.save(Project.builder()
                .projectCode(projectCode)
                .type(type)
                .name(name)
                .build());

        List<Project> projectList = projectRepository.findAll();

        Project project = projectList.get(0);
        assert (project.getProjectCode().equals(projectCode));
        assert (project.getType().equals(type));
    }

}
