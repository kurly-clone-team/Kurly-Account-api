package com.kurly.kurlyaccount.account.api;

import com.kurly.kurlyaccount.account.dto.AuthenticationRequest;
import com.kurly.kurlyaccount.account.dto.AuthenticationToken;
import com.kurly.kurlyaccount.account.service.AccountService;
import com.kurly.kurlyaccount.exception.ExceptionCode;
import com.kurly.kurlyaccount.exception.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Validated AuthenticationRequest authenticationRequest,
                         HttpServletRequest httpServletRequest) {

        AuthenticationToken token = accountService.signUp(authenticationRequest);

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("account-token", token);
        token.initializeSessionId(httpSession.getId());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid AuthenticationRequest authenticationRequest,
                         HttpServletRequest httpServletRequest) {

        AuthenticationToken token = accountService.signIn(authenticationRequest);

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("account-token", token);
        token.initializeSessionId(httpSession.getId());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null) {
            log.info("session-id=" + httpSession.getId() + " logout");
            httpSession.invalidate();
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().body(ExceptionCode.BAD_REQUEST.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(ExceptionResponse.of(
                        ExceptionCode.VALIDATION_FAILED.name(),
                        Objects.requireNonNull(e.getFieldError()).getDefaultMessage()));
    }
}
