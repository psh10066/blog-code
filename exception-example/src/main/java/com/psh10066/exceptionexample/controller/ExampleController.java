package com.psh10066.exceptionexample.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping(value = "/param")
    public String paramAPI(@RequestParam @NotBlank String param) {
        return param;
    }

    @GetMapping(value = "/path/{value}")
    public Integer pathAPI(@PathVariable @Min(1) Integer value) {
        return value;
    }

    @PostMapping(value = "/part")
    public MultipartFile partAPI(@RequestPart MultipartFile file) {
        return file;
    }

    @PostMapping(value = "/part2")
    public MultipartFile partAPI2(@RequestPart(required = false) @NotNull MultipartFile file) {
        return file;
    }

    @PostMapping(value = "/body")
    public Integer bodyAPI(@Validated @RequestBody ExampleRequest body) {
        return body.getValue();
    }

    @Getter
    public static class ExampleRequest {

        @Min(1)
        private Integer value;
    }
}
