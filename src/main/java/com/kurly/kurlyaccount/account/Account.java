package com.kurly.kurlyaccount.account;

import com.kurly.kurlyaccount.account.dto.SignUpRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts", indexes = @Index(name = "idx_user_id", columnList = "user_id", unique = true))
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    public static Account of(SignUpRequest authenticationRequest) {
        return Account.builder()
                .userId(authenticationRequest.getUserId())
                .password(authenticationRequest.getPassword())
                .name(authenticationRequest.getName())
                .address(authenticationRequest.getAddress())
                .build();
    }
}