package com.kurly.kurlyaccount.account.service;

import com.kurly.kurlyaccount.account.dto.LoginRequest;
import com.kurly.kurlyaccount.account.dto.SignUpRequest;
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

    private static final String USERID = "user-id";
    private static final String NAME = "user-name";
    private static final String PASSWORD = "user-password";
    private static final String ADDRESS = "user-address";

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    void 유저아이디_중복_검사 () {
        SignUpRequest request = SignUpRequest.builder()
                .userId(USERID)
                .password(PASSWORD)
                .name(NAME)
                .address(ADDRESS)
                .build();

        accountService.signUp(request);
        ExceptionCode code = assertThrows(AccountCustomException.class,
                () -> accountService.signUp(request)).getExceptionCode();

        assertEquals(ExceptionCode.IN_USE_USER_ID, code);
    }

    @Test
    void 로그인_이아디_검증1 () {
        LoginRequest request = LoginRequest.builder()
                .userId(USERID)
                .password(PASSWORD)
                .build();

        ExceptionCode code = assertThrows(AccountCustomException.class,
                () -> accountService.signIn(request)).getExceptionCode();

        assertEquals(ExceptionCode.NOT_FOUND_ACCOUNT, code);
    }

    @Test
    void 로그인_이아디_검증2 () {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .userId(USERID)
                .password(PASSWORD)
                .name(NAME)
                .address(ADDRESS)
                .build();

        accountService.signUp(signUpRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .userId(USERID + "123")
                .password(PASSWORD)
                .build();

        ExceptionCode code = assertThrows(AccountCustomException.class,
                () -> accountService.signIn(loginRequest)).getExceptionCode();

        assertEquals(ExceptionCode.NOT_FOUND_ACCOUNT, code);
    }

    @Test
    void 로그인_비밀번호_검증() {
        SignUpRequest request = SignUpRequest.builder()
                .userId(USERID)
                .password(PASSWORD)
                .name(NAME)
                .address(ADDRESS)
                .build();

        accountService.signUp(request);

        LoginRequest badRequest = LoginRequest.builder()
                .userId(USERID)
                .password(PASSWORD + "123")
                .build();

        ExceptionCode code = assertThrows(AccountCustomException.class,
                () -> accountService.signIn(badRequest)).getExceptionCode();

        assertEquals(ExceptionCode.NO_MATCHING_PASSWORD, code);
    }

}