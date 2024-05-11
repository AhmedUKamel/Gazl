package org.ahmedukamel.gazl.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.annotation.KSAPhone;
import org.ahmedukamel.gazl.mapper.phone.PhoneNumberMapper;

@RequiredArgsConstructor
public class KSAPhoneValidator implements ConstraintValidator<KSAPhone, String> {
    final PhoneNumberMapper mapper;

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        try {
            mapper.apply(number);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}