package com.kurly.kurlyaccount.account;

import com.kurly.kurlyaccount.account.dto.AuthenticationRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts", indexes = @Index(name = "index_name", columnList = "name", unique = true))
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Long money;

    private String address;

    public static Account of(AuthenticationRequest authenticationRequest) {
        return Account.builder()
                .name(authenticationRequest.getName())
                .password(authenticationRequest.getPassword())
                .build();
    }
}