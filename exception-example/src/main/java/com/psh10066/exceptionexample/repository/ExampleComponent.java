package com.psh10066.exceptionexample.repository;

import com.psh10066.exceptionexample.ExampleEntity;
import com.psh10066.exceptionexample.ExampleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExampleComponent {

    private final ExampleJpaRepository exampleJpaRepository;

    public ExampleEntity getById(Long id) {
        return exampleJpaRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Entity가 존재하지 않습니다."));
    }
}