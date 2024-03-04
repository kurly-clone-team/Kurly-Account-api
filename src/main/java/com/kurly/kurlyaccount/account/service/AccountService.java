package com.kurly.kurlyaccount.account.service;


import com.kurly.kurlyaccount.account.Account;
import com.kurly.kurlyaccount.account.dto.AuthenticationRequest;
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
    public AuthenticationToken signUp(AuthenticationRequest authenticationRequest) {
        boolean isExistName = accountRepository.existsByName(authenticationRequest.getName());
        if(isExistName)
            throw new AccountCustomException(ExceptionCode.IN_USE_NAME);

        Account account = Account.of(authenticationRequest);
        accountRepository.save(account);
        return AuthenticationToken.of(account);
    }

    public AuthenticationToken signIn(AuthenticationRequest authenticationRequest) {
        Account account = accountRepository.findByName(authenticationRequest.getName())
                .orElseThrow(() -> new AccountCustomException(ExceptionCode.NOT_FOUND_ACCOUNT));

        if(!account.getPassword().equals(authenticationRequest.getPassword()))
            throw new AccountCustomException(ExceptionCode.NO_MATCHING_PASSWORD);

        return AuthenticationToken.of(account);
    }
}
