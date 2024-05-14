package org.ahmedukamel.gazl.mapper.charity;

import org.ahmedukamel.gazl.dto.charity.CharityResponse;
import org.ahmedukamel.gazl.model.Charity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CharityResponseMapper implements Function<Charity, CharityResponse> {
    @Override
    public CharityResponse apply(Charity charity) {
        return new CharityResponse(
                charity.getId(),
                charity.getName(),
                charity.getPhoneNumber().toString(),
                charity.getLocation().getLatitude(),
                charity.getLocation().getLongitude()
        );
    }
}