package com.kurly.kurlyaccount.exception.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {

    private final String code;
    private final String message;

    public static ExceptionResponse of(String code, String message) {
        return ExceptionResponse.builder()
                .code(code)
                .message(message)
                .build();
    }
}