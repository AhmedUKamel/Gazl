package org.ahmedukamel.gazl.dto.opportunity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ahmedukamel.gazl.annotation.KSAPhone;

public record OpportunityRequest(
        @NotBlank
        String description,

        @NotNull
        Integer count,

        @NotBlank
        @KSAPhone
        String number,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude
) {
}