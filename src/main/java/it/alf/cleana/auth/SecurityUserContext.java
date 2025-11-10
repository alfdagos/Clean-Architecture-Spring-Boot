package it.alf.cleana.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserContext implements UserContext {
    @Override
    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new IllegalStateException("User context is unavailable");
        }
        String principal = String.valueOf(auth.getPrincipal());
        try {
            return Long.parseLong(principal);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Invalid user id in principal");
        }
    }
}
