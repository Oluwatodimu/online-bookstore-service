package com.todimu.backend.bookstoreservice.security;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAwareImpl implements AuditorAware<String> {

    private static final String SYSTEM = "system";

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        Optional<String> loggedInUser = getLoggedInUser();
        return Optional.of(loggedInUser.orElse(SYSTEM));
    }

    private static Optional<String> getLoggedInUser() {
        String principal = extractPrincipal(SecurityContextHolder.getContext().getAuthentication());
        return Optional.ofNullable(principal);
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
