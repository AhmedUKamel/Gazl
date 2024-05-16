package org.ahmedukamel.gazl.dto.opportunity;

import org.ahmedukamel.gazl.model.enumration.OpportunityType;

public record OpportunityResponse(
        Long id,

        String description,

        Integer count,

        String number,

        Double latitude,

        Double longitude,

        OpportunityType type,

        boolean isBusiness,

        String businessName,

        boolean isCharity,

        String charityName,

        boolean isGovernment,

        String governmentName,

        String governmentLogo
) {
}