package com.psh10066.example.service;

import com.psh10066.example.domain.User;
import com.psh10066.example.domain.UserRepository;
import com.psh10066.example.event.AFTER_COMMIT_ThrowEvent;
import com.psh10066.example.event.BEFORE_COMMIT_ThrowEvent;
import com.psh10066.example.event.EventListener_ThrowEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThrowPropagationService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public String eventListener() {
        userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new EventListener_ThrowEvent());
        return "성공";
    }

    @Transactional
    public String transactionalEventListener_BEFORE_COMMIT() {
        userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new BEFORE_COMMIT_ThrowEvent());
        return "성공";
    }

    @Transactional
    public String transactionalEventListener_AFTER_COMMIT() {
        userRepository.save(new User("service 생성"));
        applicationEventPublisher.publishEvent(new AFTER_COMMIT_ThrowEvent());
        return "성공";
    }
}
