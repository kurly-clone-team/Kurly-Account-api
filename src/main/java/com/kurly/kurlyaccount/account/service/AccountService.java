package com.kurly.kurlyaccount.account.service;


import com.kurly.kurlyaccount.account.Account;
import com.kurly.kurlyaccount.account.dto.LoginRequest;
import com.kurly.kurlyaccount.account.dto.SignUpRequest;
import com.kurly.kurlyaccount.account.dto.AuthenticationToken;
import com.kurly.kurlyaccount.account.repository.AccountRepository;
import com.kurly.kurlyaccount.exception.AccountCustomException;
import com.kurly.kurlyaccount.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AuthenticationToken signUp(SignUpRequest signUpRequest) {
        boolean isExistUserid = accountRepository.existsByUserId(signUpRequest.getUserId());
        if(isExistUserid)
            throw new AccountCustomException(ExceptionCode.IN_USE_USER_ID);

        Account account = Account.of(signUpRequest);
        accountRepository.save(account);
        return AuthenticationToken.of(account);
    }

    public AuthenticationToken signIn(LoginRequest loginRequest) {
        Account account = accountRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new AccountCustomException(ExceptionCode.NOT_FOUND_ACCOUNT));

        if(!account.getPassword().equals(loginRequest.getPassword()))
            throw new AccountCustomException(ExceptionCode.NO_MATCHING_PASSWORD);

        return AuthenticationToken.of(account);
    }
}
