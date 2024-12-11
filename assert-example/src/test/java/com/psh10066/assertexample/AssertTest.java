package com.psh10066.assertexample;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class AssertTest {

    @Test
    void javaAssert() {
        assert 1 == 2 : "유효성 검사에 실패했습니다.";
    }
}