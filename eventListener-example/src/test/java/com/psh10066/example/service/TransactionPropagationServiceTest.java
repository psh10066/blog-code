package com.psh10066.example.service;

import com.psh10066.example.domain.User;
import com.psh10066.example.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TransactionPropagationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionPropagationService transactionPropagationService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("service에 @Transactional이 없으면 eventListener에서 dirty checking이 일어나지 않는다.")
    @Test
    void eventListener_noTransactional() {
        transactionPropagationService.eventListener_noTransactional();

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
        assertEquals("service 생성", userList.get(0).getName());
        assertEquals("listener 생성", userList.get(1).getName());
    }

    @DisplayName("service에 @Transactional이 있으면 eventListener에서 dirty checking이 일어난다.")
    @Test
    void eventListener_hasTransactional() {
        transactionPropagationService.eventListener_hasTransactional();

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
        assertEquals("dirty checking 수정", userList.get(0).getName());
        assertEquals("listener 생성", userList.get(1).getName());
    }

    @DisplayName("service에 @Transactional이 없으면 eventListener에 @Transactional이 있어도 dirty checking이 일어나지 않는다.")
    @Test
    void eventListener_newTransactional() {
        transactionPropagationService.eventListener_newTransactional();

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
        assertEquals("service 생성", userList.get(0).getName());
        assertEquals("listener 생성", userList.get(1).getName());
    }

    @DisplayName("transactionalEventListener BEFORE_COMMIT")
    @Test
    void transactionalEventListener_BEFORE_COMMIT() {
        transactionPropagationService.transactionalEventListener_BEFORE_COMMIT();

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
        assertEquals("dirty checking 수정", userList.get(0).getName());
        assertEquals("listener 생성", userList.get(1).getName());
    }

    @DisplayName("transactionalEventListener BEFORE_COMMIT, REQUIRES_NEW")
    @Test
    void newTransactionalEventListener_BEFORE_COMMIT() {
        transactionPropagationService.newTransactionalEventListener_BEFORE_COMMIT();

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
        assertEquals("dirty checking 수정", userList.get(0).getName());
        assertEquals("listener 생성", userList.get(1).getName());
    }

    @DisplayName("transactionalEventListener AFTER_COMMIT")
    @Test
    void transactionalEventListener_AFTER_COMMIT() {
        transactionPropagationService.transactionalEventListener_AFTER_COMMIT();

        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
        assertEquals("service 생성", userList.get(0).getName());
    }

    @DisplayName("transactionalEventListener AFTER_COMMIT, REQUIRES_NEW")
    @Test
    void newTransactionalEventListener_AFTER_COMMIT() {
        transactionPropagationService.newTransactionalEventListener_AFTER_COMMIT();

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
        assertEquals("service 생성", userList.get(0).getName());
        assertEquals("listener 생성", userList.get(1).getName());
    }

    @DisplayName("transactionalEventListener AFTER_ROLLBACK")
    @Test
    void transactionalEventListener_AFTER_ROLLBACK() {
        assertThrows(RuntimeException.class, () -> transactionPropagationService.transactionalEventListener_AFTER_ROLLBACK());

        List<User> userList = userRepository.findAll();
        assertEquals(0, userList.size());
    }

    @DisplayName("transactionalEventListener AFTER_ROLLBACK, REQUIRES_NEW")
    @Test
    void newTransactionalEventListener_AFTER_ROLLBACK() {
        assertThrows(RuntimeException.class, () -> transactionPropagationService.newTransactionalEventListener_AFTER_ROLLBACK());

        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
        assertEquals("listener 생성", userList.get(0).getName());
    }
}