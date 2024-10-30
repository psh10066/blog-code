package com.psh10066.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job testJob;

    public BatchScheduler(JobLauncher jobLauncher, Job testJob) {
        this.jobLauncher = jobLauncher;
        this.testJob = testJob;
    }

    @Scheduled(cron = "*/5 * * * * *", zone = "Asia/Seoul")
    public void testScheduler() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("dateTime", LocalDateTime.now())
                .toJobParameters();

        jobLauncher.run(testJob, jobParameters);
    }
}
