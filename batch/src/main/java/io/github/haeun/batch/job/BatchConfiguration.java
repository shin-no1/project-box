package io.github.haeun.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {
    private final Step stepBook;

    @Bean
    public Job bookJob(JobRepository jobRepository) {
        return new JobBuilder("bookJob", jobRepository)
                .start(stepBook)
                .build();
    }
}
