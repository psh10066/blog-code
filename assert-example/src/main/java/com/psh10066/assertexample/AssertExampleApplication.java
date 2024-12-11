package com.psh10066.assertexample;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

@SpringBootApplication
public class AssertExampleApplication {

    public static void main(String[] args) {
//        assert 1 == 2 : "유효성 검사에 실패했습니다.";
        Assert.isTrue(1 == 2, "유효성 검사에 실패했습니다.");
        System.out.println("Success!");
    }
}
