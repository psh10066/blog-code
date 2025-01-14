package com.psh10066.exceptionexample.component;

import com.psh10066.exceptionexample.repository.ExampleComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class ExampleComponentTest {

    @Autowired
    private ExampleComponent exampleComponent;

    @Test
    @DisplayName("Entity가 없는 경우 IllegalStateException 예외가 발생한다")
    void test1() {
        assertThatThrownBy(() -> exampleComponent.getById(1L))
            .isInstanceOf(IllegalStateException.class);
    }
}