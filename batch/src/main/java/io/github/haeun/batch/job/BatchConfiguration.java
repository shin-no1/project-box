package io.github.haeun.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {
    private final Step stepBook;

    public BatchConfiguration(@Qualifier("stepBook") Step stepBook) {
        this.stepBook = stepBook;
    }

    @Bean
    public Job bookJob(JobRepository jobRepository) {
        return new JobBuilder("bookJob", jobRepository)
                .start(stepBook)
                .build();
    }
}
