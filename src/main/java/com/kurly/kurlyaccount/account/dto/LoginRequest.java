package com.kurly.kurlyaccount.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {

    @NotBlank(message = "ID 값은 필수입니다.")
    private String userId;

    @NotBlank(message = "password 값은 필수입니다.")
    private String password;
}
