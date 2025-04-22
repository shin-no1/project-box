package io.github.haeun.projectbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ProjectBoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectBoxApplication.class, args);
    }

}
