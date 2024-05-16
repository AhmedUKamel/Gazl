package org.ahmedukamel.gazl.saver.opportunity;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.dto.opportunity.OpportunityRequest;
import org.ahmedukamel.gazl.mapper.phone.PhoneNumberMapper;
import org.ahmedukamel.gazl.model.Charity;
import org.ahmedukamel.gazl.model.Government;
import org.ahmedukamel.gazl.model.Opportunity;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.model.embeddable.Location;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.ahmedukamel.gazl.model.enumration.OpportunityType;
import org.ahmedukamel.gazl.repository.OpportunityRepository;
import org.ahmedukamel.gazl.util.ContextHolderUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OpportunitySaver implements Function<OpportunityRequest, Opportunity> {
    private final OpportunityRepository repository;
    private final PhoneNumberMapper mapper;

    @Override
    public Opportunity apply(OpportunityRequest request) {
        User user = ContextHolderUtils.getUser();

        final OpportunityType opportunityType;
        Charity charity = null;
        Government government = null;

        switch (user.getRole()) {
            case BUSINESS -> opportunityType = OpportunityType.BUSINESS;
            case CHARITY -> {
                opportunityType = OpportunityType.CHARITY;
                charity = user.getCharity();
            }
            case GOVERNMENT -> {
                opportunityType = OpportunityType.GOVERNMENT;
                government = user.getGovernment();
            }
            default -> throw new IllegalStateException("Unexpected value: " + user.getRole());
        }

        PhoneNumber phoneNumber = mapper.apply(request.number().strip());

        Opportunity opportunity = Opportunity
                .builder()
                .description(request.description().strip())
                .phoneNumber(phoneNumber)
                .location(new Location(request.latitude(), request.longitude()))
                .type(opportunityType)
                .business(user)
                .charity(charity)
                .government(government)
                .count(request.count())
                .build();

        return repository.save(opportunity);
    }
}