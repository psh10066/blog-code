package com.psh10066.exceptionexample;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleJpaRepository extends JpaRepository<ExampleEntity, Long> {
}
