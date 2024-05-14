package org.ahmedukamel.gazl.mapper.government;

import org.ahmedukamel.gazl.dto.government.GovernmentResponse;
import org.ahmedukamel.gazl.model.Government;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GovernmentResponseMapper implements Function<Government, GovernmentResponse> {
    @Override
    public GovernmentResponse apply(Government government) {
        return new GovernmentResponse(
                government.getId(),
                government.getName(),
                government.getLogo()
        );
    }
}