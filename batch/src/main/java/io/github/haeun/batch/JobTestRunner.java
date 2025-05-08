//package io.github.haeun.batch;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//@RequiredArgsConstructor
//public class JobTestRunner implements ApplicationRunner {
//
//    private final JobLauncher jobLauncher;
//    private final Job bookJob;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        JobParameters params = new JobParametersBuilder()
//                .addString("runDate", LocalDate.now().toString())
//                .toJobParameters();
//        jobLauncher.run(bookJob, params);
//    }
//}
