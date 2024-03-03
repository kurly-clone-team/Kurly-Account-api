package com.kurly.kurlyaccount.account.api;

import com.kurly.kurlyaccount.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    public ResponseEntity<?> signUp() {
        accountService.signUp();
        return null;
    }

    public ResponseEntity<?> signIn() {
        accountService.signIn();
        return null;
    }
}
