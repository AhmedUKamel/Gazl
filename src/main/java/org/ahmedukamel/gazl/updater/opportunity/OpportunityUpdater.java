package org.ahmedukamel.gazl.updater.opportunity;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.dto.opportunity.OpportunityRequest;
import org.ahmedukamel.gazl.mapper.phone.PhoneNumberMapper;
import org.ahmedukamel.gazl.model.Opportunity;
import org.ahmedukamel.gazl.model.embeddable.Location;
import org.ahmedukamel.gazl.repository.OpportunityRepository;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class OpportunityUpdater implements BiFunction<Opportunity, OpportunityRequest, Opportunity> {
    private final OpportunityRepository opportunityRepository;
    private final PhoneNumberMapper phoneNumberMapper;

    @Override
    public Opportunity apply(Opportunity opportunity, OpportunityRequest request) {
        opportunity.setDescription(request.description().strip());
        opportunity.setCount(request.count());
        opportunity.setLocation(new Location(request.latitude(), request.longitude()));
        opportunity.setPhoneNumber(phoneNumberMapper.apply(request.number().strip()));

        return opportunityRepository.save(opportunity);
    }
}