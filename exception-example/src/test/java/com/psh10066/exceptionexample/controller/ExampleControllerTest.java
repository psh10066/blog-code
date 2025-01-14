package com.psh10066.exceptionexample.controller;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("존재하지 않는 경로로 호출한 경우")
    void empty1() throws Exception {
        mockMvc.perform(
                get("/empty")
            )
            .andExpect(status().isNotFound()) // 404
            .andExpect(result -> {
                Exception exception = result.getResolvedException();
                assertThat(exception.getMessage()).isEqualTo("No static resource empty.");
                assertThat(exception).isInstanceOf(NoResourceFoundException.class);
            });
    }

    @Test
    @DisplayName("필수 RequestParam을 누락한 경우")
    void param1() throws Exception {
        mockMvc.perform(
                get("/param")
            )
            .andExpect(status().isBadRequest()) // 400
            .andExpect(result -> {
                Exception exception = result.getResolvedException();
                assertThat(exception.getMessage()).isEqualTo("Required request parameter 'param' for method parameter type String is not present");
                assertThat(exception).isInstanceOf(MissingServletRequestParameterException.class);
                assertThat(exception).isInstanceOf(MissingRequestValueException.class);
            });
    }

    @Test
    @DisplayName("RequestParam의 Bean Validation이 실패한 경우")
    void param2() {
        try {
            mockMvc.perform(
                get("/param")
                    .param("param", "")
            );
            throw new RuntimeException("위에서 예외가 발생해야 한다.");
        } catch (Exception e) {
            Exception exception = (Exception) e.getCause();
            assertThat(exception.getMessage()).isEqualTo("paramAPI.param: must not be blank");
            assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        }
    }

    @Test
    @DisplayName("PathVariable의 Bean Validation이 실패한 경우")
    void path1() {
        try {
            mockMvc.perform(
                get("/path/{value}", 0)
            );
            throw new RuntimeException("위에서 예외가 발생해야 한다.");
        } catch (Exception e) {
            Exception exception = (Exception) e.getCause();
            assertThat(exception.getMessage()).isEqualTo("pathAPI.value: must be greater than or equal to 1");
            assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        }
    }

    @Test
    @DisplayName("RequestPart가 존재하지 않는 경우")
    void part1() throws Exception {
        mockMvc.perform(
                multipart("/part")
            )
            .andExpect(status().isBadRequest()) // 400
            .andExpect(result -> {
                Exception exception = result.getResolvedException();
                assertThat(exception.getMessage()).isEqualTo("Required part 'file' is not present.");
                assertThat(exception).isInstanceOf(MissingServletRequestPartException.class);
            });
    }

    @Test
    @DisplayName("RequestPart의 Bean Validation이 실패한 경우")
    void part2() throws Exception {
        try {
            mockMvc.perform(
                post("/part2")
            );
            throw new RuntimeException("위에서 예외가 발생해야 한다.");
        } catch (Exception e) {
            Exception exception = (Exception) e.getCause();
            assertThat(exception.getMessage()).isEqualTo("partAPI2.file: must not be null");
            assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        }
    }

    @Test
    @DisplayName("필수 RequestBody를 누락한 경우")
    void body1() throws Exception {
        mockMvc.perform(
                post("/body")
            )
            .andExpect(status().isBadRequest()) // 400
            .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertThat(exception.getMessage()).isEqualTo("Required request body is missing: public java.lang.Integer com.psh10066.exceptionexample.controller.ExampleController.bodyAPI(com.psh10066.exceptionexample.controller.ExampleController$ExampleRequest)");
                    assertThat(exception).isInstanceOf(HttpMessageNotReadableException.class);
                }
            );
    }

    @Test
    @DisplayName("RequestBody의 Bean Validation이 실패한 경우")
    void body2() throws Exception {
        mockMvc.perform(
                post("/body")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"value\":0}")
            )
            .andExpect(status().isBadRequest()) // 400
            .andExpect(result -> {
                Exception exception = result.getResolvedException();
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException.class);
            });
    }
}