package com.psh10066.example.service;

import com.psh10066.example.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ThrowPropagationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThrowPropagationService throwPropagationService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("eventListener에서 예외가 발생하면 service에서도 예외가 발생한다.")
    @Test
    void eventListener() {
        assertThrows(RuntimeException.class, () -> throwPropagationService.eventListener());

        assertEquals(0, userRepository.count());
    }

    @DisplayName("transactionalEventListener의 BEFORE_COMMIT에서 예외가 발생하면 service에서도 예외가 발생한다.")
    @Test
    void transactionalEventListener_BEFORE_COMMIT() {
        assertThrows(RuntimeException.class, () -> throwPropagationService.transactionalEventListener_BEFORE_COMMIT());

        assertEquals(0, userRepository.count());
    }

    @DisplayName("transactionalEventListener의 AFTER_COMMIT에서 예외가 발생해도 service에서 예외가 발생하지 않는다.")
    @Test
    void transactionalEventListener_AFTER_COMMIT() {
        assertEquals("성공", throwPropagationService.transactionalEventListener_AFTER_COMMIT());

        assertEquals(1, userRepository.count());
    }
}