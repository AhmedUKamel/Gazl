package org.ahmedukamel.gazl.updater.charity;


import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.dto.charity.CharityRequest;
import org.ahmedukamel.gazl.mapper.phone.PhoneNumberMapper;
import org.ahmedukamel.gazl.model.Charity;
import org.ahmedukamel.gazl.model.embeddable.Location;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.ahmedukamel.gazl.repository.CharityRepository;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CharityUpdater implements BiFunction<Charity, CharityRequest, Charity> {
    private final DatabaseService databaseService;
    private final CharityRepository repository;
    private final PhoneNumberMapper mapper;

    @Override
    public Charity apply(Charity charity, CharityRequest request) {
        PhoneNumber phoneNumber = mapper.apply(request.number());
        if (!charity.getPhoneNumber().equals(phoneNumber)) {
            databaseService.unique(repository::existsByPhoneNumber, phoneNumber, Charity.class);
            charity.setPhoneNumber(phoneNumber);
        }

        Location location = new Location(request.latitude(), request.longitude());
        if (!charity.getLocation().equals(location)) {
            databaseService.unique(repository::existsByLocation, location, Charity.class);
            charity.setLocation(location);
        }

        String name = request.name().strip();
        if (!charity.getName().equals(name)) {
            databaseService.unique(repository::existsByNameIgnoreCase, name, Charity.class);
            charity.setName(name);
        }

        return repository.save(charity);
    }
}