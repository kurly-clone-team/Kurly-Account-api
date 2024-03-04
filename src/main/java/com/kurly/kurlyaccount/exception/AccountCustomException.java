package com.kurly.kurlyaccount.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountCustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;
}
