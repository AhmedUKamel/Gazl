package org.ahmedukamel.gazl.service.account;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.model.AccountActivationToken;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.repository.AccountActivationTokenRepository;
import org.ahmedukamel.gazl.repository.UserRepository;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountWebService implements IAccountWebService {
    final AccountActivationTokenRepository repository;
    final DatabaseService databaseService;
    final UserRepository userRepository;
    final MessageSource messageSource;

    @Override
    public ModelAndView activateAccount(UUID id, String email) {
        AccountActivationToken token;

        try {
            token = databaseService.get(repository::findById, id, AccountActivationToken.class);
        } catch (EntityNotFoundException exception) {
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.account.AccountWebService.activateAccount.EntityNotFoundException",
                    null, LocaleConstants.ARABIC);
            return getErrorPage(message);

        }

        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.account.AccountWebService.activateAccount.isExpired",
                    null, LocaleConstants.ARABIC);
            return getErrorPage(message);

        } else if (token.isRevoked()) {
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.account.AccountWebService.activateAccount.isRevoked",
                    null, LocaleConstants.ARABIC);
            return getErrorPage(message);

        } else if (token.isUsed()) {
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.account.AccountWebService.activateAccount.isUsed",
                    null, LocaleConstants.ARABIC);
            return getErrorPage(message);

        } else if (!token.getUser().getEmail().equalsIgnoreCase(email.strip())) {
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.account.AccountWebService.activateAccount.mismatch.email",
                    null, LocaleConstants.ARABIC);
            return getErrorPage(message);
        }

        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        token.setUsed(true);
        repository.save(token);

        return new ModelAndView("account-activation-page");
    }

    private static ModelAndView getErrorPage(String message) {
        return new ModelAndView("error-page", Map.of(
                "message", message
        ));
    }
}