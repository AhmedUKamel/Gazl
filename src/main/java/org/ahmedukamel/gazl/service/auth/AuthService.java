package org.ahmedukamel.gazl.service.auth;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.ahmedukamel.gazl.dto.auth.RegisterRequest;
import org.ahmedukamel.gazl.dto.user.ProfileResponse;
import org.ahmedukamel.gazl.mapper.user.ProfileResponseMapper;
import org.ahmedukamel.gazl.model.AccountActivationToken;
import org.ahmedukamel.gazl.model.BearerToken;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.saver.user.UserSaver;
import org.ahmedukamel.gazl.service.account.AccountActivationTokenGenerator;
import org.ahmedukamel.gazl.service.mail.AccountActivationMailSender;
import org.ahmedukamel.gazl.service.token.BearerTokenService;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    final AccountActivationTokenGenerator accountActivationTokenGenerator;
    final AccountActivationMailSender accountActivationMailSender;
    final AuthenticationManager authenticationManager;
    final ProfileResponseMapper profileResponseMapper;
    final BearerTokenService bearerTokenService;
    final MessageSource messageSource;
    final UserSaver userSaver;

    final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public Object register(Object object) {
        RegisterRequest request = (RegisterRequest) object;
        User savedUser = userSaver.apply(request);

        CompletableFuture<AccountActivationToken> tokenFuture =
                CompletableFuture.supplyAsync(() -> accountActivationTokenGenerator.apply(savedUser), executor);
        tokenFuture.thenAcceptAsync(accountActivationMailSender::send, executor);

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
