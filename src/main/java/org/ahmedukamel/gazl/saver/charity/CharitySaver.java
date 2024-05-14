package org.ahmedukamel.gazl.saver.charity;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.dto.charity.CharityRequest;
import org.ahmedukamel.gazl.mapper.phone.PhoneNumberMapper;
import org.ahmedukamel.gazl.model.Charity;
import org.ahmedukamel.gazl.model.embeddable.Location;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.ahmedukamel.gazl.repository.CharityRepository;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CharitySaver implements Function<CharityRequest, Charity> {
    private final DatabaseService databaseService;
    private final CharityRepository repository;
    private final PhoneNumberMapper mapper;

    @Override
    public Charity apply(CharityRequest request) {
        PhoneNumber phoneNumber = mapper.apply(request.number());
        databaseService.unique(repository::existsByPhoneNumber, phoneNumber, Charity.class);

        Location location = new Location(request.latitude(), request.longitude());
        databaseService.unique(repository::existsByLocation, location, Charity.class);

        String name = request.name().strip();
        databaseService.unique(repository::existsByNameIgnoreCase, name, Charity.class);

        Charity charity = Charity
                .builder()
                .phoneNumber(phoneNumber)
                .location(location)
                .name(name)
                .build();

        return repository.save(charity);
    }
}