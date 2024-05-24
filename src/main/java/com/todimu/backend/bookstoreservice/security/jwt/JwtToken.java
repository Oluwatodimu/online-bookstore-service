package com.todimu.backend.bookstoreservice.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {

    private String authToken;

    public JwtToken(String authToken) {
        this.authToken = authToken;
    }
}
