package com.todimu.backend.bookstoreservice.service;

import com.todimu.backend.bookstoreservice.data.dto.request.CreateUserRequest;
import com.todimu.backend.bookstoreservice.data.entity.Authority;
import com.todimu.backend.bookstoreservice.data.entity.User;
import com.todimu.backend.bookstoreservice.exception.EmailAlreadyExistsException;
import com.todimu.backend.bookstoreservice.repository.AuthorityRepository;
import com.todimu.backend.bookstoreservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private AuthorityRepository authorityRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UserService userService;

    private User user;
    private CreateUserRequest createUserRequest;
    private Authority userAuthority;

    @BeforeEach
    public void init() {
        user = User.builder()
                .password("test-password-12345")
                .email("test-user@example.com")
                .build();

        user.setId(2L);

        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test-user@example.com");
        createUserRequest.setPassword("password123$");

        userAuthority = new Authority();
        userAuthority.setAuthorityName("ROLE_USER");
    }

    @Test
    public void successfulUserRegistration() {

        BDDMockito.given(userRepository.findByEmailIgnoreCase(createUserRequest.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(passwordEncoder.encode(Mockito.any(String.class))).willReturn("password-hash");
        BDDMockito.given(authorityRepository.findByAuthorityName("ROLE_USER")).willReturn(Optional.of(userAuthority));
        BDDMockito.given(userRepository.save(Mockito.any(User.class))).willReturn(user);

        User registeredUser = userService.createUser(createUserRequest);

        Assertions.assertThat(registeredUser.getEmail()).isEqualTo("test-user@example.com");
    }

    @Test
    public void failedRegistrationIfEmailAlreadyExists() {

        BDDMockito.given(userRepository.existsByEmailIgnoreCase(createUserRequest.getEmail())).willReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(createUserRequest));

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }
}