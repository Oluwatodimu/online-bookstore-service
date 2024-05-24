package com.todimu.backend.bookstoreservice.security;

import com.todimu.backend.bookstoreservice.data.entity.Authority;
import com.todimu.backend.bookstoreservice.data.entity.User;
import com.todimu.backend.bookstoreservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("ensure user details are found")
    public void loadUsernameWithEmailAndGetUser() {
        User activeUser = User.builder()
                .email("test@email.com")
                .password("test-email-12345")
                .build();

        Authority authority = new Authority();
        authority.setAuthorityName("ROLE_USER");
        authority.setId(1L);

        activeUser.setId(1L);
        activeUser.setAuthorities(Set.of(authority));

        BDDMockito.given(userRepository.findByEmailIgnoreCase(activeUser.getEmail())).willReturn(Optional.of(activeUser));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(activeUser.getEmail());

        Assertions.assertThat(userDetails.getUsername()).isNotEmpty();
        Assertions.assertThat(userDetails.getPassword()).isNotEmpty();
    }

    @Test
    @DisplayName("throw exception when user details are not found")
    public void throwExceptionWhenUserDetailsNotFound() {
        BDDMockito.given(userRepository.findByEmailIgnoreCase(BDDMockito.anyString())).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(Mockito.anyString()));
    }
}