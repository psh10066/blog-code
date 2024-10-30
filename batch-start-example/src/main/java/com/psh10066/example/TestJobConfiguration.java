package com.psh10066.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestJobConfiguration {

    @Bean
    public Job testJob(
            JobRepository jobRepository,
            Step testStep
    ) {
        return new JobBuilder("testJob", jobRepository)
                .validator(new DefaultJobParametersValidator(new String[]{"dateTime"}, new String[0]))
                .start(testStep)
                .build();
    }
}
