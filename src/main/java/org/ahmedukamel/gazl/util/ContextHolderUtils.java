package org.ahmedukamel.gazl.util;

import org.ahmedukamel.gazl.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class ContextHolderUtils {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Optional<User> getOptionalUser() {
        if (getAuthentication().getPrincipal() instanceof User user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public static User getUser() {
        return getOptionalUser().orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}