package org.ahmedukamel.gazl.mapper.user;

import org.ahmedukamel.gazl.dto.user.ProfileResponse;
import org.ahmedukamel.gazl.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Function;

@Component
public class ProfileResponseMapper implements Function<User, ProfileResponse> {
    @Override
    public ProfileResponse apply(User user) {
        boolean isCharity = Objects.nonNull(user.getCharity());
        String charityName = isCharity ? user.getCharity().getName() : null;
        Integer charityId = isCharity ? user.getCharity().getId() : null;

        boolean isGovernment = Objects.nonNull(user.getGovernment());
        String governmentName = isGovernment ? user.getGovernment().getName() : null;
        Integer governmentId = isGovernment ? user.getGovernment().getId() : null;
        String governmentLogo = isGovernment ? user.getGovernment().getLogo() : null;

        return new ProfileResponse(
                user.getId(),

                user.getEmail(),

                user.getName(),

                user.getPicture(),

                StringUtils.hasLength(user.getPicture()),

                user.getPhoneNumber().toString(),

                user.getRole(),

                user.getGender(),

                isCharity,

                charityName,

                charityId,

                isGovernment,

                governmentName,

                governmentId,

                governmentLogo,

                user.getRegistration()
        );
    }
}