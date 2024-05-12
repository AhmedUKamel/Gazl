package org.ahmedukamel.gazl.service.account;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.model.AccountActivationToken;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.model.enumration.TokenType;
import org.ahmedukamel.gazl.repository.AccountActivationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AccountActivationTokenGenerator implements Function<User, AccountActivationToken> {
    final AccountActivationTokenRepository repository;

    @Override
    public AccountActivationToken apply(User user) {
        AccountActivationToken token = new AccountActivationToken();
        token.setUser(user);
        token.setType(TokenType.ACCOUNT_ACTIVATION);
        token.setCreation(LocalDateTime.now());
        token.setExpiration(token.getCreation().plusDays(1));
        return repository.save(token);
    }
}