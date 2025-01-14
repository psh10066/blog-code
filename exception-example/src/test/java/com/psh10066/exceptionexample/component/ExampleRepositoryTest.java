package com.psh10066.exceptionexample.component;

import com.psh10066.exceptionexample.repository.ExampleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class ExampleRepositoryTest {

    @Autowired
    private ExampleRepository exampleRepository;

    @Test
    @DisplayName("Entity가 없는 경우 IllegalStateException 예외가 InvalidDataAccessApiUsageException 예외로 변환된다")
    void test1() {
        assertThatThrownBy(() -> exampleRepository.getById(1L))
            .isNotInstanceOf(IllegalStateException.class)
            .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }
}