package io.github.haeun.batch;

import io.github.haeun.batch.core.luncher.SequenceJobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.TimeZone;

@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        ConfigurableApplicationContext ctx = SpringApplication.run(BatchApplication.class, args);
        SequenceJobLauncher.run(ctx, args);
        System.exit(SpringApplication.exit(ctx));
    }

}
