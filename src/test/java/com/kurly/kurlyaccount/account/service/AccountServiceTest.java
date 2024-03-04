package com.kurly.kurlyaccount.account.service;

import com.kurly.kurlyaccount.account.dto.AuthenticationRequest;
import com.kurly.kurlyaccount.account.repository.AccountRepository;
import com.kurly.kurlyaccount.exception.AccountCustomException;
import com.kurly.kurlyaccount.exception.ExceptionCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    private static final String NAME = "user-name";
    private static final String PASSWORD = "user-password";

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    void 이름_중복_검사 () {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .name(NAME)
                .password(PASSWORD)
                .build();

        accountService.signUp(request);
        ExceptionCode code = assertThrows(AccountCustomException.class,
                () -> accountService.signUp(request)).getExceptionCode();

        assertEquals(ExceptionCode.IN_USE_NAME, code);
    }

    @Test
    void 로그인_이아디_검증 () {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .name(NAME)
                .password(PASSWORD)
                .build();

        ExceptionCode code = assertThrows(AccountCustomException.class,
                () -> accountService.signIn(request)).getExceptionCode();

        assertEquals(ExceptionCode.NOT_FOUND_ACCOUNT, code);
    }

    @Test
    void 로그인_비밀번호_검증() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .name(NAME)
                .password(PASSWORD)
                .build();

        accountService.signUp(request);

        AuthenticationRequest badRequest = AuthenticationRequest.builder()
                .name(NAME)
                .password(PASSWORD + "123")
                .build();

        ExceptionCode code = assertThrows(AccountCustomException.class,
                () -> accountService.signIn(badRequest)).getExceptionCode();

        assertEquals(ExceptionCode.NO_MATCHING_PASSWORD, code);
    }

}