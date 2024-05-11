package org.ahmedukamel.gazl.saver.user;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.dto.auth.RegisterRequest;
import org.ahmedukamel.gazl.mapper.phone.PhoneNumberMapper;
import org.ahmedukamel.gazl.model.Charity;
import org.ahmedukamel.gazl.model.Government;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.ahmedukamel.gazl.model.enumration.Role;
import org.ahmedukamel.gazl.repository.CharityRepository;
import org.ahmedukamel.gazl.repository.GovernmentRepository;
import org.ahmedukamel.gazl.repository.UserRepository;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserSaver implements Function<RegisterRequest, User> {
    final GovernmentRepository governmentRepository;
    final CharityRepository charityRepository;
    final PhoneNumberMapper phoneNumberMapper;
    final PasswordEncoder passwordEncoder;
    final DatabaseService databaseService;
    final UserRepository userRepository;
    final MessageSource messageSource;

    @Override
    public User apply(RegisterRequest request) {
        PhoneNumber phoneNumber = phoneNumberMapper.apply(request.number());
        databaseService.unique(userRepository::existsByPhoneNumber, phoneNumber, User.class);

        String email = request.email().strip().toLowerCase();
        databaseService.unique(userRepository::existsByEmailIgnoreCase, email, User.class);

        databaseService.unique(userRepository::existsById, request.id(), User.class);

        User user = new User();

        switch (request.role()) {
            case CHARITY -> {
                Charity charity = databaseService.get(charityRepository::findById, request.entityId(), Charity.class);
                user.setCharity(charity);
            }
            case GOVERNMENT -> {
                Government government = databaseService.get(governmentRepository::findById, request.entityId(), Government.class);
                user.setGovernment(government);
            }
            case BUSINESS -> user.setRole(Role.BUSINESS);
        }

        user.setId(request.id());
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setName(request.name().strip());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setGender(request.gender());

        return userRepository.save(user);
    }
}