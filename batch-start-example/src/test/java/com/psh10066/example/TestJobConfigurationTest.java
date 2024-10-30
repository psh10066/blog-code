package com.psh10066.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBatchTest
@SpringBootTest
class TestJobConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job testJob;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberDataRepository memberDataRepository;

    @BeforeEach
    void setUp() {
        jobLauncherTestUtils.setJob(testJob);
    }

    @Test
    void testJob() throws Exception {
        // given
        memberRepository.save(new Member(1L, "홍길동"));

        // when
        LocalDateTime dateTime = LocalDateTime.of(2024, 9, 3, 12, 34, 56);
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("dateTime", dateTime)
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        List<MemberData> result = memberDataRepository.findAll();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getMemberId()).isEqualTo(1);
        assertThat(result.get(0).getData()).isEqualTo("Hello 홍길동 : 2024-09-03T12:34:56");
    }
}