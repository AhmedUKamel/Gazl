package org.ahmedukamel.gazl.service.auth;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.model.BearerToken;
import org.ahmedukamel.gazl.saver.user.UserSaver;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.ahmedukamel.gazl.dto.auth.RegisterRequest;
import org.ahmedukamel.gazl.dto.user.ProfileResponse;
import org.ahmedukamel.gazl.mapper.user.ProfileResponseMapper;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.service.token.BearerTokenService;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    final AuthenticationManager authenticationManager;
    final ProfileResponseMapper profileResponseMapper;
    final BearerTokenService bearerTokenService;
    final MessageSource messageSource;
    final UserSaver userSaver;

    @Override
    public Object register(Object object) {
        RegisterRequest request = (RegisterRequest) object;
        User savedUser = userSaver.apply(request);

        ProfileResponse response = profileResponseMapper.apply(savedUser);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.auth.AuthService.register", null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (Objects.nonNull(authentication) &&
            Objects.nonNull(authentication.getPrincipal()) &&
            authentication.getPrincipal() instanceof User user) {

            BearerToken bearerToken = bearerTokenService.getBearerToken(user);
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.auth.AuthService.login", null, LocaleConstants.ARABIC);

            return new ApiResponse(true, message, bearerToken.getToken());
        }

        throw new IllegalArgumentException("Invalid email or password");
    }

    @Override
    public Object logout(String accessToken) {
        return null;
    }
}
