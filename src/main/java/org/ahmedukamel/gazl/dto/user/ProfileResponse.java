package org.ahmedukamel.gazl.dto.user;

import org.ahmedukamel.gazl.model.enumration.Gender;
import org.ahmedukamel.gazl.model.enumration.Role;

import java.time.LocalDateTime;

public record ProfileResponse(
        Long id,

        String email,

        String name,

        String picture,

        boolean hasPicture,

        String number,

        Role role,

        Gender gender,

        boolean isCharity,

        String charityName,

        Integer charityId,

        boolean isGovernment,

        String governmentName,

        Integer governmentId,

        String governmentLogo,

        LocalDateTime registration
) {
}