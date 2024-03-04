package com.kurly.kurlyaccount.account.dto;

import com.kurly.kurlyaccount.account.Account;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationToken {

    private String sessionId;
    private String name;

    public static AuthenticationToken of(Account account) {
        return AuthenticationToken.builder()
                .name(account.getName())
                .build();
    }

    public void initializeSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
