package org.ahmedukamel.gazl.mapper.opportunity;

import org.ahmedukamel.gazl.dto.opportunity.OpportunityResponse;
import org.ahmedukamel.gazl.model.Opportunity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class OpportunityResponseMapper implements Function<Opportunity, OpportunityResponse> {
    @Override
    public OpportunityResponse apply(Opportunity opportunity) {
        boolean isCharity = Objects.nonNull(opportunity.getCharity());
        String charityName = isCharity ? opportunity.getCharity().getName() : null;

        boolean isGovernment = Objects.nonNull(opportunity.getGovernment());
        String governmentName = isGovernment ? opportunity.getGovernment().getName() : null;
        String governmentLogo = isGovernment ? opportunity.getGovernment().getLogo() : null;

        boolean isBusiness = !isCharity && !isGovernment;
        String businessName = isBusiness ? opportunity.getBusiness().getName() : null;

        return new OpportunityResponse(
                opportunity.getId(),
                opportunity.getDescription(),
                opportunity.getCount(),
                opportunity.getPhoneNumber().toString(),
                opportunity.getLocation().getLatitude(),
                opportunity.getLocation().getLongitude(),
                opportunity.getType(),
                isBusiness,
                businessName,
                isCharity,
                charityName,
                isGovernment,
                governmentName,
                governmentLogo
        );
    }
}