package io.github.haeun.batch.core.luncher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SequenceJobLauncher {

    public static void run(ConfigurableApplicationContext ctx, String[] args) {
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters();

            String[] jobNames = System.getProperty("spring.batch.job.names").split(",");

            for (String jobName : jobNames) {
                jobLauncher.run((Job) ctx.getBean(jobName), jobParameters);
            }
        } catch (Exception e) {
            log.error("error", e);
        }
    }
}
