package org.ahmedukamel.gazl.service.db;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    final MessageSource messageSource;

    public <T, R> R get(Function<T, Optional<R>> function, T value, Class<?> theClass) {
        String message = messageSource.getMessage(
                "org.ahmedukamel.gazl.service.db.DatabaseService.get", new Object[]{value, theClass.getSimpleName()}, LocaleConstants.ARABIC);
        return function.apply(value).orElseThrow(
                () -> new EntityNotFoundException(message));
    }

    public <T> void unique(Predicate<T> predicate, T value, Class<?> theClass) {
        String message = messageSource.getMessage(
                "org.ahmedukamel.gazl.service.db.DatabaseService.unique", new Object[]{value, theClass.getSimpleName()}, LocaleConstants.ARABIC);
        if (predicate.test(value))
            throw new EntityExistsException(message);
    }
}