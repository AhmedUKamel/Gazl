package org.ahmedukamel.gazl.dto.charity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ahmedukamel.gazl.annotation.KSAPhone;

public record CharityRequest(
        @NotBlank
        String name,

        @NotBlank
        @KSAPhone
        String number,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude
) {
}