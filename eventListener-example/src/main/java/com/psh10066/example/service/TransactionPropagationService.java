package com.psh10066.example.service;

import com.psh10066.example.domain.User;
import com.psh10066.example.domain.UserRepository;
import com.psh10066.example.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionPropagationService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void eventListener_noTransactional() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new EventListener_TransactionEvent(user));
    }

    @Transactional
    public void eventListener_hasTransactional() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new EventListener_TransactionEvent(user));
    }

    public void eventListener_newTransactional() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new EventListener_NewTransactionEvent(user));
    }

    @Transactional
    public void transactionalEventListener_BEFORE_COMMIT() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new BEFORE_COMMIT_TransactionEvent(user));
    }

    @Transactional
    public void newTransactionalEventListener_BEFORE_COMMIT() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new BEFORE_COMMIT_NewTransactionEvent(user));
    }

    @Transactional
    public void transactionalEventListener_AFTER_COMMIT() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new AFTER_COMPLETION_TransactionEvent(user));
    }

    @Transactional
    public void newTransactionalEventListener_AFTER_COMMIT() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new AFTER_COMPLETION_NewTransactionEvent(user));
    }

    @Transactional
    public void transactionalEventListener_AFTER_ROLLBACK() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new AFTER_COMPLETION_TransactionEvent(user));
        throw new RuntimeException();
    }

    @Transactional
    public void newTransactionalEventListener_AFTER_ROLLBACK() {
        User user = userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new AFTER_COMPLETION_NewTransactionEvent(user));
        throw new RuntimeException();
    }
}
