package org.ahmedukamel.gazl.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.gazl.annotation.KSAPhone;

public record UpdateProfileRequest(
        @NotBlank
        String name,

        @NotBlank
        @KSAPhone
        String number
) {
}