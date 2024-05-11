package org.ahmedukamel.gazl.mapper.phone;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.SneakyThrows;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PhoneNumberMapper implements Function<String, PhoneNumber> {
    @SneakyThrows
    @Override
    public PhoneNumber apply(String string) {
        Phonenumber.PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(string, "SA");

        if (phoneNumber.getCountryCode() != 966) {
            throw new IllegalArgumentException("Invalid country code: " + phoneNumber.getCountryCode());
        }

        if (phoneNumber.getNationalNumber() < 100_000_000 ||
            phoneNumber.getNationalNumber() > 999_999_999) {
            throw new IllegalArgumentException("Invalid national number: " + phoneNumber.getNationalNumber());
        }

        return new PhoneNumber(phoneNumber);
    }
}