package org.ahmedukamel.gazl.dto.auth;

import jakarta.validation.constraints.*;
import org.ahmedukamel.gazl.annotation.KSAPhone;
import org.ahmedukamel.gazl.constant.RegexConstants;
import org.ahmedukamel.gazl.model.enumration.Gender;
import org.ahmedukamel.gazl.model.enumration.Role;

public record RegisterRequest(
        @Min(value = 1_000_000_000L)
        @Max(value = 9_999_999_999L)
        @NotNull
        Long id,

        @Email
        @NotEmpty
        String email,

        @Pattern(regexp = RegexConstants.PASSWORD)
        @NotEmpty
        String password,

        @NotEmpty
        String name,

        @NotEmpty
        @KSAPhone
        String number,

        @NotNull
        Role role,

        @NotNull
        Gender gender,

        Integer entityId
) {
}