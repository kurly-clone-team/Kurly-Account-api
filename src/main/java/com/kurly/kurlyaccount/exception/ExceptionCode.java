package com.kurly.kurlyaccount.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ACCOUNT(HttpStatus.BAD_REQUEST, "존재하지 않는 계정입니다."),
    IN_USE_NAME(HttpStatus.BAD_REQUEST, "사용 중인 이름입니다."),
    NO_MATCHING_PASSWORD(HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "잘못된 형식입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
