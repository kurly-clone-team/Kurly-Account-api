package com.kurly.kurlyaccount.account.dto;

import com.kurly.kurlyaccount.account.Account;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationToken {

    private String sessionId;
    private String userId;
    private String name;
    private String address;

    public static AuthenticationToken of(Account account) {
        return AuthenticationToken.builder()
                .userId(account.getUserId())
                .name(account.getName())
                .address(account.getAddress())
                .build();
    }

    public void initializeSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
