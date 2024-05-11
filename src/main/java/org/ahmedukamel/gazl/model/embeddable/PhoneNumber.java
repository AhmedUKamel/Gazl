package org.ahmedukamel.gazl.model.embeddable;

import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PhoneNumber {
    private int countryCode;

    private long nationalNumber;

    public PhoneNumber(Phonenumber.PhoneNumber phoneNumber) {
        this.countryCode = phoneNumber.getCountryCode();
        this.nationalNumber = phoneNumber.getNationalNumber();
    }

    @Override
    public String toString() {
        return "+%d%d".formatted(countryCode, nationalNumber);
    }
}