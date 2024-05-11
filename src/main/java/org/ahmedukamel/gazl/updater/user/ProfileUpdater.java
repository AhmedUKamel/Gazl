package org.ahmedukamel.gazl.updater.user;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.dto.user.UpdateProfileRequest;
import org.ahmedukamel.gazl.mapper.phone.PhoneNumberMapper;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.ahmedukamel.gazl.repository.UserRepository;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class ProfileUpdater implements BiFunction<User, UpdateProfileRequest, User> {
    final PhoneNumberMapper phoneNumberMapper;
    final DatabaseService databaseService;
    final UserRepository userRepository;

    @Override
    public User apply(User user, UpdateProfileRequest request) {
        PhoneNumber phoneNumber = phoneNumberMapper.apply(request.number());
        if (!user.getPhoneNumber().equals(phoneNumber)) {
            databaseService.unique(userRepository::existsByPhoneNumber, phoneNumber, User.class);
        }
        user.setPhoneNumber(phoneNumber);
        user.setName(request.name().strip());
        return userRepository.save(user);
    }
}