package com.todimu.backend.bookstoreservice.service;


import com.todimu.backend.bookstoreservice.data.dto.request.CreateUserRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.UserLoginRequest;
import com.todimu.backend.bookstoreservice.data.entity.Authority;
import com.todimu.backend.bookstoreservice.data.entity.User;
import com.todimu.backend.bookstoreservice.exception.EmailAlreadyExistsException;
import com.todimu.backend.bookstoreservice.repository.AuthorityRepository;
import com.todimu.backend.bookstoreservice.repository.UserRepository;
import com.todimu.backend.bookstoreservice.security.jwt.JwtToken;
import com.todimu.backend.bookstoreservice.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER = "ROLE_USER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthorityRepository authorityRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public User createUser(CreateUserRequest createUserRequest) {

        if (userRepository.existsByEmailIgnoreCase(createUserRequest.getEmail())) {
            throw new EmailAlreadyExistsException("email already in use");
        }

        User newUser = User.builder()
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findByAuthorityName(USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        return userRepository.save(newUser);
    }

    public JwtToken authenticateUser(UserLoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        return tokenProvider.createToken(authentication);
    }
}
