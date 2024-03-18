package com.kurly.kurlyaccount.account.api;

import com.kurly.kurlyaccount.account.dto.LoginRequest;
import com.kurly.kurlyaccount.account.dto.SignUpRequest;
import com.kurly.kurlyaccount.account.dto.AuthenticationToken;
import com.kurly.kurlyaccount.account.service.AccountService;
import com.kurly.kurlyaccount.exception.ExceptionCode;
import com.kurly.kurlyaccount.exception.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
@Tag(name = "Account API", description = "Authentication 관련 API 제공")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @RequestBody @Validated SignUpRequest signUpRequest,
            HttpServletRequest httpServletRequest
    ) {
        AuthenticationToken token = accountService.signUp(signUpRequest);

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("account-token", token);
        token.initializeSessionId(httpSession.getId());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Validated LoginRequest loginRequest,
            HttpServletRequest httpServletRequest
    ) {
        AuthenticationToken token = accountService.signIn(loginRequest);

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

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        log.info("health check...");
        return ResponseEntity.ok("health check...");
    }
}
