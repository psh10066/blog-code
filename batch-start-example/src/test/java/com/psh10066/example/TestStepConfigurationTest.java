package com.psh10066.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestStepConfigurationTest {

    private TestStepConfiguration testStepConfiguration;

    @BeforeEach
    void setUp() {
        testStepConfiguration = new TestStepConfiguration();
    }

    @Test
    void memberDataItemProcessor() throws Exception {
        // given
        Member member = new Member(1L, "홍길동");
        LocalDateTime dateTime = LocalDateTime.of(2024, 9, 3, 12, 34, 56);

        // when
        MemberData result = testStepConfiguration.memberDataItemProcessor(dateTime).process(member);

        // then
        assertThat(result.getMemberId()).isEqualTo(1L);
        assertThat(result.getData()).isEqualTo("Hello 홍길동 : 2024-09-03T12:34:56");
    }
}